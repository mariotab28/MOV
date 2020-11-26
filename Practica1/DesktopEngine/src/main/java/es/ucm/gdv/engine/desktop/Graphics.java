package es.ucm.gdv.engine.desktop;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import java.io.InputStream;


import javax.swing.JFrame;

public class Graphics implements es.ucm.gdv.engine.Graphics {

    private JFrame frame;
    private java.awt.Graphics graphics;
    private BufferStrategy bufferStrategy;
    private Canvas canvas;


    private float savedX=0,savedY=0;
    private float savedScaleX=0,savedScaleY=0;
    private float savedRot=0;
    private Color savedColor=Color.BLACK;

    private Color actualColor;

    private int width, height;
    private float transX=0f,transY=0f;
    private float scaleX=1f,scaleY=1f;
    private float rotation=0;
    private String title ="newWindow";

    public Graphics(int _width,int _height) {
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
        } // while pidiendo la creación de la bufferStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        bufferStrategy = canvas.getBufferStrategy();


    }
    public void update()
    {

        do {
            do {
                graphics = bufferStrategy.getDrawGraphics();
                try {

                }
                finally {
                    graphics.dispose();
                }
            } while(bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while(bufferStrategy.contentsLost());

        graphics = bufferStrategy.getDrawGraphics();
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
        Font baseFont=new Font(filename,size,isBold, graphics);

        return baseFont;
    }

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    public void clear(int[] color) {
        graphics = bufferStrategy.getDrawGraphics();
        Color c=new Color(color[0],color[1],color[2]);
       // g.clearRect(0,0,width,height);
        graphics.setColor(c);
        graphics.fillRect(0,0,width,height);
    }

    public void clear(float[] color) {
        int[] auxColor = {(int)color[0]*255, (int)color[1]*255, (int)color[2]*255};
        clear(auxColor);
    }

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    public void translate(float x, float y) {
        transX+=x;
        transY+=y;
        //g.translate(x,y);
        graphics.translate((int)(transX*1/scaleX),(int)(transY*1/scaleY));
    }

    public void scale(float x, float y) {
        //canvas.setSize(x,y);
        scaleX*=x;
        scaleY*=y;
        graphics = bufferStrategy.getDrawGraphics();
        ((Graphics2D) graphics).scale(x,y);
        graphics.translate((int)(transX*1/scaleX),(int)(transY*1/scaleY));

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
        graphics.setColor(actualColor);
        scale(scaleX,scaleY);

    }

    //------------------------------------------------------------

    /**
     * Establece el color a utilizar en las operaciones de
     * dibujado posteriores.
     * @param color
     */
    public void setColor(int[] color) {
        actualColor=new Color(color[0],color[1],color[2]);
        graphics.setColor(actualColor);
    }

    public void setColor(float[] color) {
        int[] auxColor = {(int)color[0]*255, (int)color[1]*255, (int)color[2]*255};
        setColor(auxColor);
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
        graphics.setColor(actualColor);



        int nX1=((int)(((x1)*Math.cos(rotation)-(y1)*Math.sin(rotation))*1));

        int nY1=((int)(((x1)*Math.sin(rotation)+(y1)*Math.cos(rotation))*1));
        int nX2=((int)(((x2)*Math.cos(rotation)-(y2)*Math.sin(rotation))*1));
        int nY2=((int)(((x2)*Math.sin(rotation)+(y2)*Math.cos(rotation))*1));
        graphics.drawLine(nX1,nY1,nX2,nY2);

    }

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(int x1, int y1, int x2, int y2) {

        graphics.setColor(actualColor);
        int nX1=((int)(((x1)*Math.cos(rotation)-(y1)*Math.sin(rotation))*1));

        int nY1=((int)(((x1)*Math.sin(rotation)+(y1)*Math.cos(rotation))*1));
        int nX2=((int)(((x2)*Math.cos(rotation)-(y2)*Math.sin(rotation))*1));
        int nY2=((int)(((x2)*Math.sin(rotation)+(y2)*Math.cos(rotation))*1));
        graphics.fillRect(nX1,nY1,nX2,nY2);

    }

    /**
     * Escribe el texto con la fuente y color activos.
     * @param text
     * @param x
     * @param y
     */
    public void drawText(String text, int x, int y) {
        graphics.setColor(actualColor);


        graphics.drawString(text, x, y);

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