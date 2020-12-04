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

    public void setOffSetClick(double ofx1, double ofy1, double ofx2, double ofy2) {
        offSetClickX0 = ofx1;
        offSetClickY0 = ofy1;
        offSetClickX1 = ofx2;
        offSetClickY1 = ofy2;
    }

    public void update(float deltaTime) {
        if (isActive) {
            handleInput();
            if (clicked)
                function();
        }
    }

    public void function() {
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

    public void handleInput() {
        List<Input.TouchEvent> touchEvents = engine.getInput().getTouchEvents();
        int size = touchEvents.size();
        for (int i = 0; i < size; i++) {
            Input.TouchEvent ev = touchEvents.get(i);
            System.out.println("(x,y): " + ev.x + ", " + ev.y);
            System.out.println("FROM: " + (offSetClickX0 * transform.getScaleX() + transform.getPosX()) + ", " + (offSetClickY0 * transform.getScaleY() + transform.getPosY()));
            System.out.println("TO: " + (offSetClickX1 * transform.getScaleX() + transform.getPosX()) + ", " + (offSetClickY1 * transform.getScaleY() + transform.getPosY()));
            if (ev.type == Input.TouchEvent.TOUCH_DOWN && // Tipo de evento: TOUCH_DOWN
                    ev.x < (offSetClickX1 * transform.getScaleX() + transform.getPosX()) && ev.x > (offSetClickX0 * transform.getScaleX() + transform.getPosX()) && // coor. x del evento dentro del botón
                    ev.y < (offSetClickY1 * transform.getScaleY() + transform.getPosY()) && ev.y > (offSetClickY0 * transform.getScaleY() + transform.getPosY())) // coor. y del evento dentro del botón
                clicked = true;
        }
    }
}
