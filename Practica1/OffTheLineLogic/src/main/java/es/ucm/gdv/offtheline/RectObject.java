package es.ucm.gdv.offtheline;
import es.ucm.gdv.engine.Engine;

public class RectObject extends  GameObject {

    public RectObject(Engine eng, int id )
    {
        super( eng,  id);
    }

    public RectObject(Engine eng,int id, double x1, double y1, double x2, double y2)
    {
        super( eng, id, x1, y1);
        // Establece los vértices superior-izquierdo e inferior-derecho del rectángulo
        double[] verX= {x1, x2};
        double[] verY= {y1, y2};
        setVertex(verX,verY);
    }

    /**
     * Render de los objetos que pintan un rectangulo coloreado
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

                    // Pinta un rectángulo
                    engine.getGraphics().fillRect(vertexX[0], vertexY[0], vertexX[1], vertexY[1]);

                    engine.getGraphics().restore();
                }
            }
        }
    }
}

