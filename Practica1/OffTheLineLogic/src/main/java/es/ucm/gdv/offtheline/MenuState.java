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

        font1=engine.getGraphics().newFont(is,10,true);

        InputStream is2= engine.openInputStream("Bungee-Regular.ttf");
        font2=engine.getGraphics().newFont(is2,10,false);

    }
    @Override
    public void start()
    {

        MyText Title = new MyText(engine,5);
        Title.transform.setPosX(50);
        Title.transform.setPosY(75);
        Title.transform.setScaleX(3);
        Title.transform.setScaleY(3);
        Title.text="OFF THE LINE";
        Title.setColor(0x000FF);
        Title.setFont(font1);
        objectsInScene.add(Title);

        MyText subTitle = new MyText(engine,5);
        subTitle.transform.setPosX(50);
        subTitle.transform.setPosY(100);
        subTitle.transform.setScaleX(1.5f);
        subTitle.transform.setScaleY(1.5f);
        subTitle.text="A GAME COPIED TO BRYAN PERFETTO";
        subTitle.setColor(0x000FF);
        subTitle.setFont(font2);
        objectsInScene.add(subTitle);

        Button b1=new Button(engine,0);
        b1.transform.setPosX(50);
        b1.transform.setPosY(250);
        b1.transform.setScaleX(3);
        b1.transform.setScaleY(3);
        b1.setOffSetClick(0.0,-10,75,4);
        b1.text="EASY MODE";
        b1.setColor(0xFFFFFF);
       b1.setFont(font1);
        objectsInScene.add(b1);

        MyText subButton1 = new MyText(engine,5);
        subButton1.transform.setPosX(265);
        subButton1.transform.setPosY(250);
        subButton1.transform.setScaleX(1.5f);
        subButton1.transform.setScaleY(1.5f);
        subButton1.text="(SLOW SPEED , 10 LIVES)";
        subButton1.setColor(0x8F8F8F);
        subButton1.setFont(font2);
        objectsInScene.add(subButton1);

        Button b2=new Button(engine,1);
        b2.transform.setPosX(50);
        b2.transform.setPosY(325);
        b2.transform.setScaleX(3);
        b2.transform.setScaleY(3);
        b2.setOffSetClick(0.0,-10,75,4);
        b2.text="HARD MODE";
        b2.font=font1;
        b2.setColor(0xFFFFFF);
        objectsInScene.add(b2);

        MyText subButton2 = new MyText(engine,5);
        subButton2.transform.setPosX(275);
        subButton2.transform.setPosY(325);
        subButton2.transform.setScaleX(1.5f);
        subButton2.transform.setScaleY(1.5f);
        subButton2.text="(FAST SPEED , 5 LIVES)";
        subButton2.setColor(0x8F8F8F);
        subButton2.setFont(font2);
        objectsInScene.add(subButton2);
    }
    @Override
    public void update(float deltaTime)
    {
        for(int i =0;i<objectsInScene.size();i++)
        {
            objectsInScene.get(i).update(deltaTime);

        }
        engine.getInput().getTouchEvents().clear();

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
