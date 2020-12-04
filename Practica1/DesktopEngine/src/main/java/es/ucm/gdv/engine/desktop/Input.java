package es.ucm.gdv.engine.desktop;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.ucm.gdv.engine.Pool;

public class Input  implements es.ucm.gdv.engine.Input, MouseListener, MouseMotionListener {
    private final int NUM_BUTTONS=5;
    //private boolean[] buttons =new boolean[NUM_BUTTONS];
    //private boolean[] buttonsLast =new boolean[NUM_BUTTONS];
   // private int scaleX,scaleY;
    private int mouseX,mouseY;
    //private List<TouchEvent> events;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();

    public Input(Canvas canvas)
    {
        Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 50);

        mouseX=0;
        mouseY=0;

        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }

    /**
     * Devuelve la lista de TouchEvents y libera el buffer de eventos.
     * @return Lista con los TouchEvents ocurridos desde la última llamada a este método.
     */
    public List<TouchEvent> getTouchEvents()
    {
        int len = touchEvents.size();
        for( int i = 0; i < len; i++ )
            touchEventPool.free(touchEvents.get(i));
        touchEvents.clear();
        touchEvents.addAll(touchEventsBuffer);
        touchEventsBuffer.clear();
        return touchEvents;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX=mouseEvent.getX();
        mouseY=mouseEvent.getY();

        TouchEvent t=new TouchEvent();
        t.x=mouseEvent.getX();
        t.y=mouseEvent.getY();
        t.type=TouchEvent.TOUCH_DRAGGED;
        t.pointer=mouseEvent.getButton();
        touchEventsBuffer.add(t);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        /*mouseX=(int)(mouseEvent.getX());
        mouseY=(int)(mouseEvent.getY());

        TouchEvent t=new TouchEvent();
        t.x=(int)(mouseEvent.getX());
        t.y=(int)(mouseEvent.getY());
        t.type=TouchEvent.TOUCH_DRAGGED;
        t.pointer=-1;
        touchEventsBuffer.add(t);*/
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent t=new TouchEvent();
        t.x=mouseEvent.getX();
        t.y=mouseEvent.getY();
        t.type=TouchEvent.TOUCH_DOWN;
        t.pointer=mouseEvent.getButton();
        touchEventsBuffer.add(t);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        TouchEvent t=new TouchEvent();
        t.x=mouseEvent.getX();
        t.y=mouseEvent.getY();
        t.type=TouchEvent.TOUCH_UP;
        t.pointer=mouseEvent.getButton();
        touchEventsBuffer.add(t);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return false;
    }

    @Override
    public int getTouchX(int pointer) {
        return 0;
    }

    @Override
    public int getTouchY(int pointer) {
        return 0;
    }

}
