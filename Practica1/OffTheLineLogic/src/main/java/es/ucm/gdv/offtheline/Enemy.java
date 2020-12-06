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

    /**
     * Creacion de instancia de enemy sin posicion inicial
     * @param eng
     * @param id
     */
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

    /**
     * Creación de la instancia del enemy
     * @param eng
     * @param id
     * @param posX
     * @param posY
     */
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

    /**
     * Actualiza los vertices del enemigo a unos especificos con la longitud y angulos correctos.
     * Esta funcion se debe llamar tras las funciones de set correspondientes a la longitud y angulo.
     */
    public void updateVertex()
    {

        double[] verX= {1.0,-1.0};
        double[] verY= {1.0,-1.0};
        for(int i =0; i< verX.length;i++)
        {
            verX[i]=verX[i]*((int)((length-1)/2)*Math.cos((angle/180)*Math.PI));
            verY[i]=verY[i]*((int)((length-1)/2)*Math.sin((-angle/180)*Math.PI));
        }
        setVertex(verX,verY);
    }

    /**
     * Comportamiento del Enemy respecto a su movimiento y rotación.
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {

        if(isActive) {
            double rot= speed*deltaTime;
            transform.setRotation(transform.getRotation()+(float)((rot/180.0)*Math.PI));
            if(counter<time1 && moving)
            {
                transform.setPosX(transform.getPosX()+offsetX *1/time1 * direction*deltaTime);
                transform.setPosY(transform.getPosY()+offsetY *1/time1* direction*deltaTime);

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

    /**
     * Getter de angulo
     * @return
     */
    public double getAngle() {
        return angle;
    }
    /**
     * Setter de angulo
     * @return
     */
    public void setAngle(double angle) {
        this.angle = angle;

    }

    /**
     * Getter de velocidad
     * @return
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter de velocidad
     * @return
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter de longitud
     * @return
     */
    public double getLength() {
        return length;
    }

    /**
     * Setter de longitud
     * @return
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Setter de posicion a la que tiene que llegar en la X
     * @return
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Setter de posicion a la que tiene que llegar en la Y
     * @return
     */
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Setter del tiempo que tarda en llegar al destino
     * @return
     */
    public void setTime1(double time1) {
        this.time1 = time1;
    }

    /**
     * Setter del tiempo que dura en el destino antes de volver
     * @return
     */
    public void setTime2(double time2) {
        this.time2 = time2;
    }
}
