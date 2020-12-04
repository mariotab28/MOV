package es.ucm.gdv.engine.desktop;




import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import java.io.InputStream;


import javax.swing.JFrame;

public class Graphics extends es.ucm.gdv.engine.AbstractGraphics {
    private JFrame frame;
    private java.awt.Graphics graphics;

    private BufferStrategy bufferStrategy;
    private Canvas canvas;

    private float savedX=0,savedY=0;
    private float savedScaleX=1,savedScaleY=1;
    private float savedRot=0;
    private Color savedColor=Color.BLACK;

    private Color actualColor;

    private float transX=0f,transY=0f;
    private float scaleX=1f,scaleY=1f;
    private float rotation=0;
    private String title ="newWindow";
    private Engine engine;


    public void GraphDispose() {

        graphics.dispose();
    }

    public void Show() {

        bufferStrategy.show();
    }

    public boolean BsRestore() {
        return bufferStrategy.contentsRestored();
    }

    public boolean BsContentLost() {
        return bufferStrategy.contentsLost();
    }

    public Graphics(int _width,int _height, Engine engine) {
        screenWidth = _width;
        screenHeight = _height;
        this.engine = engine;

        canvas=new Canvas();
        Dimension s=new Dimension(screenWidth, screenHeight);
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

        graphics = bufferStrategy.getDrawGraphics();

    }

    /**
     * Establece las dimensiones de la ventana.
     * @param w Ancho de la ventana.
     * @param h Alto de la ventana.
     */
    public void setScreenSize(int w, int h) {
        screenWidth = w;
        screenHeight = h;
        frame.setSize(w, h);
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public void updateBuffer()
    {
        graphics = bufferStrategy.getDrawGraphics();
    }

    /**
     *  Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente
     * en negrita.
     * @param fileName
     * @param size
     * @param isBold
     * @return
     */
    public Font newFont(String fileName, float size, boolean isBold) {
        Font baseFont=new Font(engine.openInputStream(fileName), size, isBold);

        return baseFont;
    }


    @Override
    public void setFont(es.ucm.gdv.engine.Font font) {

        java.awt.Font help= ((es.ucm.gdv.engine.desktop.Font) font).getFont();

        graphics.setFont(help);

    }

    /**
     * Borra el contenido completo de la ventana, rellenándolo
     * con un color recibido como parámetro.
     * @param color
     */
    public void clear(int color) {
        graphics = bufferStrategy.getDrawGraphics();
        Color c=new Color(color);
       // g.clearRect(0,0,width,height);
        graphics.setColor(c);
        graphics.fillRect(0,0,screenWidth,screenHeight);
    }

    //------------------------------------------------------------
    //  Métodos de control de la transformación sobre el canvas
    //------------------------------------------------------------

    public void translate(double x, double y) {
        transX+=x;
        transY+=y;

        ((Graphics2D)graphics).translate((transX*1/scaleX),(transY*1/scaleY));
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
    public void setColor(int color) {
        actualColor = new Color(color);
        graphics.setColor(actualColor);
    }

    /**
     * Dibuja una línea en patalla
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(double x1, double y1, double x2, double y2) {
        // g.translate(0,0);
        graphics.setColor(actualColor);



        double nX1=((((x1)*Math.cos(rotation)-(y1)*Math.sin(rotation))*1));

        double nY1=((((x1)*Math.sin(rotation)+(y1)*Math.cos(rotation))*1));
        double nX2=((((x2)*Math.cos(rotation)-(y2)*Math.sin(rotation))*1));
        double nY2=((((x2)*Math.sin(rotation)+(y2)*Math.cos(rotation))*1));

        ((Graphics2D)graphics).setStroke(new BasicStroke(2));
        ((Graphics2D)graphics).draw(new Line2D.Double(nX1,nY1,nX2,nY2));
        //g.drawLine(nX1,nY1,nX2,nY2);

        //g.drawRect();

    }

    /**
     * Dibuja un rectángulo relleno.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(double x1, double y1, double x2, double y2) {

        graphics.setColor(actualColor);
        double nX1=((((x1)*Math.cos(rotation)-(y1)*Math.sin(rotation))*1));

        double nY1=((((x1)*Math.sin(rotation)+(y1)*Math.cos(rotation))*1));
        double nX2=((((x2)*Math.cos(rotation)-(y2)*Math.sin(rotation))*1));
        double nY2=((((x2)*Math.sin(rotation)+(y2)*Math.cos(rotation))*1));
        //g.fillRect(nX1,nY1,nX2,nY2);
        ((Graphics2D)graphics).fill(new Rectangle2D.Double(nX1,nY1,nX2,nY2));

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


        ((Graphics2D)graphics).drawString(text, x, y);
        //graphics.drawString(text, x, y);

    }



}