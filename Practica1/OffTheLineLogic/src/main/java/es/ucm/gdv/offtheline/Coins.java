package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Coins extends  LineObject {

    boolean collision;
    float acumScale;
    double angle=0;
    double speed=0;
    double radius=0;
    double posiniX=0;
    double posiniY=0;

    public Coins(Engine eng, int id )
    {
        super( eng,  id);
        color = 0xFFFF00;
        acumScale=1;
        collision=false;



        double[] verX= {-4,4,4,-4};
        double[] verY= {4,4,-4,-4};

        setVertex(verX,verY);
    }

    public Coins(Engine eng,int id,double posX,double posY)
    {

        super( eng,  id,posX,posY);
        posiniX=posX;
        posiniY=posY;

        color = 0xFFFF00;
        acumScale=1;
        collision=false;

        double[] verX= {-4,4,4,-4};
        double[] verY= {4,4,-4,-4};

        setVertex(verX,verY);

    }
/*
    public void updateVertex()
    {
        for(int i =0; i< vertexX.length;i++)
        {
            vertexX[i]=vertexX[i]+(radius*Math.cos((angle/180)*Math.PI));
            vertexY[i]=vertexY[i]+(radius*Math.sin((angle/180)*Math.PI));
        }
    }
*/
    @Override
    public void update(float deltaTime) {

        if(isActive) {
            angle= angle+speed*deltaTime;
            transform.setPosX((posiniX +radius*Math.sin((angle/180)*Math.PI)));
            transform.setPosY((posiniY +radius*Math.cos((angle/180)*Math.PI)));

            transform.setRotation(transform.getRotation() + ((float) Math.PI ) * deltaTime);


            if (collision) {
                acumScale = acumScale +6* deltaTime;
                transform.setScaleX( acumScale);
                transform.setScaleY(acumScale);

            }
            if(transform.getScaleX()>=4)
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
