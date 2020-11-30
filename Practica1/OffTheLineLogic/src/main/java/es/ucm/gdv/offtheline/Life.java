package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Life extends  LineObject {

    public Life(Engine eng, int id ,double posX,double posY)
    {
        super( eng,  id,posX,posY);
        color = 0x007FFF;
        double [] verticesX= {-6,6,6,-6};
        double [] verticesY= {-6,-6,6,6};

        vertexX=verticesX;
        vertexY=verticesY;

    }

    public void Die()
    {

        double [] verticesX= {0,-6,6,0,-6,6};
        double [] verticesY= {0,-6,6,0,6,-6};

        vertexX=verticesX;
        vertexY=verticesY;
        setColor(0xFF0000);

    }


}
