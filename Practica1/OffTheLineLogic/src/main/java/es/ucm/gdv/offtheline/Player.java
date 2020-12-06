package es.ucm.gdv.offtheline;

import java.util.List;
import java.util.Vector;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Input;

public class Player extends  LineObject {

    double speed=0;
    double flyingSpeed=1500;
    double flyingDirX,flyingDirY;
    int multiplier=1;
    int wallDir=1;
    double xDir,yDir;
    boolean onWall=true;
    double lastPosX,lastPosY;
    LevelBorder path;
    double lastVertexX,lastVertexY;
    double nextVertexX,nextVertexY;
    int idVertex;
    int coinsCollected;


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

    }

    @Override
    public void update(float deltaTime) {



        if(isActive) {


            lastPosX=transform.getPosX();
            lastPosY=transform.getPosY();



            double[] x1,y1;
            x1=new double[2];
            x1[0]=lastPosX;
            x1[1]=transform.getPosX();
            y1=new double[2];
            y1[0]=lastPosY;
            y1[1]=transform.getPosY();


            double diffChange =util.sqrDistancePointSegment(x1,y1,nextVertexX,nextVertexY);


            if(onWall) {
                if( diffChange<=16) {
                    transform.setPosX(path.getVertexX()[idVertex]);
                    transform.setPosY(path.getVertexY()[idVertex]);
                    idVertex+=1*wallDir;
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



            if(transform.getPosX()<0 || transform.getPosX()>OffTheLineLogic.WIDTH||transform.getPosY()<0||transform.getPosY()>OffTheLineLogic.HEIGHT)
                isActive=false;


        }
    }

    @Override
    public void handleInput(List<Input.TouchEvent> events) {
        int size = events.size();
        for(int i=0; i < size; i++) {
            Input.TouchEvent evt = events.get(i);
            if(evt.type== Input.TouchEvent.TOUCH_DOWN || (evt.type== Input.TouchEvent.KEY_DOWN && evt.pointer== Input.TouchEvent.Key_Code.SPACE.ordinal())) {


                double xLastDir = nextVertexX - lastVertexX;
                double yLastDir = nextVertexY - lastVertexY;
                double magnitude = Math.sqrt(Math.pow(xLastDir, 2) + Math.pow(yLastDir, 2));
                xLastDir = (xLastDir / magnitude);
                yLastDir = yLastDir / magnitude;
                if (path.hasDirections()) {
                    if (onWall) {
                        int idver = idVertex - wallDir;
                        if (idver < 0)
                            idver = path.getVertexX().length - 1;
                        if (idver >= path.getVertexX().length)
                            idver = 0;


                        idver = Math.min(idver, idVertex);


                        flyingDirX = path.getDirectionsX()[idver];
                        flyingDirY = path.getDirectionsY()[idver];

                        transform.setPosY(transform.getPosY() + flyingDirY);
                        transform.setPosX(transform.getPosX() + flyingDirX);
                        onWall = false;

                        if (Math.abs(flyingDirY - xLastDir) < 0.01 && Math.abs(flyingDirX - yLastDir) > 0.01) {
                            multiplier = -1;
                        } else
                            multiplier = 1;
                    }
                } else {
                    if (onWall) {
                        multiplier = -multiplier;
                        onWall = false;


                        flyingDirX = yLastDir * multiplier;
                        flyingDirY = -xLastDir * multiplier;
                        transform.setPosY(transform.getPosY() + flyingDirY);
                        transform.setPosX(transform.getPosX() + flyingDirX);
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
                        double checkDistance=util.sqrDistancePointSegment(x1,y1,nextVertexX,nextVertexY);
                        if ((x2[0] != lastVertexX || x2[1] != nextVertexX || y2[0] != lastVertexY || y2[1] != nextVertexY) && (x2[1] != lastVertexX || x2[0] != nextVertexX || y2[1] != lastVertexY || y2[0] != nextVertexY))
                            found = result.collision;
                        else found = false;

                        if (!found)
                            wall++;

                    }

                    if (found) {
                        transform.setPosX(result.x);
                        transform.setPosY(result.y);

                        double magnitude= 0;
                        int help=0;
                        double dir1x=result.x-gO.get(i).getVertexX()[wall];
                        double dir1y=result.y-gO.get(i).getVertexY()[wall];
                        magnitude= Math.sqrt(Math.pow(dir1x,2)+ Math.pow(dir1y,2));
                        dir1x=dir1x/magnitude;
                        dir1y=dir1y/magnitude;

                        if(wall < gO.get(i).getVertexX().length-1) {
                            help=wall +1;

                        }
                        double dir2y=result.y-gO.get(i).getVertexY()[help];
                        double dir2x=result.x-gO.get(i).getVertexX()[help];
                        magnitude= Math.sqrt(Math.pow(dir2x,2)+ Math.pow(dir2y,2));
                        dir2x=dir2x/magnitude;
                        dir2y=dir2y/magnitude;



                        if((Math.abs(dir1x-xDir)+Math.abs(dir1y-yDir)) <(Math.abs(dir2x-xDir)+Math.abs(dir2y-yDir)))
                        {
                            wallDir=1;
                            lastVertexX = gO.get(i).getVertexX()[wall];
                            lastVertexY = gO.get(i).getVertexY()[wall];
                            nextVertexX=gO.get(i).getVertexX()[help];
                            nextVertexY=gO.get(i).getVertexY()[help];
                            idVertex = help;
                        }
                        else {
                            lastVertexX = gO.get(i).getVertexX()[help];
                            lastVertexY = gO.get(i).getVertexY()[help];
                            nextVertexX=gO.get(i).getVertexX()[wall];
                            nextVertexY=gO.get(i).getVertexY()[wall];
                            wallDir = -1;
                            idVertex = wall;
                        }


                        onWall = true;



                        path = (LevelBorder) gO.get(i);
                    }
                } else if (gO.get(i).id == 1) {
                    if (util.sqrDistancePointSegment(x1, y1, gO.get(i).transform.getPosX(), gO.get(i).transform.getPosY()) < 400) {
                        if (!((Coins) gO.get(i)).collision)
                            coinsCollected++;
                        ((Coins) gO.get(i)).collision = true;

                    }
                } else if (gO.get(i).id == 2) {
                    x2[0] = gO.get(i).transform.getPosX() + (gO.get(i).getVertexX()[0]) * Math.cos(gO.get(i).transform.getRotation()) - (gO.get(i).getVertexY()[0]) * Math.sin(gO.get(i).transform.getRotation()) * 1;
                    y2[0] = gO.get(i).transform.getPosY() + (gO.get(i).getVertexX()[0]) * Math.sin(gO.get(i).transform.getRotation()) + (gO.get(i).getVertexY()[0]) * Math.cos(gO.get(i).transform.getRotation()) * 1;
                    x2[1] = gO.get(i).transform.getPosX() + (gO.get(i).getVertexX()[1]) * Math.cos(gO.get(i).transform.getRotation()) - (gO.get(i).getVertexY()[1]) * Math.sin(gO.get(i).transform.getRotation()) * 1;
                    y2[1] = gO.get(i).transform.getPosY() + (gO.get(i).getVertexX()[1]) * Math.sin(gO.get(i).transform.getRotation()) + (gO.get(i).getVertexY()[1]) * Math.cos(gO.get(i).transform.getRotation()) * 1;


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