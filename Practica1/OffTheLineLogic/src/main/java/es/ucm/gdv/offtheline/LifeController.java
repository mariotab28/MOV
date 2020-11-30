package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.offtheline.GameObject.MyTransform;

public class LifeController extends GameObject{

    Life[] lives;
    Engine engine;
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
