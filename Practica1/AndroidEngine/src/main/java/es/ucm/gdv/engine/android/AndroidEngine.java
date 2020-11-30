package es.ucm.gdv.engine.android;

import android.graphics.Canvas;

import java.io.InputStream;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Game;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class AndroidEngine implements Engine, Runnable {
    AndroidGraphics graphics = null;
    AndroidInput input = null;
    Game game = null;
    private boolean _running = false;
    private Thread _thread;

    public AndroidEngine()
    {

    }

    public void setGame(Game game)
    {
        this.game = game;
    }


    public void mainLoop()
    {

        // Bucle principal

        while(true) {


            game.update(0.0f);
            game.render();
        }
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public InputStream openInputStream(String filename) {
        return null;
    }

    //- - - - - - - - - - - - - R U N - - - - - - - - - - - - - - - -

    /**
     * Bucle principal del motor de juego. Se encarga de llamar
     * a los métodos de actualización y renderizado de la lógica.
     * Es ejecutado en otra hebra.
     */
    @Override
    public void run() {

        if (_thread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");
        }

        // Antes de saltar a la simulación, confirmamos que tenemos
        // un tamaño mayor que 0. Si la hebra se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while(_running && graphics.getWidth() == 0)
            ;

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle principal.
        while(_running) {

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            float elapsedTime = (float) (nanoElapsedTime / 1.0E9);

            game.update(elapsedTime); //UPDATE

            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            graphics.renderFrame(game);

        } // while
    } // run

    //- - - - - - P A U S E - - & - - R E S U M E - - - - - - - - - - -

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {

        if (!_running) {
            _running = true;
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _thread = new Thread(this);
            _thread.start();
        } // if (!_running)

    } // resume

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            while (true) {
                try {
                    _thread.join();
                    _thread = null;
                    break;
                } catch (InterruptedException ie) {
                    // ...
                }
            } // while(true)
        } // if (_running)

    } // pause
}
