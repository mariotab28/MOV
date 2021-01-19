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

        public SpriteRenderer playerSprite;
        public SpriteRenderer NArrowSprite;
        public SpriteRenderer DArrowSprite;
        public SpriteRenderer RArrowSprite;
        public SpriteRenderer LArrowSprite;

        private LevelManager levelManager;
        private BoardManager board;
        private bool moving = false;
        private bool showArrow = true;
//        private int dirX = 0, dirY = 0;

        // Transforms to act as start and end markers for the journey.
        private Vector3 startMarker;
        private PathPoint endPoint;

        // Movement speed in units per second.
        public float speed = 1.0F;

        // Time when the movement started.
        private float startTime;
        private bool goal;
        private bool onPause;
        private float actualTime;
        // Total distance between the markers.
        private float journeyLength;

        private Stack<TraceInfo> Movements;

        public void init(BoardManager _board, float x, float y)
        {
            board = _board;
            transform.localPosition = new Vector3(x, y);
            showArrow = true;
            moving = false;
            Color c= levelManager.GetLevelColor();
            playerSprite.color = c;
            NArrowSprite.color = c;
            DArrowSprite.color = c;
            RArrowSprite.color = c;
            LArrowSprite.color = c;
            goal = false;
            onPause = false;
            Movements = new Stack<TraceInfo>();
        }
        public void setLevelManager(LevelManager levelManager)
        {
            this.levelManager = levelManager;
        }

        public void Pause()
        {
            onPause = true;
        }
        public void Resume()
        {
            onPause = false;
            float diff = Time.time - actualTime;
            startTime += diff;
        }
        void Update()
        {
            if (!onPause)
            {
                if (board.GetTile(transform.localPosition.x, transform.localPosition.y).isGoal() && !goal)
                {
                    levelManager.LevelComplete();
                    goal = true;
                    endPoint.end = board.GetTile(transform.localPosition.x, transform.localPosition.y).transform.localPosition;
                }
                else if (!board.GetTile(transform.localPosition.x, transform.localPosition.y).isGoal())
                {
                    goal = false;
                }

                actualTime= Time.time;
                if (moving)
                {
                    //transform.Translate(time * dirX, time * dirY, 0, Space.Self);
                    //transform.localPosition += new Vector3(time * dirX, time * dirY);
                    //Debug.Log(count);
                    // count -= time;
                    if (!showArrow)
                    {
                        ShowArrows(showArrow);
                        showArrow = true;
                    }


                    // Distance moved equals elapsed time times speed..
                    float distCovered = (actualTime - startTime) * speed;

                    // Fraction of journey completed equals current distance divided by total distance.
                    float fractionOfJourney = distCovered / journeyLength;



                    // Set our position as a fraction of the distance between the markers.
                    transform.localPosition = Vector3.Lerp(startMarker, endPoint.end, fractionOfJourney);
                    if (transform.localPosition == endPoint.end)
                    {
                        WallDir walls = AmountOfWalls(transform.localPosition.x, transform.localPosition.y);
                        if (walls.amount == 2 && !board.GetTile(transform.localPosition.x, transform.localPosition.y).isIce() && !goal)
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
                else if (showArrow)
                {
                    ShowArrows(showArrow);
                    showArrow = false;

                }
            }
        }

        private void ShowArrows(bool show)
        {
            if (!onPause)
            {
                WallDir walls = AmountOfWalls(transform.localPosition.x, transform.localPosition.y);
                if (!show)
                {
                    NArrowSprite.enabled = false;
                    DArrowSprite.enabled = false;
                    RArrowSprite.enabled = false;
                    LArrowSprite.enabled = false;
                }
                else
                {
                    NArrowSprite.enabled = !walls.North;
                    DArrowSprite.enabled = !walls.South;
                    RArrowSprite.enabled = !walls.East;
                    LArrowSprite.enabled = !walls.West;
                }
            }
        }
        // Desplaza al jugador hasta la siguiente intersección a la IZQUIERDA si es posible
        public void MoveLeft()
        {
            if (!onPause)
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
        }

        // Desplaza al jugador hasta la siguiente intersección a la DERECHA si es posible
        public void MoveRight()
        {
            if (!onPause)
            {
                if (!board.GetTile(transform.localPosition.x, transform.localPosition.y).isWallRight() && !moving)
                {

                    startTime = Time.time;
                    startMarker = transform.localPosition;

                    endPoint = searchLinePath(1, 0);

                    journeyLength = endPoint.lenght;
                    moving = true;
                }
            }
        }

        // Desplaza al jugador hasta la siguiente intersección hacia ARRIBA si es posible
        public void MoveUp()
        {
            if (!onPause)
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
        }

        // Desplaza al jugador hasta la siguiente intersección hacia ABAJO si es posible
        public void MoveDown()
        {
            if (!onPause)
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
            SingularPathCall(dirX, dirY,transform.localPosition.x,transform.localPosition.y,0);
           
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
                    if (board.GetTile(initial.x, initial.y).isGoal())
                        found = true;
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
                    if (board.GetTile(initial.x, initial.y).isGoal())
                        found = true;
                }



                if (!found)
                {
                    DoublePathCall(dirX, dirY, initial.x, initial.y, dist / speed);
                    initial.x = initial.x + dirX;
                    initial.y = initial.y + dirY;
                    dist++;
                   


                }

            }
            SingularPathCall(dirX, dirY, initial.x, initial.y, dist/ speed, true);


            path.end = initial;
            path.lenght = dist;
            path.dirX = dirX;
            path.dirY = dirY;
            return path;
        }

        private void SingularPathCall(int dirX, int dirY,float posX,float posY,float secondsUntil,bool end=false)
        {
            TraceInfo info = new TraceInfo();
            if (!end)
            {
                info.from = Direction.Center;
                if (dirX > 0)
                    info.to = Direction.East;
                else if (dirX < 0)
                    info.to = Direction.West;
                else if (dirY > 0)
                    info.to = Direction.North;
                else if (dirY < 0)
                    info.to = Direction.South;
            }
            else
            {
                info.to = Direction.Center;
                if (dirX < 0)
                    info.from = Direction.East;
                else if (dirX > 0)
                    info.from = Direction.West;
                else if (dirY < 0)
                    info.from = Direction.North;
                else if (dirY > 0)
                    info.from = Direction.South;
            }
            info.time = 1/(speed*2);

            if(Movements.Count>0 && Movements.Peek().from==info.to && Movements.Peek().to == info.from)
            {
                Movements.Pop();
                info.goingBack = true;
            }
            else
            {
                info.goingBack = false;
                Movements.Push(info);
            }
            
            board.GetTile(posX, posY).trace(info,secondsUntil);

        }
        private void DoublePathCall(int dirX, int dirY, float posX, float posY, float secondsUntil)
        {
            TraceInfo info = new TraceInfo();
            
            if (dirX > 0)
            { info.to = Direction.East; info.from = Direction.West; }
            else if (dirX < 0)
            { info.to = Direction.West; info.from = Direction.East; }
            else if (dirY > 0)
            { info.to = Direction.North; info.from = Direction.South; }
            else if (dirY < 0)
            {  info.to = Direction.South; info.from = Direction.North; }
            info.time = 1/(speed);

            if (Movements.Count > 0 && Movements.Peek().from == info.to && Movements.Peek().to == info.from)
            {
                Movements.Pop();
                info.goingBack = true;
            }
            else
            {
                info.goingBack = false;
                Movements.Push(info);
            }


            board.GetTile(posX, posY).trace(info, secondsUntil);

        }
        
    }
}
