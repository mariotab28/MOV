package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class FullScreenChanger {
    private Engine eng;
    public FullScreenChanger(Engine engine) {
        eng=engine;
    }

    public void handleInput(List<Input.TouchEvent> events) {
        int size = events.size();

        for(int i=0; i < size; i++) {
            Input.TouchEvent evt = events.get(i);
            if (evt.type == Input.TouchEvent.KEY_DOWN && evt.pointer == Input.TouchEvent.Key_Code.F.ordinal()) {
                eng.getGraphics().toggleFullscreen();
            }
        }
    }
}
