package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.offtheline.GameObject.MyTransform;

public class LifeController extends GameObject{

    Life[] lives;
    Engine engine;

    /**
     * Constructora de el controlador de las vidas en la interfaz. Comunica con las vidas y las crea para cambiarlas de color y forma indicando cuantas quedan
     * @param numOfLives
     * @param remainingLifes
     * @param eng
     * @param posX
     * @param posY
     */
    public LifeController(int numOfLives,int remainingLifes, Engine eng,double posX,double posY)
    {
        id=6;
        engine=eng;
        lives=new Life[numOfLives];
        for(int i =0;i<numOfLives;i++)
        {
            lives[i]=new Life(engine,5,posX+18*i,posY);
            if(i>=remainingLifes)
                lives[i].Die();

        }
    }
    public void render()
    {
        for(int i =0;i<lives.length;i++)
        {
            lives[i].render();
        }
    }
}
