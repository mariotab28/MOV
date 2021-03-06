package es.ucm.gdv.offtheline;

import java.util.Random;

import es.ucm.gdv.engine.Engine;

public class Particle extends  LineObject {
    double angle=0;
    double speed=0;
    double randDirX=0,randDirY=0;
    double timer=0;

    /**
     * Devuelve la instancia de Particle sin posición definida
     * @param eng
     * @param id
     */
    public Particle(Engine eng, int id )
    {
        super( eng,  id);
        color = 0xFFFF00;

        double[] verX= {-4,4,4,-4};
        double[] verY= {4,4,-4,-4};

        setVertex(verX,verY);
    }

    /**
     * Devuelve la instancia de Particle
     * @param eng
     * @param id
     * @param posX
     * @param posY
     */
    public Particle(Engine eng,int id,double posX,double posY)
    {
        super( eng,  id,(int)posX,(int)posY);

        color = 0x007FFF;

        double[] verX= {-3,3};
        double[] verY= {3,3};
        Random r= new Random();
        angle=r.nextDouble()* 2*Math.PI;

        randDirX=r.nextDouble()*2 -1;
        randDirY=r.nextDouble()*2 -1;

        speed = r.nextDouble()*100 ;

        transform.setRotation((float)angle);

        setVertex(verX,verY);

    }

    /**
     * Actualiza la particula según su comportamiento
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {


        if(isActive) {

            transform.setPosX((transform.getPosX() + ((speed*deltaTime) * (randDirX))));
            transform.setPosY((transform.getPosY() + ((speed*deltaTime) * (randDirY))));


            //engine.getGraphics().rotate(transform.getRotation());

            if(timer>0.75)
                setActive(false);
            timer+=deltaTime;

        }
    }


}
