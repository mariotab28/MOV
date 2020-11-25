package es.ucm.gdv.engine.desktop;


import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import java.awt.image.BufferedImage;
import java.io.InputStream;


import javax.swing.JFrame;

import es.ucm.gdv.engine.desktop.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {
    static Graphics instance=null;
    private JFrame frame;
    private java.awt.Graphics g;

    public void GraphDispose() {

        g.dispose();
    }
    public void Show() {

        bs.show();
    }
    public boolean BsRestore() {
        return bs.contentsRestored();
    }
    public boolean BsContentLost() {
        return bs.contentsLost();
    }

    private BufferStrategy bs;
    private Canvas canvas;


    private float savedX=0,savedY=0;
    private float savedScaleX=1,savedScaleY=1;
    private float savedRot=0;
    private Color savedColor=Color.BLACK;

    private Color actualColor;

    private int width, height;
    private float transX=0f,transY=0f;
    private float scaleX=1f,scaleY=1f;
    private float rotation=0;
    private String title ="newWindow";

    public static Graphics GetGraphics(int _width,int _height)
    {
        if(instance==null)
            instance=new Graphics( _width, _height);
        return instance;
    }

    private Graphics(int _width,int _height) {
        width = _width;
        height = _height;


        canvas=new Canvas();
        Dimension s=new Dimension(width,height);
        canvas.setMinimumSize(s);
        canvas.setMaximumSize(s);
        canvas.setPreferredSize(s);

        frame = new JFrame(title);
       // frame.setSize((int) (width * scale), (int) (height * scale));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.add(canvas,BorderLayout.CENTER);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        int intentos = 100;
        while(intentos-- > 0) {
            try {
                canvas.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        bs=canvas.getBufferStrategy();


    }
    public Canvas getCanvas()
    {
        return canvas;
    }
    public void updateG()
    {


        g=bs.getDrawGraphics();
        //g.dispose();
        //frame.paint(g);
        //bs.show();
    }
    /**
     *  Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente
     * en negrita.
     * @param filename
     * @param size
     * @param isBold
     * @return
     */
    public Font newFont(InputStream filename, int size, boolean isBold) {
        Font baseFont=new Font(filename,size,isBold, g);

        return baseFont;
    }

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    public void clear(float[] color) {
        g=bs.getDrawGraphics();
        Color c=new Color(color[0],color[1],color[2]);
       // g.clearRect(0,0,width,height);
        g.setColor(c);
        g.fillRect(0,0,width,height);



    }

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    public void translate(int x, int y) {
        transX+=x;
        transY+=y;
        //g.translate(x,y);
        g.translate((int)(transX*1/scaleX),(int)(transY*1/scaleY));
    }

    public void scale(float x, float y) {
        //canvas.setSize(x,y);
        scaleX*=x;
        scaleY*=y;
        g=bs.getDrawGraphics();
        ((Graphics2D)g).scale(x,y);
        g.translate((int)(transX*1/scaleX),(int)(transY*1/scaleY));

    }

    public void rotate(float angle) {
        rotation=angle;
        // g=bs.getDrawGraphics();
        // ((Graphics2D)g).rotate(angle);

    }

    public void save() {
        savedColor=actualColor;
        savedRot=rotation;
        savedScaleX=scaleX;
        savedScaleY=scaleY;
        savedX=transX;
        savedY=transY;

    }

    public void restore() {
        actualColor=savedColor;
        rotation=savedRot;
        scaleX=savedScaleX;
        scaleY=savedScaleY;
        transX=savedX;
        transY=savedY;
        g.setColor(actualColor);
        scale(scaleX,scaleY);

    }

    //------------------------------------------------------------

    /**
     * Establece el color a utilizar en las operaciones de
     * dibujado posteriores.
     * @param color
     */
    public void setColor(float[] color) {
        actualColor=new Color(color[0],color[1],color[2]);
        g.setColor(actualColor);
    }

    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
       // g.translate(0,0);
        g.setColor(actualColor);



        int nX1=((int)(((x1)*Math.cos(rotation)-(y1)*Math.sin(rotation))*1));

        int nY1=((int)(((x1)*Math.sin(rotation)+(y1)*Math.cos(rotation))*1));
        int nX2=((int)(((x2)*Math.cos(rotation)-(y2)*Math.sin(rotation))*1));
        int nY2=((int)(((x2)*Math.sin(rotation)+(y2)*Math.cos(rotation))*1));
        g.drawLine(nX1,nY1,nX2,nY2);

    }

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(int x1, int y1, int x2, int y2) {

        g.setColor(actualColor);
        int nX1=((int)(((x1)*Math.cos(rotation)-(y1)*Math.sin(rotation))*1));

        int nY1=((int)(((x1)*Math.sin(rotation)+(y1)*Math.cos(rotation))*1));
        int nX2=((int)(((x2)*Math.cos(rotation)-(y2)*Math.sin(rotation))*1));
        int nY2=((int)(((x2)*Math.sin(rotation)+(y2)*Math.cos(rotation))*1));
        g.fillRect(nX1,nY1,nX2,nY2);

    }

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    public void drawText(String text, int x, int y) {
        g.setColor(actualColor);


        g.drawString(text, x, y);

    }

    /**
     * Devuelven el tamaño de la ventana.
     * @return
     */
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

}