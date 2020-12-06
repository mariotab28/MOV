package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Input;

public class Button extends MyText {
    double offSetClickX0 = 0, offSetClickY0 = 0, offSetClickX1 = 0, offSetClickY1 = 0;
    boolean clicked = false;

    public Button(Engine eng, int id) {
        super(eng, id);
    }

    /**
     * Establece el offset del boton en el que se capta la pulsacion del raton
     * @param ofx1
     * @param ofy1
     * @param ofx2
     * @param ofy2
     */
    public void setOffSetClick(double ofx1, double ofy1, double ofx2, double ofy2) {
        offSetClickX0 = ofx1;
        offSetClickY0 = ofy1;
        offSetClickX1 = ofx2;
        offSetClickY1 = ofy2;
    }

    /**
     * Comprobación de si se ha pulsado y por consecuente llamada a la función correspondiente
     * @param deltaTime
     */
    public void update(float deltaTime) {
        if (isActive && clicked)
                function();
    }

    /**
     * Funcion de eleccion de funcionalidad segun el Id del boton
     */
    private void function() {
        if (id == 0) {
            OffTheLineLogic.stateChanged = true;
            OffTheLineLogic.nextState = new PlayState(engine, 0);
        } else if (id == 1) {
            OffTheLineLogic.stateChanged = true;
            OffTheLineLogic.nextState = new PlayState(engine, 1);
        } else if (id == -1) {
            OffTheLineLogic.stateChanged = true;
            OffTheLineLogic.nextState = new MenuState(engine);
        }
    }

    /**
     * Comprobación de pulsación en el boton
     * @param events
     */
    @Override
    public void handleInput(List<Input.TouchEvent> events) {
        int size = events.size();

        for (int i = 0; i < size; i++) {
            Input.TouchEvent evt = events.get(i);

            if (evt.type == Input.TouchEvent.TOUCH_DOWN && // Tipo de evento: TOUCH_DOWN
                    evt.x < (offSetClickX1 * transform.getScaleX() + transform.getPosX()) && evt.x > (offSetClickX0 * transform.getScaleX() + transform.getPosX()) && // coor. x del evento dentro del botón
                    evt.y < (offSetClickY1 * transform.getScaleY() + transform.getPosY()) && evt.y > (offSetClickY0 * transform.getScaleY() + transform.getPosY())) // coor. y del evento dentro del botón
                clicked = true;
        }
    }
}
