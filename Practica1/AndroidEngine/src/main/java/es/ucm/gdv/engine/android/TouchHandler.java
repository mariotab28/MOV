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
    private boolean isTouched;
    private int touchX;
    private int touchY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    private AbstractGraphics graphics;

    public TouchHandler(View view, AbstractGraphics graphics) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 50);
        view.setOnTouchListener(this);
        this.graphics = graphics;
    }

    /**
     * Método heredado de OnTouchListener que se llama
     * al recibir input en la pantalla táctil
     * @param v Vista de la aplicación
     * @param event Evento recibido
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            TouchEvent touchEvent = touchEventPool.getObject();
            // Establece el tipo de evento
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

            // Coordenadas del evento
            touchEvent.x = touchX = (int) graphics.adjustToTargetWidth(event.getX());
            touchEvent.y = touchY = (int) graphics.adjustToTargetHeight(event.getY());
            // Añade el evento al buffer de eventos
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

    /**
     * Devuelve la lista de TouchEvents y libera el buffer de eventos.
     * @return Lista con los TouchEvents ocurridos desde la última llamada a este método.
     */
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

