package es.ucm.gdv.engine;

/**
 * Interfaz para el motor gráfico.
 * Proporciona las funcionalidades gráficas mínimas sobre la ventana de la aplicación.
 * TODO:  independizar la lógica de la resolución del dispositivo
 */
public interface Graphics {

    /**
     *  Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente
     * en negrita.
     * @param filename
     * @param size
     * @param isBold
     * @return
     */
    Font newFont(String filename, int size, boolean isBold);

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    void clear(float[] color);

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    void translate(float x, float y);
    void scale(float x, float y);
    void rotate(float angle);
    void save();
    void restore();

    //------------------------------------------------------------

    /**
     * Establece el color a utilizar en las operaciones de
     * dibujado posteriores.
     * @param color
     */
    void setColor(float[] color);

    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void drawLine(float x1, float y1, float x2, float y2);

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void fillRect(float x1, float y1, float x2, float y2);

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    void drawText(String text, float x, float y);

    /**
     * Devuelven el tamaño de la ventana.
     * @return
     */
    int getWidth();
    int getHeight();

}