package es.ucm.gdv.offtheline;

import es.ucm.gdv.offtheline.Pair;

public class Utils {




   public Pair segmentsIntersection(double x1[],double y1[],double x2[],double y2[])
    {

        double s1_x, s1_y, s2_x, s2_y;
        s1_x = x1[1] - x1[0];     s1_y = y1[1] - y1[0];
        s2_x =x2[1] - x2[0];     s2_y = y2[1] - y2[0];

        double s, t;
        s = (-s1_y * (x1[0] - x2[0]) + s1_x * ( y1[0] - y2[0])) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * ( y1[0] - y2[0]) - s2_y * (x1[0] - x2[0])) / (-s2_x * s1_y + s1_x * s2_y);

        Pair punto=new Pair();
        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            // Collision detected

            punto.x= x1[0] + (t * s1_x);

            punto.y =  y1[0] + (t * s1_y);
            punto.collision=true;
            return punto;
        }

        return punto; // No collision




    }
    private double pDistance(double x, double y, double x1, double y1, double x2, double y2) {

        double A = x - x1;
        double B = y - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = -1;
        if (len_sq != 0) //in case of 0 length line
            param = dot / len_sq;

        double xx, yy;

        if (param < 0) {
            xx = x1;
            yy = y1;
        }
        else if (param > 1) {
            xx = x2;
            yy = y2;
        }
        else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }

        double dx = x - xx;
        double dy = y - yy;
        return Math.sqrt(dx * dx + dy * dy);

    }
    public double sqrDistancePointSegment(double x1[],double y1[],double x2,double y2)
    {

       double dist=Math.pow(pDistance(x2,y2,x1[0],y1[0],x1[1],y1[1]),2);
        return dist;
    }
}
