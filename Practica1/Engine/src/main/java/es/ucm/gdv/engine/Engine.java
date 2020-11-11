package es.ucm.gdv.engine;

import java.io.InputStream;

/**
 * Interfaz que aglutina Font, Graphics e Input. En condiciones normales, Graphics
 * Encargado de mantener las instancias y otros métodos útiles de acceso a la plataforma
 */
public interface Engine {
    /**
     * Devuelve la instancia del motor gráfico.
     * @return
     */
    Graphics getGraphics();

    /**
     * Devuelve la instancia del gestor de entrada.
     * @return
     */
    Input getInput();

    /**
     * Devuelve un stream de lectura de un fichero.
     * @return
     */
    InputStream openInputStream(String filename);

}
