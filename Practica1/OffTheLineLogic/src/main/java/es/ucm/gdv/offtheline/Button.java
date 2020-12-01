package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class Button extends MyText{
    double offSetClickX0=0,offSetClickY0=0,offSetClickX1=0,offSetClickY1=0;
    boolean clicked=false;
    public Button(Engine eng, int id )
    {
        super( eng,  id);
    }
    public void setOffSetClick( double ofx1, double ofy1, double ofx2, double ofy2)
    {
        offSetClickX0=ofx1;
        offSetClickY0=ofy1;
        offSetClickX1=ofx2;
        offSetClickY1=ofy2;
    }
    public void update(float deltaTime)
    {
        if(isActive) {
            handleInput();
            if (clicked)
                function();
        }
    }
    public void function()
    {
        if(id==0)
        {
            OffTheLineLogic.stateChanged=true;
            OffTheLineLogic.nextState=new PlayState(engine,0);
        }
        else if(id==1)
        {
            OffTheLineLogic.stateChanged=true;
            OffTheLineLogic.nextState=new PlayState(engine,1);
        }
        else if(id==-1)
        {
            OffTheLineLogic.stateChanged=true;
            OffTheLineLogic.nextState=new MenuState(engine);
        }
    }
    public void handleInput()
    {
        for(int i=0;i<engine.getInput().getTouchEvents().size();i++) {
            if(engine.getInput().getTouchEvents().get(i).type==0)
            {

                if(engine.getInput().getTouchEvents().get(i).x<offSetClickX1*transform.getScaleX()+transform.getPosX()  && engine.getInput().getTouchEvents().get(i).x>offSetClickX0*transform.getScaleX()+transform.getPosX() )
                {

                    if(engine.getInput().getTouchEvents().get(i).y<offSetClickY1*transform.getScaleY()+transform.getPosY()  && engine.getInput().getTouchEvents().get(i).y>offSetClickY0*transform.getScaleY()+transform.getPosY())
                    {

                        clicked=true;
                    }
                }
            }
        }

    }

}
