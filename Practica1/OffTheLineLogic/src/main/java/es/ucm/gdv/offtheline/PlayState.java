package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Input;


public class PlayState implements GameState {
    private static final int EASYLIVES = 10;
    private static final int EASYSPEED = 250;
    private static final int HARDLIVES = 5;
    private static final int HARDSPEED = 400;



    Engine engine=null;
    JSONArray levels=null;
    Vector<GameObject> objectsInScene;
    Vector<GameObject> objectsInGameOver;
    int coinsInLevel;
    int levelIndex;
    double timer;
    int numLives=0;
    int maxLives=10;
    int movementSpeed;
    Player player=null;
    boolean playing=false;
    Font font;

    public PlayState(Engine engine,int difficulty)
    {
        this.engine=engine;

        InputStream is=engine.openInputStream("levels.json");
        InputStreamReader isr=new InputStreamReader(is);
        JSONParser jsonParser=new JSONParser();

        try {
            levels =(JSONArray) jsonParser.parse(isr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer=0;
        levelIndex=16;
        if(difficulty==0)
        {
            this.maxLives=EASYLIVES;
            this.numLives=EASYLIVES;
            this.movementSpeed=EASYSPEED;
        }
        else
        {
            this.maxLives=HARDLIVES;
            this.numLives=HARDLIVES;
            this.movementSpeed=HARDSPEED;
        }

        loadFont();



    }

    public void setDificulty( int diff)
    {
        if(diff==0)
        {
            this.numLives=EASYLIVES;
            this.movementSpeed=EASYSPEED;
        }
        else
        {
            this.numLives=HARDLIVES;
            this.movementSpeed=HARDSPEED;
        }
    }

    public void start()
    {
        objectsInScene=loadLevel(levelIndex,movementSpeed);
        playing=true;
    }
    public void loadFont()
    {
        //InputStream fontis= engine.openInputStream("Bungee-Regular.ttf");

        font= engine.getGraphics().newFont("BungeeHairline-Regular.ttf",15,false);
    }

    public  void loadGameOverObjects()
    {

        objectsInGameOver=new Vector<>();

        Button BackToMenu = new Button(engine,-1);
        BackToMenu.transform.setPosX(200);
        BackToMenu.transform.setPosY(100);
        BackToMenu.transform.setScaleX(3);
        BackToMenu.transform.setScaleY(3);
        BackToMenu.setOffSetClick(-200,-100,200,200);
        BackToMenu.text="GAME OVER";
        BackToMenu.setFont(font);
        BackToMenu.setColor(0xFF0000);

        objectsInGameOver.add(BackToMenu);

        MyText mode = new MyText(engine,5);
        mode.transform.setPosX(240);
        mode.transform.setPosY(150);
        mode.transform.setScaleX(2);
        mode.transform.setScaleY(2);
        if(movementSpeed==400)
            mode.text="HARD MODE";
        else
            mode.text="EASY MODE";
        mode.setColor(0xFFFFFF);

        mode.setFont(font);

        objectsInGameOver.add(mode);

        MyText levelReach = new MyText(engine,5);
        levelReach.transform.setPosX(240);
        levelReach.transform.setPosY(180);
        levelReach.transform.setScaleX(2);
        levelReach.transform.setScaleY(2);
        levelReach.text="SCORE: "+(levelIndex+1);
        levelReach.setColor(0xFFFFFF);
        levelReach.setFont(font);

        objectsInGameOver.add(levelReach);

    }

    public  Vector<GameObject> loadLevel(int i,int movementSpeed)
    {

        Vector<GameObject> objects=new Vector<>();


        if(levels!=null) {

            if(i<levels.size())
            {


                JSONObject level=(JSONObject)levels.get(i);
                JSONArray walls=(JSONArray)level.get("paths");
                if(walls!=null) {
                    for (int j = 0; j < walls.size(); j++) {
                        JSONArray vertices = (JSONArray) ((JSONObject) walls.get(j)).get("vertices");
                        double[] verteX = new double[vertices.size()];
                        double[] verteY = new double[vertices.size()];
                        for (int vert = 0; vert < vertices.size(); vert++) {

                            double x = ((Number) ((JSONObject) vertices.get(vert)).get("x")).doubleValue();
                            double y = ((Number) ((JSONObject) vertices.get(vert)).get("y")).doubleValue();
                            verteX[vert] = x + 320;
                            verteY[vert] = (-y) + 240;
                        }
                        /////Falta tambien las direcciones
                        JSONArray directions = (JSONArray) ((JSONObject) walls.get(j)).get("directions");
                        double[] dirX=null;
                        double[] dirY=null;
                        if(directions!=null) {
                            dirX= new double[directions.size()];
                            dirY= new double[directions.size()];
                            for (int dir = 0; dir < directions.size(); dir++) {

                                double x = ((Number) ((JSONObject) directions.get(dir)).get("x")).doubleValue();
                                double y = ((Number) ((JSONObject) directions.get(dir)).get("y")).doubleValue();
                                dirX[dir] = x;
                                dirY[dir] = -y;
                            }
                        }
                        LevelBorder wall = new LevelBorder(engine, 0);
                        wall.setVertex(verteX, verteY);

                        wall.setDirectionsX(dirX);
                        wall.setDirectionsY(dirY);

                        objects.add(wall);
                        //////////////////////////////////// Coger los vertices y crear el objeto ahora


                    }
                }

                player=new Player(engine,3,(LevelBorder)objects.get(0));
                player.setSpeed(movementSpeed);
                objects.add(player);
                coinsInLevel=0;
                JSONArray items=(JSONArray)level.get("items");
                if(items!=null) {
                    for (int it = 0; it < items.size(); it++) {
                        int x = ((Number) ((JSONObject) items.get(it)).get("x")).intValue();
                        int y = ((Number) ((JSONObject) items.get(it)).get("y")).intValue();
                        Coins coin = new Coins(engine, 1, x + 320, (-y) + 240);

                        if (((JSONObject) items.get(it)).get("angle") != null)
                            coin.setAngle(((Number) ((JSONObject) items.get(it)).get("angle")).doubleValue());
                        if (((JSONObject) items.get(it)).get("radius") != null)
                            coin.setRadius(((Number) ((JSONObject) items.get(it)).get("radius")).doubleValue());
                        if (((JSONObject) items.get(it)).get("speed") != null)
                            coin.setSpeed(((Number) ((JSONObject) items.get(it)).get("speed")).doubleValue());
                        // coin.updateVertex();
                        objects.add(coin);
                        coinsInLevel++;
                    }
                }
                /////

                JSONArray enemies=(JSONArray)level.get("enemies");
                if(enemies!=null) {
                    for (int it = 0; it < enemies.size(); it++) {
                        int x = ((Number) ((JSONObject) enemies.get(it)).get("x")).intValue();
                        int y = ((Number) ((JSONObject) enemies.get(it)).get("y")).intValue();
                        Enemy enemy = new Enemy(engine, 2, x + 320, (-y) + 240);

                        if (((JSONObject) enemies.get(it)).get("angle") != null)
                            enemy.setAngle(((Number) ((JSONObject) enemies.get(it)).get("angle")).doubleValue());
                        if (((JSONObject) enemies.get(it)).get("length") != null)
                            enemy.setLength(((Number) ((JSONObject) enemies.get(it)).get("length")).doubleValue());
                        if (((JSONObject) enemies.get(it)).get("speed") != null)
                            enemy.setSpeed(((Number) ((JSONObject) enemies.get(it)).get("speed")).doubleValue());
                        if (((JSONObject) enemies.get(it)).get("offset") != null)
                        {
                            enemy.setOffsetX(((Number)(((JSONObject) ((JSONObject) enemies.get(it)).get("offset")).get("x"))).doubleValue());
                            enemy.setOffsetY(-((Number)(((JSONObject) ((JSONObject) enemies.get(it)).get("offset")).get("y"))).doubleValue());
                        }
                        if (((JSONObject) enemies.get(it)).get("time1") != null)
                            enemy.setTime1(((Number) ((JSONObject) enemies.get(it)).get("time1")).doubleValue());
                        if (((JSONObject) enemies.get(it)).get("time2") != null)
                            enemy.setTime2(((Number) ((JSONObject) enemies.get(it)).get("time2")).doubleValue());
                        enemy.updateVertex();
                        objects.add(enemy);
                    }
                }
                //////AQUI TIENEN QUE ESTAR LOS ENEMIGOS
                String name=(String)level.get("name");
                name= "Level "+ (i+1) + "- "+name;
                MyText text=new MyText(engine,5);
                text.text=name;

                text.setFont(font);
                text.transform.setPosX(100);
                text.transform.setPosY(20);

                objects.add(text);

                LifeController lives =new LifeController(maxLives,numLives,engine, 320+100,20);
                objects.add(lives);
            }
        }

        return objects;
    }


    @Override
    public void update(float deltaTime) {
        if (numLives > 0 && levelIndex<levels.size()) {
            for (int i = 0; i < objectsInScene.size(); i++) {
                objectsInScene.get(i).update(deltaTime);
            }
            player.ManageCollisions(objectsInScene);
            if (coinsInLevel == player.getCoinsCollected()) {
                timer += deltaTime;
                if (timer > 1) {
                    levelIndex++;
                    objectsInScene.clear();
                    objectsInScene = loadLevel(levelIndex, movementSpeed);
                    timer = 0;
                }
            }

            if (!player.isActive()) {
                timer += deltaTime;
                if (timer > 2) {
                    objectsInScene.clear();
                    numLives--;
                    if (numLives <= 0) {
                        numLives = 0;
                        loadGameOverObjects();
                    }
                    else
                        objectsInScene = loadLevel(levelIndex, movementSpeed);

                    timer = 0;
                }

            }
        }
        else
        {
            for (int i = 0; i < objectsInGameOver.size(); i++) {
                objectsInGameOver.get(i).update(deltaTime);
            }
        }

    }

    @Override
    public void handleInput() {
        List<Input.TouchEvent> touchEvents = engine.getInput().getTouchEvents();
        if (numLives > 0 && levelIndex<levels.size()) {
            for (int i = 0; i < objectsInScene.size(); i++) {

                objectsInScene.get(i).handleInput(touchEvents);
            }
        }
        else {
            for (int j = 0; j < objectsInGameOver.size(); j++) {

                objectsInGameOver.get(j).handleInput(touchEvents);
            }
        }

    }

    @Override
    public void render() {

        if (numLives > 0 && levelIndex<levels.size()) {
            engine.getGraphics().clear(0x000000);
            for (int i = 0; i < objectsInScene.size(); i++) {
                objectsInScene.get(i).render();
            }
        }
        else
        {
            engine.getGraphics().restore();
            engine.getGraphics().rotate(0);
            engine.getGraphics().translate(0,0);
            engine.getGraphics().scale(1,1);
            engine.getGraphics().setColor(0x0F0F0F);
            engine.getGraphics().fillRect(0,0,640.0,200);
            engine.getGraphics().setColor(0xFFFFFF);
            for (int i = 0; i < objectsInGameOver.size(); i++) {
                objectsInGameOver.get(i).render();
            }

        }
    }


}
