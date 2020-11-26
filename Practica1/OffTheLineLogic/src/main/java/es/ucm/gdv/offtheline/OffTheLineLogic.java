package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Game;


/**
 * Implementa la interfaz lógica del juego.
 */
public class OffTheLineLogic implements Game {

    Engine engine=null;
    JSONArray levels=null;
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

       System.out.print(((JSONObject)levels.get(0)).toJSONString());
    }

    public GameObject[] loadLevel(int i)
    {
        GameObject[] objects;

        if(levels!=null) {

            if(i<levels.size())
            {


                JSONObject level=(JSONObject)levels.get(0);
                JSONArray walls=(JSONArray)level.get("paths");
                for(int j=0;j< walls.size();j++)
                {
                   JSONArray vertices= (JSONArray)((JSONObject)walls.get(j)).get("vertices");
                   //////////////////////////////////// Coger los vertices y crear el objeto ahora
                }
            }
        }
        return null;
    }

    public void update(float deltaTime){

    }
    public void render()
    {

    }
}