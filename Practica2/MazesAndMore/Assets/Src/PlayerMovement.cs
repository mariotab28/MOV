using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace MazesAndMore
{
    public class PlayerMovement : MonoBehaviour
    {
        struct PathPoint
        {
            public Vector3 end;
            public float lenght;
            public int dirX;
            public int dirY;
        }

        struct WallDir
        {
            public bool North;
            public bool South;
            public bool East;
            public bool West;
            public int amount;


        }

        private BoardManager board;
        private bool moving = false;
        private int dirX = 0, dirY = 0;

        // Transforms to act as start and end markers for the journey.
        private Vector3 startMarker;
        private PathPoint endPoint;

        // Movement speed in units per second.
        public float speed = 1.0F;

        // Time when the movement started.
        private float startTime;

        // Total distance between the markers.
        private float journeyLength;

        public void init(BoardManager _board, float x, float y)
        {
            board = _board;
            transform.localPosition = new Vector3(x, y);
        }
        void Update()
        {
            float time = Time.deltaTime;
            if (moving)
            {
                //transform.Translate(time * dirX, time * dirY, 0, Space.Self);
                //transform.localPosition += new Vector3(time * dirX, time * dirY);
                //Debug.Log(count);
                // count -= time;



                // Distance moved equals elapsed time times speed..
                float distCovered = (Time.time - startTime) * speed;

                // Fraction of journey completed equals current distance divided by total distance.
                float fractionOfJourney = distCovered / journeyLength;



                // Set our position as a fraction of the distance between the markers.
                transform.localPosition = Vector3.Lerp(startMarker, endPoint.end, fractionOfJourney);
                if (transform.localPosition == endPoint.end)
                {
                    WallDir walls = AmountOfWalls(transform.localPosition.x, transform.localPosition.y);
                    if (walls.amount == 2 && !board.GetTile(transform.localPosition.x, transform.localPosition.y).isIce())
                    {
                        int dirX = 0, dirY = 0;
                        if (endPoint.dirX != 0)
                        {
                            if (walls.North) dirY = -1;
                            else dirY = 1;
                        }
                        else
                        {
                            if (walls.East) dirX = -1;
                            else dirX = 1;
                        }
                        startTime = Time.time;
                        startMarker = transform.localPosition;
                        endPoint = searchLinePath(dirX, dirY);
                        journeyLength = endPoint.lenght;
                    }
                    else
                    {
                        moving = false;
                        endPoint.dirX = 0;
                        endPoint.dirY = 0;
                    }
                }
            }

        }

        // Desplaza al jugador hasta la siguiente intersección a la IZQUIERDA si es posible
        public void MoveLeft()
        {

            if (!board.GetTile(transform.localPosition.x - 1, transform.localPosition.y).isWallRight() && !moving)
            {
                startTime = Time.time;
                startMarker = transform.localPosition;
                endPoint = searchLinePath(-1, 0);

                journeyLength = endPoint.lenght;
                moving = true;
            }

        }

        // Desplaza al jugador hasta la siguiente intersección a la DERECHA si es posible
        public void MoveRight()
        {
            //Debug.Log(board.GetTile(transform.localPosition.x, transform.localPosition.y).isWallRight());
            if (!board.GetTile(transform.localPosition.x, transform.localPosition.y).isWallRight() && !moving)
            {

                startTime = Time.time;
                startMarker = transform.localPosition;

                endPoint = searchLinePath(1, 0);

                journeyLength = endPoint.lenght;
                moving = true;
            }

        }

        // Desplaza al jugador hasta la siguiente intersección hacia ARRIBA si es posible
        public void MoveUp()
        {
            if (!board.GetTile(transform.localPosition.x, transform.localPosition.y).isWallTop() && !moving)
            {
                startTime = Time.time;
                startMarker = transform.localPosition;
                endPoint = searchLinePath(0, 1);

                journeyLength = endPoint.lenght;
                moving = true;
            }
        }

        // Desplaza al jugador hasta la siguiente intersección hacia ABAJO si es posible
        public void MoveDown()
        {
            if (!board.GetTile(transform.localPosition.x, transform.localPosition.y - 1).isWallTop() && !moving)
            {
                startTime = Time.time;
                startMarker = transform.localPosition;
                endPoint = searchLinePath(0, -1);

                journeyLength = endPoint.lenght;
                moving = true;
            }
        }

        private WallDir AmountOfWalls(float x, float y)
        {
            WallDir walls = new WallDir();
            walls.amount = 0; walls.North = false;
            walls.East = false; walls.South = false;
            walls.West = false;
            if (board.GetTile(x, y).isWallRight())
            {
                walls.amount++;
                walls.East = true;
            }
            if (board.GetTile(x - 1, y).isWallRight())
            {
                walls.amount++;
                walls.West = true;
            }
            if (board.GetTile(x, y - 1).isWallTop())
            {
                walls.amount++;
                walls.South = true;
            }
            if (board.GetTile(x, y).isWallTop())
            {
                walls.amount++;
                walls.North = true;
            }
            return walls;
        }
        private PathPoint searchLinePath(int dirX, int dirY)
        {
            PathPoint path = new PathPoint();
            Vector3 initial = transform.localPosition;
            initial.x = initial.x + dirX;
            initial.y = initial.y + dirY;
            bool found = false;

            int dist = 1;
           
            while (!found)
            {
                if (board.GetTile(initial.x, initial.y).isIce())
                {
                    if (dirX > 0)
                    {
                        if (board.GetTile(initial.x, initial.y).isWallRight())
                        {
                            found = true;
                        }
                    }
                    else if (dirX < 0)
                    {
                        if (board.GetTile(initial.x - 1, initial.y).isWallRight())
                        {
                            found = true;
                        }
                    }
                    else if (dirY < 0)
                    {
                        if (board.GetTile(initial.x, initial.y - 1).isWallTop())
                        {
                            found = true;
                        }
                    }
                    else if (dirY > 0)
                    {
                        if (board.GetTile(initial.x, initial.y).isWallTop())
                        {
                            found = true;
                        }
                    }
                }
                else
                {
                    int amountX = 0, amountY = 0;
                    if (!board.GetTile(initial.x, initial.y).isWallRight())
                        amountX++;
                    if (!board.GetTile(initial.x - 1, initial.y).isWallRight())
                        amountX++;
                    if (!board.GetTile(initial.x, initial.y - 1).isWallTop())
                        amountY++;
                    if (!board.GetTile(initial.x, initial.y).isWallTop())
                        amountY++;
                    if ((amountX != 2 && dirX != 0 || amountX > 0 && dirX == 0) || (amountY != 2 && dirY != 0 || amountY > 0 && dirY == 0))
                        found = true;
                }



                if (!found)
                {
                    initial.x = initial.x + dirX;
                    initial.y = initial.y + dirY;
                    dist++;
                   
                }

            }
           
            

            path.end = initial;
            path.lenght = dist;
            path.dirX = dirX;
            path.dirY = dirY;
            return path;
        }

        //private void firstPathCall(int dirX,int dirY)
        //{
        //    TraceInfo info = new TraceInfo();
        //    info.from = Direction.Center;
        //    if (dirX > 0)
        //        info.to = Direction.East;
        //    else if (dirX < 0)
        //        info.to = Direction.West;
        //    else if (dirY > 0)
        //        info.to = Direction.North;
        //    else if (dirY < 0)
        //        info.to = Direction.South;
        //    info.time = 0.1f;

        //    board.GetTile(transform.localPosition.x, transform.localPosition.y).trace(info);
            
        //}
        //private void PathCall()
        //{
        //    TraceInfo info = new TraceInfo();
        //    if (endPoint.dirX > 0)
        //    {
        //        info.from = Direction.West;
        //        info.to = Direction.East;
        //        info.time = 0.25f;
        //        if (Mathf.Abs(endPoint.end.x - transform.localPosition.x) < 0.4)
        //        {
        //            info.to = Direction.Center;
        //            info.time = 0.125f;
        //        }
                

        //    }
        //    else if (endPoint.dirX < 0)
        //    {
        //        info.from = Direction.East;
        //        info.to = Direction.West;
        //        info.time = 0.25f;
        //        if (Mathf.Abs(endPoint.end.x - transform.localPosition.x) < 0.4)
        //        {
        //            info.to = Direction.Center;
        //            info.time = 0.125f;
        //        }
               

        //    }
        //    else if (endPoint.dirY > 0)
        //    {
        //        info.from = Direction.South;
        //        info.to = Direction.North;
        //        info.time = 0.25f;
        //        if (Mathf.Abs(endPoint.end.y - transform.localPosition.y) < 0.4)
        //        {
        //            info.to = Direction.Center;
        //            info.time = 0.125f;
        //        }
               

        //    }
        //    else if (endPoint.dirY < 0)
        //    {
        //        info.from = Direction.North;
        //        info.to = Direction.South;
        //        info.time = 0.25f;
        //        if (Mathf.Abs(endPoint.end.y - transform.localPosition.y) < 0.4)
        //        {
        //            info.to = Direction.Center;
        //            info.time = 0.125f;
        //        }
                

        //    }
         
        //    if(endPoint.dirX!=0 ||endPoint.dirY!=0)
        //    board.GetTile(transform.localPosition.x, transform.localPosition.y).trace(info);
        //}
        
    }
}
