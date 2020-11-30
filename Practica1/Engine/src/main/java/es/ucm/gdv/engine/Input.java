package es.ucm.gdv.engine;

import java.util.List;

public interface Input {
    /**
     * Clase que representa la información de un toque sobre
     *     la pantalla (o evento de ratón). Indicará el tipo (pulsación, liberación,
     *     desplazamiento), la posición y el identificador del dedo (o botón).
     */
    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        /**
         * Tipo del evento de pulsación.
         */
        public int type;
        /**
         * Coordenadas de la pulsación.
         */
        public int x, y;
        /**
         * El ID de la pulsación.
         * (Para diferenciar múltiples pulsaciones)
         */
        public int pointer;
    }

    /**
     * Devuelve true si la pantalla está siendo pulsada.
     * @param pointer
     * @return si la pantalla se está pulsando
     */
    public boolean isTouchDown(int pointer);

    /**
     * Devuelve la coordenada X de la posición de la pantalla
     * donde se está pulsando actualmente, o undefined si no se
     *      * está pulsando en ese momento.
     * @param pointer
     * @return coordenada X de la pulsación
     */
    public int getTouchX(int pointer);

    /**
     * Devuelve la coordenada Y de la posición de la pantalla
     * donde se está pulsando actualmente, o undefined si no se
     * está pulsando en ese momento.
     * @param pointer
     * @return coordenada Y de la pulsación
     */
    public int getTouchY(int pointer);


    /**
     * Devuelve la lista de eventos recibidos desde la última invocación.
     * @return Lista de eventos recibidos desde la última invocación
     */
    List<TouchEvent> getTouchEvents();
}
