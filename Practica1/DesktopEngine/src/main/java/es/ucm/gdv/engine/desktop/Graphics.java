package es.ucm.gdv.engine.desktop;




import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import java.io.InputStream;
import java.math.MathContext;


import javax.swing.JFrame;

import es.ucm.gdv.engine.Game;

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
        Dimension sMIN=new Dimension(0, 0);
        Dimension sMAX=new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

        canvas.setMinimumSize(sMIN);
        canvas.setMaximumSize(s);
        canvas.setPreferredSize(s);

        frame = new JFrame(title);
        frame.setSize((int) (screenWidth), (int) (screenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.add(canvas,BorderLayout.CENTER);
        //frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.addComponentListener(new FrameListener(this));

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

        Dimension s=new Dimension(screenWidth, screenHeight);


        canvas.setMinimumSize(s);
        canvas.setMaximumSize(s);
        canvas.setPreferredSize(s);
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    /**
     * Renderiza el siguiente frame del juego.
     * @param game El juego con la lista de objetos a renderizar.
     */
    public void renderFrame(Game game) {
        // Traslación del canvas hacia el centro de la pantalla
        //translate(canvasXOffset, canvasYOffset);
        restore();
        // Escalado del canvas


        // Renderizado de objetos del juego
        game.render();
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

        ((Graphics2D)graphics).translate((transX /scaleX),(transY /scaleY));
    }

    public void scale(float x, float y) {
        //canvas.setSize(x,y);
        scaleX=x*scaleFactor;
        scaleY=y*scaleFactor;
        graphics = bufferStrategy.getDrawGraphics();
        if(graphics!=null) {
            ((Graphics2D) graphics).scale(scaleX, scaleY);
            ((Graphics2D) graphics).translate((transX * scaleFactor / scaleX), (transY *scaleFactor / scaleY));
        }

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

    private void completeRestore()
    {
        actualColor=Color.BLACK;
        rotation=0;
        scaleX=1;
        scaleY=1;
        transX=0;
        transY=0;
        graphics.setColor(actualColor);
       // scale(scaleX,scaleY);
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

    // *********************************************************
    //                  FRAME LISTENER
    // *********************************************************

    /**
     * Escucha eventos sobre la ventana.
     */
    private class FrameListener implements ComponentListener {
        private Graphics graphics;

        public FrameListener(Graphics graphics)
        {
            this.graphics = graphics;

        }

        /**
         * Función que se llama al cambiar el tamaño de la ventana.
         * Actualiza las variables que ayudan a posicionar el canvas
         * en el centro de la ventana.
         */
        public void componentResized(ComponentEvent arg0) {
            if(targetWidth == 0 || targetHeight == 0) return; // Todavía no se han establecido las dimensiones del juego

            int w = frame.getWidth(), h = frame.getHeight();

            graphics.setScreenSize(w, h);
            graphics.initCanvas();
            completeRestore();
            translate(canvasXOffset*1/scaleFactor , canvasYOffset*1/scaleFactor );
            scaleX=scaleFactor;
            scaleY=scaleFactor;

            save();
        }

        public void componentHidden(ComponentEvent arg0) {
        }
        public void componentMoved(ComponentEvent arg0) {
        }
        public void componentShown(ComponentEvent arg0) {
        }
    }
}