package es.ucm.gdv.engine;

import java.util.List;

public interface Input {
    /**
     * Clase que representa la información de un toque sobre
     *     la pantalla (o evento de ratón). Indicará el tipo (pulsación, liberación,
     *     desplazamiento), la posición y el identificador del dedo (o botón).
     */
    class TouchEvent {

    }

    /**
     * Devuelve la lista de eventos recibidos desde la última invocación.
     * @return
     */
    List<TouchEvent> getTouchEvents();
}
