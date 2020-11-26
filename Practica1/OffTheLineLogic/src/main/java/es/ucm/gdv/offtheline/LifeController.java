package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.offtheline.GameObject.MyTransform;

public class LifeController extends GameObject{

    Life[] lives;
    Engine engine;
    public LifeController(int numOfLives, Engine eng)
    {
        engine=eng;
        lives=new Life[numOfLives];
        for(int i =0;i<numOfLives;i++)
        {
            lives[i]=new Life(engine,5);
        }
    }
    public void update(int deltaTime)
    {

    }
    public void render()
    {

    }
}
