package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Enemy extends  LineObject {

    boolean collision;
    float acumScale;
    double angle=0;
    double speed=0;
    double length=0;
    public Enemy(Engine eng, int id )
    {
        super( eng,  id);
        color[0]=1.0f;
        color[1]=0.0f;
        color[2]=0.0f;
        acumScale=1;
        collision=false;



        double[] verX= {1,-1};
        double[] verY= {1,-1};

        setVertex(verX,verY);
    }

    public Enemy(Engine eng,int id,int posX,int posY)
    {

        super( eng,  id,posX,posY);

        color[0]=1.0f;
        color[1]=0.0f;
        color[2]=0.0f;
        acumScale=1;
        collision=false;

        double[] verX= {-5,5,5,-5};
        double[] verY= {5,5,-5,-5};

        setVertex(verX,verY);

    }

    public void updateVertex()
    {

        double[] verX= {1,-1};
        double[] verY= {1,-1};
        for(int i =0; i< verX.length;i++)
        {
            verX[i]=verX[i]*((length/2)*Math.cos((angle/180)*Math.PI));
            verY[i]=verY[i]*((length/2)*Math.sin((angle/180)*Math.PI));
        }
        setVertex(verX,verY);
    }

    @Override
    public void update(float deltaTime) {

        if(isActive) {
            angle= angle+speed*deltaTime;
            transform.setRotation((float)((angle/180)*Math.PI));

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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
