package es.ucm.gdv.engine.android;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {
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

    public void translate(float x, float y) {

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
    public void drawLine(float x1, float y1, float x2, float y2) {

    }

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(float x1, float y1, float x2, float y2) {

    }

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    public void drawText(String text, float x, float y) {

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
