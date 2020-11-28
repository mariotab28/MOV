package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Game;


/**
 * Implementa la interfaz lógica del juego.
 */
public class OffTheLineLogic implements Game {

    Engine engine=null;
    JSONArray levels=null;
    Vector<GameObject> objectsInScene;
    public OffTheLineLogic(Engine engine)
    {
        this.engine=engine;

        InputStream is=engine.openInputStream("Resources/levels.json");
        InputStreamReader isr=new InputStreamReader(is);
        JSONParser jsonParser=new JSONParser();

        try {
            levels =(JSONArray) jsonParser.parse(isr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        objectsInScene=loadLevel(2);


    }

    public  Vector<GameObject> loadLevel(int i)
    {

        Vector<GameObject> objects=new Vector<GameObject>();
        GameObject[] objectsA=new  GameObject[100];


        if(levels!=null) {

            if(i<levels.size())
            {


                JSONObject level=(JSONObject)levels.get(i);
                JSONArray walls=(JSONArray)level.get("paths");
                for(int j=0;j< walls.size();j++)
                {
                   JSONArray vertices= (JSONArray)((JSONObject)walls.get(j)).get("vertices");
                    double[] verteX=new double[vertices.size()];
                    double[] verteY=new double[vertices.size()];
                   for(int vert=0;vert<vertices.size();vert++)
                   {

                       double x=((Number)((JSONObject)vertices.get(vert)).get("x")).doubleValue();
                       double y=((Number)((JSONObject)vertices.get(vert)).get("y")).doubleValue();
                       verteX[vert]=x+320;
                       verteY[vert]=(-y)+240;
                   }
                   /////Falta tambien las direcciones
                   LevelBorder wall =new LevelBorder(engine,0);
                   wall.setVertex(verteX,verteY);
                   objects.add(wall);
                   //////////////////////////////////// Coger los vertices y crear el objeto ahora


                }

                JSONArray items=(JSONArray)level.get("items");
                for(int it =0 ; it < items.size();it++)
                {
                    int x=((Number)((JSONObject)items.get(it)).get("x")).intValue();
                    int y=((Number)((JSONObject)items.get(it)).get("y")).intValue();
                    Coins coin=new Coins(engine,1,x+320,(-y)+240);

                    if(((JSONObject)items.get(it)).get("angle")!=null)
                        coin.setAngle(((Number)((JSONObject)items.get(it)).get("angle")).doubleValue());
                    if(((JSONObject)items.get(it)).get("radius")!=null)
                    coin.setRadius(((Number)((JSONObject)items.get(it)).get("radius")).doubleValue());
                    if(((JSONObject)items.get(it)).get("speed")!=null)
                    coin.setSpeed(((Number)((JSONObject)items.get(it)).get("speed")).doubleValue());
                    coin.updateVertex();
                    objects.add(coin);
                }
                /////
                //////AQUI TIENEN QUE ESTAR LOS ENEMIGOS
                String name=(String)level.get("name");
                name= "Level "+ (i+1) + "- "+name;
                MyText text=new MyText(engine,5);
                text.text=name;
                text.transform.setPosX(100);
                text.transform.setPosY(20);

                objects.add(text);

            }
        }

        return objects;
    }

    public void loadFont()
    {
        InputStream font= engine.openInputStream("Resources/Bungee-Regular.ttf");
        engine.getGraphics().newFont(font,80,true);
    }
    public void update(float deltaTime){

            for(int i =0;i<objectsInScene.size();i++)
            {
                objectsInScene.get(i).update(deltaTime);
            }
    }
    public void render()
    {
        float[] colorBlack={0.0f,0.0f,0.0f};

        engine.getGraphics().clear(colorBlack);
        for(int i =0;i<objectsInScene.size();i++)
        {
            objectsInScene.get(i).render();
        }
    }
}