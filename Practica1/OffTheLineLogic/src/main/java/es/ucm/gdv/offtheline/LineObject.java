package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class LineObject extends  GameObject {

    public LineObject(Engine eng, int id )
    {
        super( eng,  id);
    }
    public void render ()
    {
        if(isActive)
        {
            if(engine!=null) {
                if (vertexX.length > 0 && vertexY.length > 0) {
                    engine.getGraphics().translate(transform.getPosX(),transform.getPosY());
                    engine.getGraphics().scale(transform.getScaleX(),transform.getScaleY());
                    engine.getGraphics().rotate(transform.getRotation());
                    engine.getGraphics().setColor(color);
                    for (int i = 0; i < vertexX.length - 1; i++) {
                        engine.getGraphics().drawLine(vertexX[i], vertexY[i], vertexX[i + 1], vertexY[i + 1]);
                    }
                    engine.getGraphics().restore();
                }
            }
        }
    }
}
