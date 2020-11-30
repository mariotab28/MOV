package es.ucm.gdv.offtheline;

import java.util.Vector;

import es.ucm.gdv.engine.Engine;

public class Player extends  LineObject {

    double speed=0;
    double flyingSpeed=300;
    double flyingDirX,flyingDirY;
    int multiplier=1;
    double xDir,yDir;
    boolean onWall=true;
    double lastPosX,lastPosY;
    LevelBorder path;
    double lastVertexX,lastVertexY;
    double nextVertexX,nextVertexY;
    int idVertex;
    int coinsCollected;
    boolean  alive;

    Utils util;

    public Player(Engine eng,int id,LevelBorder border)
    {

        super( eng,  id);
        path=border;
        transform.setPosX(path.getVertexX()[0]);
        transform.setPosY(path.getVertexY()[0]);

        color = 0x007FFF;


        double[] verX= {-6,6,6,-6};
        double[] verY= {6,6,-6,-6};

        setVertex(verX,verY);
        idVertex=1;

        lastVertexX= path.getVertexX()[0];
        lastVertexY=path.getVertexY()[0];
        nextVertexX=path.getVertexX()[idVertex];
        nextVertexY=path.getVertexY()[idVertex];
        util=new Utils();
        coinsCollected=0;
        alive=true;
    }

    @Override
    public void update(float deltaTime) {

        if(isActive) {

            handleEvent();
            lastPosX=transform.getPosX();
            lastPosY=transform.getPosY();
            double differX=Math.abs(nextVertexX-transform.getPosX());
            double differY=Math.abs(nextVertexY-transform.getPosY());

            if(onWall) {
                if (differX<2 && differY<2) {
                    idVertex+=1*multiplier;
                    if(idVertex>=path.getVertexX().length)
                        idVertex=0;
                    if(idVertex<0)
                        idVertex=path.getVertexX().length-1;
                    lastVertexX = nextVertexX;
                    lastVertexY = nextVertexY;
                    nextVertexX = path.getVertexX()[idVertex];
                    nextVertexY = path.getVertexY()[idVertex];


                }
                xDir=nextVertexX-transform.getPosX();
                yDir=nextVertexY-transform.getPosY();
                double magnitude= Math.sqrt(Math.pow(xDir,2)+ Math.pow(yDir,2));
                xDir=(xDir/magnitude);
                yDir=yDir/magnitude;
            }

            if(onWall)
            {
                transform.setPosX( (transform.getPosX()+ ((speed*deltaTime) * (xDir))));
                transform.setPosY( (transform.getPosY()+ ((speed*deltaTime) * (yDir))));
            }
            else
            {

                transform.setPosX((transform.getPosX()+ ((flyingSpeed*deltaTime) * (flyingDirX))));
                transform.setPosY( (transform.getPosY()+ ((flyingSpeed*deltaTime) *  (flyingDirY))));
            }

            transform.setRotation(transform.getRotation() + ((float) Math.PI ) * deltaTime);

            engine.getGraphics().rotate(transform.getRotation());


        }
    }

    public void handleEvent()
    {
        for(int i=0;i<engine.getInput().getTouchEvents().size();i++) {
            if(engine.getInput().getTouchEvents().get(i).type==0)
            {
                if(path.hasDirections())
                {
                    if (onWall) {
                        flyingDirX = path.getDirectionsX()[idVertex];
                        flyingDirY = path.getDirectionsY()[idVertex];
                        transform.setPosY(transform.getPosY() + flyingDirY);
                        transform.setPosX(transform.getPosX() + flyingDirX);
                        onWall = false;
                    }

                }
                else {
                    if (onWall) {
                        multiplier = -multiplier;
                        onWall = false;
                        flyingDirX = yDir*multiplier;
                        flyingDirY = -xDir*multiplier;
                        transform.setPosY(transform.getPosY()+flyingDirY);
                        transform.setPosX(transform.getPosX()+flyingDirX);
                    }
                }

            }
        }
    }

    public void Die(Vector<GameObject> gO)
    {
        isActive=false;
        for(int i =0;i<10;i++)
            gO.add(new Particle(engine,4,transform.getPosX(),transform.getPosY()));
    }

