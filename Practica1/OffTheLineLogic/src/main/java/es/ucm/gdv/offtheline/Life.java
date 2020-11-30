package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Life extends  LineObject {

    boolean lifeOk;
    public Life(Engine eng, int id )
    {
        super( eng,  id);
        color = 0x0000FF;

    }

    public void Die()
    {
        lifeOk=false;
    }

    public boolean isLifeOk()
    {
        return lifeOk;
    }

}
