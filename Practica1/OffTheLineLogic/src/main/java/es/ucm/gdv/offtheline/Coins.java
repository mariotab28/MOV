package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Coins extends  LineObject {

    boolean collision;
    float acumScale;
    public Coins(Engine eng, int id )
    {
        super( eng,  id);
        color[0]=1.0f;
        color[1]=1.0f;
        color[2]=0.0f;
        acumScale=1;
        collision=false;
    }

    @Override
    public void update(float deltaTime) {

        if(isActive) {
            transform.setRotation(transform.getRotation() + ((float) Math.PI / 10) * deltaTime);

            engine.getGraphics().rotate(transform.getRotation());

            if (collision) {
                acumScale = acumScale + 1 * deltaTime;
                transform.setScaleX(transform.getScaleX() * acumScale);
            }
            if(acumScale==2)
            {
                isActive=false;
            }
        }
    }
}
