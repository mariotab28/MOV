package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;

public class MenuState implements GameState {

    Engine engine=null;
    Font font1,font2;

    Vector<GameObject> objectsInScene;
    public MenuState(Engine engine)
    {


        this.engine=engine;

        objectsInScene=new Vector<>();
        loadFonts();

    }

    public void loadFonts()
    {
        InputStream is= engine.openInputStream("Bungee-Regular.ttf");
        font1=engine.getGraphics().newFont(is,80,false);

        InputStream is2= engine.openInputStream("Bungee-Regular.ttf");
        font2=engine.getGraphics().newFont(is2,1600,false);

    }
    @Override
    public void start()
    {

        Button b1=new Button(engine,0);
        b1.transform.setPosX(100);
        b1.transform.setPosY(100);
        b1.text="EASY MODE";
        b1.font=font2;
        objectsInScene.add(b1);
        Button b2=new Button(engine,1);
        b2.transform.setPosX(100);
        b2.transform.setPosY(200);
        b2.text="EASY MODE";
        b2.font=font1;
        objectsInScene.add(b2);
    }
    @Override
    public void update(float deltaTime)
    {
        for(int i =0;i<objectsInScene.size();i++)
        {
            objectsInScene.get(i).update(deltaTime);
        }
    }

    @Override
    public void render() {
        engine.getGraphics().clear(0x000000);
        for(int i =0;i<objectsInScene.size();i++)
        {
            objectsInScene.get(i).render();
        }
    }
}
