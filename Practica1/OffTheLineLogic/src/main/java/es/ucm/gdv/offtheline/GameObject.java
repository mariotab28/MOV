package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Engine;

public class GameObject {

    MyTransform transform;
    Engine engine;
    int id;
    int color;
    boolean isActive;

    double[] vertexX;
    double[] vertexY;



    public class MyTransform{
        private double posX;
        private double posY;

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
        public double getPosX() {
            return posX;
        }

        public void setPosX(double posX) {
            this.posX = posX;
        }

        public double getPosY() {
            return posY;
        }

        public void setPosY(double posY) {
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
        int c = 0xFFFFFF;

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
        int c = 0x000000;
        color = c;
        engine=eng;
        this.id=id;
        isActive=true;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setVertex(double[] vertexX,double[]vertexY)
    {
        this.vertexX=vertexX;
        this.vertexY=vertexY;
    }

    public double[] getVertexX() {
        return vertexX;
    }

    public double[] getVertexY() {
        return vertexY;
    }
}
