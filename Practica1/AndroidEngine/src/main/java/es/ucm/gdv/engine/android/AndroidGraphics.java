package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Game;
import es.ucm.gdv.engine.Graphics;

public class AndroidGraphics extends SurfaceView implements Graphics {
    AssetManager assets;
    Canvas canvas;
    Paint paint;
    /**
     * Manejador de la superficie para poder acceder a su contenido.
     */
    private final SurfaceHolder _holder;

    public AndroidGraphics(AssetManager assets, Context context) {
        super(context);
        _holder = getHolder();
        this.assets = assets;
        //this.canvas = new Canvas(frameBuffer);
    }


    /**
     *  Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente
     * en negrita.
     * @param filename
     * @param size
     * @param isBold
     * @return
     */
    public Font newFont(InputStream filename, int size, boolean isBold) {
        return null;
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

    public void rotate(float angle) {
        canvas.rotate(angle);
    }

    public void save() {
        canvas.save();
    }

    public void restore() {
        canvas.restore();
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
        canvas.drawLine((float)x1, (float)y2, (float)x2, (float)y2, paint);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        canvas.drawLine((float)x1, (float)y2, (float)x2, (float)y2, paint);
    }


    @Override
    public void fillRect(double x1, double y1, double x2, double y2) {

    }



    /**
     * Dibuja un rectángulo relleno.
     * @param x1 Left
     * @param y1 Top
     * @param x2 Right
     * @param y2 Bottom
     */
    public void fillRect(int x1, int y1, int x2, int y2) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x1, y1, x2, y2, paint);
    }

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    public void drawText(String text, int x, int y) {

    }

    /**
     * Devuelven el tamaño del canvas.
     * @return
     */
    public int getCanvasWidth() {
        return canvas.getWidth();
    }
    public int getCanvasHeight() {
        return canvas.getHeight();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void renderFrame(Game game) {
        // Pintamos el frame
        while (!_holder.getSurface().isValid())
            ;
        canvas = _holder.lockCanvas();
        game.render(); //render(canvas);
        _holder.unlockCanvasAndPost(canvas);
                /*
                // Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
                try {
                    Thread.sleep(1);
                }
                catch(Exception e) {}
    			*/
    }

}
