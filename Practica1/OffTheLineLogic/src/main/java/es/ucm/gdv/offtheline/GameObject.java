package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class GameObject {

    MyTransform transform;
    Engine engine;
    int id;
    float[] color;
    boolean isActive;

    int[] vertexX;
    int[] vertexY;



    public class MyTransform{
        private int posX;
        private int posY;

        private float rotation;

        private float scaleX;
        private float scaleY;

        public MyTransform(){
            posX=0;
            posY=0;
            rotation=0;
            scaleX=1;
            scaleY=1;
        }
        public int getPosX() {
            return posX;
        }

        public void setPosX(int posX) {
            this.posX = posX;
        }

        public int getPosY() {
            return posY;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public float getRotation() {
            return rotation;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        public float getScaleX() {
            return scaleX;
        }

        public void setScaleX(float scaleX) {
            this.scaleX = scaleX;
        }

        public float getScaleY() {
            return scaleY;
        }

        public void setScaleY(float scaleY) {
            this.scaleY = scaleY;
        }
    }

    public void update(float deltaTime)
    {

    }

    public void render ()
    {

    }
    public GameObject()
    {

    }
    public GameObject(Engine eng,int id)
    {
        transform=new MyTransform();
        float[] c={0.0f,0.0f,0.0f};

        color=c;
        engine=eng;
        this.id=id;
        isActive=true;
    }

    public GameObject(Engine eng,int id,int posX,int posY)
    {
        transform=new MyTransform();
        transform.posX=posX;
        transform.posY=posY;
        float[] c=new float[3];
        c[0]=0.0f;
        c[1]=0.0f;
        c[2]=0.0f;
        color=c;
        engine=eng;
        this.id=id;
        isActive=true;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setVertex(int[] vertexX,int[]vertexY)
    {
        this.vertexX=vertexX;
        this.vertexY=vertexY;
    }

    public int[] getVertexX() {
        return vertexX;
    }

    public int[] getVertexY() {
        return vertexY;
    }
}
