package es.ucm.gdv.offtheline;


import es.ucm.gdv.engine.desktop.Engine;

/**
 *
 */
public class Main {
    public static void main (String[] args)
    {
        Engine engine = new Engine();
        OffTheLineLogic logic = new OffTheLineLogic(engine);
        engine.setGame(logic);

        // Inicio del bucle principal del motor
        engine.mainLoop();
     }

}
