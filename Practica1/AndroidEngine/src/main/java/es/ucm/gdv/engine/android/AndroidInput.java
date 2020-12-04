package es.ucm.gdv.engine.android;

import java.util.List;

import android.view.View;

import es.ucm.gdv.engine.AbstractGraphics;
import es.ucm.gdv.engine.Input;

public class AndroidInput implements Input {
    TouchHandler touchHandler;

    public AndroidInput(View view, AbstractGraphics graphics) {
        touchHandler = new TouchHandler(view, graphics);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }
    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }
    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }
    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
}

