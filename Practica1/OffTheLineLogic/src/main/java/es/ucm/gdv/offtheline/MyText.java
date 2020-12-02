package es.ucm.gdv.offtheline;

import java.io.InputStream;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;

public class MyText extends  GameObject {

    Font font;
    String text;
    int offSetx;
    int offSetY;

    public MyText(Engine eng, int id )
    {
        super( eng,  id);
        text ="";
        offSetx=0;
        offSetY=0;


    }

    public void render ()
    {
        if(isActive)
        {
            if(engine!=null) {
                engine.getGraphics().save();
               // font.setFont();


                engine.getGraphics().translate(transform.getPosX(),transform.getPosY());
                engine.getGraphics().scale(transform.getScaleX(),transform.getScaleY());
                engine.getGraphics().rotate(transform.getRotation());
                engine.getGraphics().setColor(color);

                engine.getGraphics().setFont(font);
                engine.getGraphics().drawText(text,offSetx,offSetY);
                engine.getGraphics().restore();

            }
        }
    }


    public void setFont(Font font)
    {
        this.font=font;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOffSetx() {
        return offSetx;
    }

    public void setOffSetx(int offSetx) {
        this.offSetx = offSetx;
    }

    public int getOffSetY() {
        return offSetY;
    }

    public void setOffSetY(int offSetY) {
        this.offSetY = offSetY;
    }


}
