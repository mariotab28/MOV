package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class LineObject extends  GameObject {

    public LineObject(Engine eng, int id )
    {
        super( eng,  id);
    }

    public LineObject(Engine eng,int id,double posX,double posY)
    {
        super( eng,  id,posX,posY);
    }

    /**
     * Funcion de renderizado de los objetos que se dibujen con lineas
     */
    public void render ()
    {
        if(isActive)
        {
            if(engine!=null) {
                if (vertexX.length > 0 && vertexY.length > 0) {
                    engine.getGraphics().save();
                    engine.getGraphics().translate(transform.getPosX(),transform.getPosY());
                    engine.getGraphics().scale(transform.getScaleX(),transform.getScaleY());
                    engine.getGraphics().rotate(transform.getRotation());
                    engine.getGraphics().setColor(color);
                    for (int i = 0; i < vertexX.length ; i++) {
                        if(i+1>=vertexX.length)
                            engine.getGraphics().drawLine(vertexX[i], vertexY[i], vertexX[0], vertexY[0]);
                        else
                            engine.getGraphics().drawLine(vertexX[i], vertexY[i], vertexX[i + 1], vertexY[i + 1]);
                    }
                    engine.getGraphics().restore();
                }
            }
        }
    }
}
