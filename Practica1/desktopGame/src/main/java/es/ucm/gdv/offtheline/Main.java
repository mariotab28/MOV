package es.ucm.gdv.offtheline;

import java.awt.Canvas;
import java.awt.Color;
import java.util.List;


import es.ucm.gdv.engine.desktop.Engine;
import es.ucm.gdv.engine.desktop.Graphics;
import es.ucm.gdv.engine.desktop.Input;

/**
 *
 */
public class Main {
    public static void main (String[] args)
    {
        boolean ok=false;
        Engine engine=new Engine();
        OffTheLineLogic logic=new OffTheLineLogic(engine);
        engine.setGame(logic);
        logic.loadFont(); //TODO: Esto está bien?
        while(true) //TODO: NO HACE FALTA ESTE WHILE
        {

            engine.UpdateEngine();


        }

       // g.update();

     }

}
