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
   static final int WIDTH = 640, HEIGHT = 480;

    Engine engine=null;
    JSONArray levels=null;
    Vector<GameObject> objectsInScene;
    int coinsInLevel;
    int levelIndex;
    double timer;
    int numLives=0;
    int maxLives=10;
    Player player=null;
    static GameState nextState;
    static  boolean stateChanged;
    GameState state;
    public OffTheLineLogic(Engine engine)
    {
        stateChanged=false;
        this.engine=engine;
        state = new MenuState(engine);
        state.start();

    }

    @Override
    public void handleInput() {
        state.handleInput();
    }

    @Override
    public void update(float deltaTime){
        if(stateChanged)
        {
            state=nextState;
            stateChanged=false;
            state.start();
        }

        state.update(deltaTime);

    }

    @Override
    public void render()
    {
        state.render();
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}