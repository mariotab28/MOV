package es.ucm.gdv.offtheline;

public class Pair
{
    public double x;
    public double y;
    public boolean collision=false;

    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public Pair() {
        this.x = 0;
        this.y = 0;
    }
}