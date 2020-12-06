package es.ucm.gdv.engine;

import java.io.InputStream;
import es.ucm.gdv.engine.Font;


/**
 * Interfaz para el motor gráfico.
 * Proporciona las funcionalidades gráficas mínimas sobre la ventana de la aplicación.
 * TODO:  independizar la lógica de la resolución del dispositivo
 */
public interface Graphics {
    /**
     *  Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente
     * en negrita.
     * @param fileName
     * @param size
     * @param isBold
     * @return
     */
    Font newFont(String fileName, float size, boolean isBold);

    void setFont(Font font);

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    void clear(int color);

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    void translate(double x, double y);
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
    void setColor(int color);

    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void drawLine(double x1, double y1, double x2, double y2);

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void fillRect(double x1, double y1, double x2, double y2);

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    void drawText(String text, int x, int y);

    /**
     * Devuelven el tamaño de la ventana.
     * @return
     */
    int getWidth();
    int getHeight();
    void toggleFullscreen();
}