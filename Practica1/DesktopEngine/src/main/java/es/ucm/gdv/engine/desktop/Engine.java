package es.ucm.gdv.engine.desktop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;


public class Engine implements es.ucm.gdv.engine.Engine{
    es.ucm.gdv.engine.desktop.Graphics g=null;
    es.ucm.gdv.engine.desktop.Input in=null;
    
    /**
     * Devuelve la instancia del motor gráfico.
     * @return
     */
    public Engine()
    {
        g= es.ucm.gdv.engine.desktop.Graphics.GetGraphics(640,480);
        in =es.ucm.gdv.engine.desktop.Input.GetInput(g.getCanvas());

    }

    public void UpdateEngine()
    {
        while(true) {
            //deltaTime = calcularDeltaTime;
            //update(deltaTime);
            do {
                do {
                    g.updateG();
                    try {
                        //render
                    } finally {
                       g.GraphDispose();
                    }
                } while (g.BsRestore());
                g.Show();
            } while (g.BsContentLost());
        }
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
        try {
           is = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }
}
