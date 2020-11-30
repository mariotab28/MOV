package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Enemy extends  LineObject {

    boolean collision;
    float acumScale;
    double angle=0;
    double speed=0;
    double length=0;

    double offsetX=0;
    double offsetY=0;
    double time1=0;
    double time2=0;
    boolean moving=true;

    int direction=1;

    double counter=0;
    public Enemy(Engine eng, int id )
    {
        super( eng,  id);
        color = 0xFF0000;
        acumScale=1;
        collision=false;



        double[] verX= {1,-1};
        double[] verY= {1,-1};

        setVertex(verX,verY);
    }

    public Enemy(Engine eng,int id,double posX,double posY)
    {

        super( eng,  id,posX,posY);

        color = 0xFF0000;
        acumScale=1;
        collision=false;

        double[] verX= {-5,5,5,-5};
        double[] verY= {5,5,-5,-5};
        counter=0;
        setVertex(verX,verY);

    }

    public void updateVertex()
    {

        double[] verX= {1,-1};
        double[] verY= {1,-1};
        for(int i =0; i< verX.length;i++)
        {
            verX[i]=verX[i]*((length/2)*Math.cos((angle/180)*Math.PI));
            verY[i]=verY[i]*((length/2)*Math.sin((-angle/180)*Math.PI));
        }
        setVertex(verX,verY);
    }

    @Override
    public void update(float deltaTime) {

        if(isActive) {
            double rot= speed*deltaTime;
            transform.setRotation(transform.getRotation()+(float)((rot/180.0)*Math.PI));
            if(counter<time1 && moving)
            {
                transform.setPosX(transform.getPosX()+offsetX * direction*deltaTime);
                transform.setPosY(transform.getPosY()+offsetY * direction*deltaTime);

            }
            else if(counter>= time1&& moving) {
                counter=0;
                moving = false;
            }
            if(counter>=time2 && !moving)
            {
                moving=true;
                counter=0;
                direction=-direction;
            }

            counter+=deltaTime;
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

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setTime1(double time1) {
        this.time1 = time1;
    }

    public void setTime2(double time2) {
        this.time2 = time2;
    }
}
