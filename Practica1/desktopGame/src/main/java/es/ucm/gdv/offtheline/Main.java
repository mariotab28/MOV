package es.ucm.gdv.offtheline;

import java.awt.Canvas;
import java.awt.Color;
import java.util.List;


import es.ucm.gdv.engine.desktop.Graphics;
import es.ucm.gdv.engine.desktop.Input;

/**
 *
 */
public class Main {
    public static void main (String[] args)
    {

        Graphics g=new Graphics(640,480);
        Input i =new Input(g.getCanvas());

       // g.update();
        System.out.println("Hola buenas");

        float[] c=new float[3];
        c[0]=0.9f;
        c[1]=0.2f;
        c[2]=0.2f;
        g.clear(c);
        float angle=0;
        float scale=1;
        //g.scale(1.0f,1.0f);
        //g.save();
      
        while(true) {
            angle+=(float)Math.PI/10000;
            //if(scale<10f)
               // scale+=1f/100f;
            c[0]=0.9f;
            c[1]=0.2f;
            c[2]=0.2f;
            g.clear(c);

            c[0]=0.2f;
            c[1]=0.2f;
            c[2]=0.9f;



            g.setColor(c);
            //g.scale(1.0f,1.0f);

           // g.scale(6.0f,1.0f);
            g.rotate(angle);
            g.translate(400,100);
           // g.save();
            g.scale(scale,scale);



            g.setColor(c);
            g.drawLine(-50,-50,-50,50);
            //g.rotate(angle);

            //g.setColor(c);
           // g.translate(-100,100);


            g.drawLine(-50,50,50,50);
           // g.rotate(angle);

           // g.setColor(c);
            //g.translate(400,100);
            g.drawLine(50,50,50,-50);


            //g.setColor(c);
           // g.translate(400,100);
           // g.rotate(angle);
            g.drawLine(50,-50,-50,-50);

            g.restore();
            g.translate(100,400);
            g.scale(4.0f,4.0f);
            g.setColor(c);

            g.rotate(0);
            g.fillRect(-10,-10,10,10);

            g.restore();
            g.update();

            List<es.ucm.gdv.engine.Input.TouchEvent> ev= i.getTouchEvents();
            for (es.ucm.gdv.engine.Input.TouchEvent e:ev)
            {
                System.out.println(e.type);

            }
            ev.clear();


        }

    }
}