package es.ucm.gdv.engine.android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.InputStream;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Canvas canvas;
    Paint paint;

    public AndroidGraphics(AssetManager assets) {
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
    public void clear(int[] color) {
        canvas.drawRGB(color[0], color[1], color[2]);
    }

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    public void translate(int x, int y) {

    }

    public void scale(float x, float y) {

    }

    public void rotate(float angle) {

    }

    public void save() {

    }

    public void restore() {

    }

    //------------------------------------------------------------

    /**
     * Establece el color a utilizar en las operaciones de
     * dibujado posteriores.
     * @param color
     */
    public void setColor(int[] color) {
        paint.setARGB(255, color[0], color[1], color[2]);
    }

    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        canvas.drawLine(x1, y2, x2, y2, paint);
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
     * Devuelven el tamaño de la ventana.
     * @return
     */
    public int getWidth() {
        return 0;
    }
    public int getHeight() {
        return 0;
    }
}
