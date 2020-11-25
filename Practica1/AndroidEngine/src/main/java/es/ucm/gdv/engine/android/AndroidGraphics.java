package es.ucm.gdv.engine.android;

import android.content.res.AssetManager;
import android.graphics.Canvas;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;

public class AndroidGraphics implements Graphics {
    AssetManager assets;

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
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    public void clear(float[] color) {

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
    public void setColor(float[] color) {

    }

    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(int x1, int y1, int x2, int y2) {

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
