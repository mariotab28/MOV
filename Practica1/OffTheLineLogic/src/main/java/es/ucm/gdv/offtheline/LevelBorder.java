package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class LevelBorder extends  LineObject {
    double []directionsX;
    double []directionsY;


    public LevelBorder(Engine eng, int id )
    {
        super( eng,  id);
        color[0]=1.0f;
        color[1]=1.0f;
        color[2]=1.0f;

    }

    public double[] getDirectionsX() {
        return directionsX;
    }

    public void setDirectionsX(double[] directionsX) {
        this.directionsX = directionsX;
    }

    public double[] getDirectionsY() {
        return directionsY;
    }

    public void setDirectionsY(double[] directionsY) {
        this.directionsY = directionsY;
    }

    public boolean hasDirections()
    {
        if(directionsX== null || directionsY== null ||directionsX.length==0 || directionsY.length==0)
            return false;
        else
            return true;
    }
}
