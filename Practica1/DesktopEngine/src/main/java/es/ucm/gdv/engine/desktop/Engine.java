package es.ucm.gdv.engine.desktop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import es.ucm.gdv.engine.Game;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;


public class Engine implements es.ucm.gdv.engine.Engine{
    es.ucm.gdv.engine.desktop.Graphics g=null;
    es.ucm.gdv.engine.desktop.Input in=null;
    Game game=null;
    long lastTime;

    /**
     * Devuelve la instancia del motor gráfico.
     * @return
     */
    public Engine()
    {
        g= new es.ucm.gdv.engine.desktop.Graphics(640,480, this);
        in = new es.ucm.gdv.engine.desktop.Input(g.getCanvas(),g);
        lastTime=System.nanoTime();

    }

    /**
     * Establece el juego y las dimensiones lógicas.
     * @param game El juego que ejecutará el motor.
     */
    public void setGame(Game game)
    {
        this.game=game;
       // g.setScreenSize(game.getWidth(), game.getHeight());
        g.setTargetWidth(game.getWidth());
        g.setTargetHeight(game.getHeight());
        g.initCanvas();
    }

    public void mainLoop()
    {
        float deltaTime = getDeltaTime();
        while(true) {
            deltaTime = getDeltaTime();

            game.handleInput(); // HANDLE INPUT
            game.update(deltaTime); // UPDATE

            do {
                do {
                    g.updateBuffer();
                    try {
                        g.renderFrame(game); // RENDER
                    } finally {
                       g.GraphDispose();
                    }
                } while (g.BsRestore());
                g.Show();
            } while (g.BsContentLost());
        }
    }

    private float getDeltaTime()
    {

        long newTime=System.nanoTime();

        long timex=newTime-lastTime;
        float miliseconds=(float)(timex/1.0E9);
        lastTime=newTime;


        return miliseconds;
    }

    public Graphics getGraphics()
    {
        return g;
    }

    /**
     * Devuelve la instancia del gestor de entrada.
     * @return
     */
    public Input getInput()
    {
        return in;
    }

    /**
     * Devuelve un stream de lectura de un fichero.
     * @return
     */
    public InputStream openInputStream(String filename)
    {
        InputStream is=null;
        filename = "Resources/" + filename; // Añade "Resources/" a la ruta

        try {
           is = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }
}
