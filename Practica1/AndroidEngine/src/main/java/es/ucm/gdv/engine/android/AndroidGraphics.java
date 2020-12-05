package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.security.SignatureSpi;

import es.ucm.gdv.engine.AbstractGraphics;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Game;

public class AndroidGraphics extends AbstractGraphics {
    private Canvas canvas;
    private Paint paint ;
    // Indica si se ha hecho un save() antes de un restore()
    private boolean stateIsSaved = false;
    private AssetManager assetsMgr;
    // Vista principal de la actividad
    private SurfaceView renderView;
    // Manejador de la superficie para poder acceder a su contenido.
    private SurfaceHolder holder;


    public AndroidGraphics(Context context) {
        // Crear el SurfaceView
        renderView = new SurfaceView(context);
        holder = renderView.getHolder();
        // Obtener el asset manager
        assetsMgr = context.getAssets();
         paint =new Paint();
         paint.setStrokeCap(Paint.Cap.SQUARE);
        do {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            screenWidth = metrics.widthPixels;
            screenHeight = metrics.heightPixels;
        } while(screenWidth == 0); // Nos aseguramos de que el context ya se ha creado
    }

    public View getSurfaceView() {
        return (View) renderView;
    }

    /**
     *  Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente
     * en negrita.
     * @param fileName
     * @param size
     * @param isBold
     * @return
     */
    public Font newFont(String fileName, float size, boolean isBold) {
        Font baseFont = new AndroidFont(fileName, size, isBold, assetsMgr);

        return baseFont;
    }

    /**
     * Establece la fuente a usar en las próximas llamadas a drawText
     * @param font La fuente que se desea usar.
     */
    @Override
    public void setFont(Font font) {
        paint.setTypeface(((AndroidFont)font).getFont());
        paint.setFakeBoldText(font.isBold());
        paint.setTextSize(font.getFontSize());
    }

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    public void clear(int color) {
        canvas.drawRGB(
                (color & 0xFF0000) >> 16,
                (color & 0xFF00) >> 8,
                color & 0xFF
        );
    }

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    public void translate(double x, double y) {
        canvas.translate((float)x, (float)y);
    }

    public void scale(float x, float y) {
        canvas.scale(x, y);
    }

    /**
     * Le aplica una rotación al canvas.
     * @param angle Ángulo de la rotación en RADIANES.
     */
    public void rotate(float angle) {
        canvas.rotate(angle * 180 /(float) Math.PI);
    }

    /**
     * Guarda el estado del canvas.
     */
    public void save() {
        canvas.save();
        stateIsSaved = true;
    }

    /**
     * Restaura el último estado guardado del canvas.
     */
    public void restore() {
        if(stateIsSaved) {
            canvas.restore();

            stateIsSaved = false;
        } else {
            //System.out.println("Aviso: Intento de restaurar canvas sin guardar primero.\n");
        }

    }

    //------------------------------------------------------------

    /**
     * Establece el color a utilizar en las operaciones de
     * dibujado posteriores.
     * @param color
     */
    public void setColor(int color) {
        paint.setARGB(255,
                (color & 0xFF0000) >> 16,
                (color & 0xFF00) >> 8,
                color & 0xFF
        );
    }

    @Override
    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(double x1, double y1, double x2, double y2) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth (2f);
        canvas.drawLine((float)x1, (float)y1, (float)x2, (float)y2, paint);
    }

    @Override
    public void fillRect(double x1, double y1, double x2, double y2) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect((float)x1, (float)y1, (float)x2, (float)y2, paint);
    }

    /**
     * Dibuja un rectángulo relleno.
     * @param x1 Left
     * @param y1 Top
     * @param x2 Right
     * @param y2 Bottom
     */

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    public void drawText(String text, int x, int y) {
        canvas.drawText(text, x, y, paint);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     *  Se obtiene el canvas y se bloquea.
     *  IMPORTANTE: Llamar a esto antes de renderFrame
     */
    public void lockCanvas() {
        while (!holder.getSurface().isValid())
            ;
        canvas = holder.lockCanvas();
    }

    /**
     * Desbloquea el canvas.
     */
    public void unlockCanvas() {
        holder.unlockCanvasAndPost(canvas);
    }

    /**
     * Renderiza el siguiente frame del juego
     * y, al terminar, libera el canvas.
     * @param game El juego con la lista de objetos a renderizar.
     */
    public void renderFrame(Game game) {
        if(canvas == null) {
            // Problema: No hemos hecho el lockCanvas
            System.out.println("ERROR: Canvas is null.");
            return;
        }

        // Pintamos el frame
        try {
            // Traslación del canvas hacia el centro de la pantalla
            translate(canvasXOffset, canvasYOffset);

            // Escalado del canvas
            scale(scaleFactor, scaleFactor);

            // Renderizado de objetos del juego
            game.render();
        }
        catch (java.lang.IllegalStateException exception) {
            if (exception.getMessage() != null && (//
                    exception.getMessage().contains("Underflow in restore") || //
                            exception.getCause().getMessage().contains("Underflow in restore"))) { //
                System.out.println("ERROR: Canvas underflow in restore() (java.lang.IllegalStateException: Underflow in restore)");
            }
            else {
                throw exception; // No es un underflow
            }
        }


        // Desbloqueamos el canvas
        unlockCanvas();
    }
}