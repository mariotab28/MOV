package es.ucm.gdv.engine.android;


import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import es.ucm.gdv.engine.AbstractGraphics;
import es.ucm.gdv.engine.Input.TouchEvent;
import es.ucm.gdv.engine.Pool;
import es.ucm.gdv.engine.Pool.PoolObjectFactory;

public class TouchHandler implements OnTouchListener {
    boolean isTouched;
    int touchX;
    int touchY;
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    AbstractGraphics graphics;

    public TouchHandler(View view, AbstractGraphics graphics) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);
        this.graphics = graphics;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            TouchEvent touchEvent = touchEventPool.getObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    isTouched = false;
                    break;
            }

            touchEvent.x = touchX = (int) graphics.adjustToTargetWidth(event.getX());
            touchEvent.y = touchY = (int) graphics.adjustToTargetHeight(event.getY());
            touchEventsBuffer.add(touchEvent);

            return true;
        }
    }

    public boolean isTouchDown(int pointer) {
        synchronized(this) {
            if(pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    public int getTouchX(int pointer) {
        synchronized(this) {
            return touchX;
        }
    }

    public int getTouchY(int pointer) {
        synchronized(this) {
            return touchY;
        }
    }

    public List<TouchEvent> getTouchEvents() {
        synchronized(this) {
            int len = touchEvents.size();
            for( int i = 0; i < len; i++ )
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}