    public void ManageCollisions(Vector<GameObject> gO)
    {
        double[] x1,y1,x2,y2;
        x1=new double[2];
        x1[0]=lastPosX;
        x1[1]=transform.getPosX();

        y1=new double[2];
        y1[0]=lastPosY;
        y1[1]=transform.getPosY();

        x2=new double[2];
        y2=new double[2];

        Pair result =null;
        if(isActive) {
            for (int i = 0; i < gO.size(); i++) {
                if (gO.get(i).id == 0 && !onWall) {
                    int wall = 0;
                    boolean found = false;
                    while (wall < gO.get(i).getVertexX().length && !found) {

                        x2[0] = gO.get(i).getVertexX()[wall];
                        y2[0] = gO.get(i).getVertexY()[wall];
                        if (wall + 1 < gO.get(i).getVertexX().length) {
                            x2[1] = gO.get(i).getVertexX()[wall + 1];
                            y2[1] = gO.get(i).getVertexY()[wall + 1];
                        } else {
                            x2[1] = gO.get(i).getVertexX()[0];
                            y2[1] = gO.get(i).getVertexY()[0];
                        }


                        result = util.segmentsIntersection(x1, y1, x2, y2);
                        if ((x2[0] != lastVertexX || x2[1] != nextVertexX || y2[0] != lastVertexY || y2[1] != nextVertexY) && (x2[1] != lastVertexX || x2[0] != nextVertexX || y2[1] != lastVertexY || y2[0] != nextVertexY))
                            found = result.collision;
                        else found = false;
                        if (!found)
                            wall++;

                    }

                    if (found) {
                        transform.setPosX(result.x);
                        transform.setPosY(result.y);


                        onWall = true;
                        idVertex = wall;
                        if (multiplier == -1) {
                            int help = wall;
                            if (wall + 1 >= gO.get(i).getVertexX().length) {
                                help = 0;
                            }

                            lastVertexX = gO.get(i).getVertexX()[help];
                            lastVertexY = gO.get(i).getVertexY()[help];

                        } else {
                            lastVertexX = gO.get(i).getVertexX()[wall];
                            lastVertexY = gO.get(i).getVertexY()[wall];
                        }
                        if (multiplier != -1)
                            wall = wall + 1;

                        if (wall >= gO.get(i).getVertexX().length) {
                            wall = 0;
                        } else if (wall < 0) {
                            wall = gO.get(i).getVertexX().length - 1;
                        }


                        nextVertexX = gO.get(i).getVertexX()[wall];
                        nextVertexY = gO.get(i).getVertexY()[wall];

                        path = (LevelBorder) gO.get(i);
                    }
                } else if (gO.get(i).id == 1) {
                    if (util.sqrDistancePointSegment(x1, y1, gO.get(i).transform.getPosX(), gO.get(i).transform.getPosY()) < 400) {
                        if (!((Coins) gO.get(i)).collision)
                            coinsCollected++;
                        ((Coins) gO.get(i)).collision = true;

                    }
                } else if (gO.get(i).id == 2 && !onWall) {
                    x2[0] = gO.get(i).transform.getPosX() + (gO.get(i).getVertexX()[0]) * Math.cos(gO.get(i).transform.getRotation()) - (gO.get(i).getVertexY()[0]) * Math.sin(gO.get(i).transform.getRotation()) * 1;
                    y2[0] = gO.get(i).transform.getPosY() + (gO.get(i).getVertexX()[0]) * Math.sin(gO.get(i).transform.getRotation()) + (gO.get(i).getVertexY()[0]) * Math.cos(gO.get(i).transform.getRotation()) * 1;
                    x2[1] = gO.get(i).transform.getPosX() + (gO.get(i).getVertexX()[1]) * Math.cos(gO.get(i).transform.getRotation()) - (gO.get(i).getVertexY()[1]) * Math.sin(gO.get(i).transform.getRotation()) * 1;
                    y2[1] = gO.get(i).transform.getPosY() + (gO.get(i).getVertexX()[1]) * Math.sin(gO.get(i).transform.getRotation()) + (gO.get(i).getVertexY()[1]) * Math.cos(gO.get(i).transform.getRotation()) * 1;

               /* x2[0]= (gO.get(i).getVertexX()[0]);
                y2[0]= (gO.get(i).getVertexY()[0]);
                x2[1]= (gO.get(i).getVertexX()[1]);
                y2[1]= (gO.get(i).getVertexY()[1]);*/

                    result = util.segmentsIntersection(x1, y1, x2, y2);
                    if (result.collision)
                        this.Die(gO);
                }

            }
        }

    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }
}

