package es.ucm.gdv.engine.desktop;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;
import java.util.List;

public class Input  implements es.ucm.gdv.engine.Input , MouseListener, MouseMotionListener {
    static Input instance=null;
    private final int NUM_BUTTONS=5;
    //private boolean[] buttons =new boolean[NUM_BUTTONS];
    //private boolean[] buttonsLast =new boolean[NUM_BUTTONS];
   // private int scaleX,scaleY;
    private int mouseX,mouseY;
    private List<TouchEvent> events;

    public static Input GetInput(Canvas canvas)
        {
        if(instance==null)
            instance=new Input(canvas);
        return instance;
    }

    private Input(Canvas canvas)
    {
        mouseX=0;
        mouseY=0;
        events= new LinkedList<TouchEvent>();
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }
    public void update()
    {

    }
    public List<TouchEvent> getTouchEvents()
    {

        return events;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX=(int)(mouseEvent.getX());
        mouseY=(int)(mouseEvent.getX());

        TouchEvent t=new TouchEvent();
        t.posX=(int)(mouseEvent.getX());
        t.posY=(int)(mouseEvent.getY());
        t.type=2;
        t.id=mouseEvent.getButton();
        events.add(t);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX=(int)(mouseEvent.getX());
        mouseY=(int)(mouseEvent.getX());

        TouchEvent t=new TouchEvent();
        t.posX=(int)(mouseEvent.getX());
        t.posY=(int)(mouseEvent.getY());
        t.type=2;
        t.id=-1;
        events.add(t);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent t=new TouchEvent();
        t.posX=mouseX;
        t.posY=mouseY;
        t.type=0;
        t.id=mouseEvent.getButton();
        events.add(t);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        TouchEvent t=new TouchEvent();
        t.posX=mouseX;
        t.posY=mouseY;
        t.type=1;
        t.id=mouseEvent.getButton();
        events.add(t);
    }
}
