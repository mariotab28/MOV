using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    using Point = Map.MapPosition;
    using Wall = Map.MapWall;
    public class BoardManager : MonoBehaviour
    {
        public GameObject tilePF;
        public PlayerMovement player;
        private Tile[,] tiles; // tiles [fila, columna]
        //private Wall[] walls;
        private Color color;
        private Map map;
        private LevelManager levelManager;
        
        void Start()
        {

        }

        public void Init(LevelManager levelManager)
        {
            this.levelManager = levelManager;
           
        }

        public void Pause()
        {
            for (int r = 0; r < map.GetHeight() + 1; r++)
            {
                for (int c = 0; c < map.GetWidth() + 1; c++)
                {
                    tiles[r, c].Pause();
                }
            }
        }
        public void Resume()
        {
            for (int r = 0; r < map.GetHeight() + 1; r++)
            {
                for (int c = 0; c < map.GetWidth() + 1; c++)
                {
                    tiles[r, c].Resume();
                }
            }
        }
        public void SetMap(Map map)
        {
         
            if (levelManager!=null)
            color = levelManager.GetLevelColor();
            else color = Color.white;
            this.map = map;
            tiles = new Tile[map.GetHeight()+1, map.GetWidth()+1];
            List<Point> mapIce;
           
            mapIce = map.GetIceTiles();
            List<Wall> mapWalls;
            mapWalls = map.GetWalls();

            float xOffset = -(map.GetWidth() + 1) / 2.0f;
            float yOffset = -(map.GetHeight() + 1) / 2.0f;

            Vector3 scale = new Vector3(map.GetWidth(), map.GetHeight());
         
            scale.x = (7.0f/scale.x ) *transform.localScale.x * 1.5f;
            scale.y= ( 9.0f/scale.y ) * transform.localScale.y * 1.5f;
            this.transform.localScale=scale;

            // Instancia las tiles del nivel
            for (int r = 0; r < map.GetHeight()+1; r++)
            {
                for (int c = 0; c < map.GetWidth()+1; c++)
                {
                    Tile tile = CreateTile(c + xOffset, r + yOffset);
                    tiles[r, c] = tile;
                    tile.setColor(color);
                    // Propiedades del tile
                    if (map.GetGoal().x+1 == c && map.GetGoal().y+1 == r) tile.EnableGoal();
                   
                   

                    if (c == 0 && r!=0) tile.EnableRightWall();

                    if (r == 0 && c!=0) tile.EnableTopWall();

                    Point pos; pos.x = c-0.5f; pos.y = r-0.5f;
                    if (mapIce.Contains(pos)) 
                        tile.EnableIce();


                    Point originX; originX.x = c-1; originX.y = r;
                    Point destinyX; destinyX.x = c; destinyX.y = r;
                    Point destiny; destiny.x = c; destiny.y = r;
                    Wall helpX; helpX.o = originX; helpX.d = destinyX;

                    if (mapWalls.Contains(helpX)) tile.EnableTopWall();
                    else
                    {
                        helpX.o = destinyX; helpX.d = originX;
                        if (mapWalls.Contains(helpX)) tile.EnableTopWall();
                    }
                    Point originY; originY.x = c ; originY.y = r-1;
                    Wall helpY; helpY.o = destiny; helpY.d = originY;

                    if (mapWalls.Contains(helpY)) tile.EnableRightWall();
                    else
                    {
                        helpY.o = originY; helpY.d = destiny;
                        if (mapWalls.Contains(helpY)) tile.EnableRightWall();
                    }
                }
            }
            Point playerStart=map.GetStart();
            player.init(this,playerStart.x+xOffset+1,playerStart.y + yOffset+1);
            
            // Instancia los muros del nivel

        }

        private void makeHintTrace(Point start, Point end)
        {
            TraceInfo ifo = new TraceInfo();
            float xOffset = -(map.GetWidth() + 1) / 2.0f;
            float yOffset = -(map.GetHeight() + 1) / 2.0f;
            ifo.from = Direction.Center;
            if (start.x < end.x)
                ifo.to = Direction.East;
            else if (start.x > end.x)
                ifo.to = Direction.West;
            else if (start.y < end.y)
                ifo.to = Direction.North;
            else if (start.y > end.y)
                ifo.to = Direction.South;
            ifo.time = 0;

            GetTile(start.x + xOffset + 1, start.y + yOffset + 1).hintTrace(ifo);

            ifo.to = Direction.Center;
            if (start.x < end.x)
                ifo.from = Direction.West;
            else if (start.x > end.x)
                ifo.from = Direction.East;
            else if (start.y < end.y)
                ifo.from = Direction.South;
            else if (start.y > end.y)
                ifo.from = Direction.North;
            GetTile(end.x + xOffset + 1, end.y + yOffset + 1).hintTrace(ifo);
        }
        public bool HintUsed()
        {
            bool reached = false;
            List<Point> hints = map.GetHints();
            float xOffset = -(map.GetWidth() + 1) / 2.0f;
            float yOffset = -(map.GetHeight() + 1) / 2.0f;
            int iter = 0;
            int count =( hints.Count+3) / 3;


            Direction ifo=Direction.Center;
            if (map.GetStart().x < hints[0].x)
                ifo = Direction.East;
            else if (map.GetStart().x > hints[0].x)
                ifo = Direction.West;
            else if (map.GetStart().y < hints[0].y)
                ifo = Direction.North;
            else if (map.GetStart().y > hints[0].y)
                ifo = Direction.South;

            if (!GetTile(map.GetStart().x + xOffset + 1, map.GetStart().y + yOffset + 1).isHintTraceDone(ifo))
                makeHintTrace(map.GetStart(), hints[0]);

            while (!reached &&iter!= hints.Count-1)
            {
                if (hints[iter].x < hints[iter+1].x)
                    ifo = Direction.East;
                else if (hints[iter].x > hints[iter+1].x)
                    ifo = Direction.West;
                else if (hints[iter].y < hints[iter+1].y)
                    ifo = Direction.North;
                else if (hints[iter].y > hints[iter+1].y)
                    ifo = Direction.South;
                if (!(hints[iter].x > map.GetWidth() || hints[iter + 1].x > map.GetWidth() || hints[iter].y > map.GetHeight() || hints[iter + 1].y > map.GetHeight()))
                {
                    if (!(hints[iter].x < 0 || hints[iter + 1].x < 0 || hints[iter].y < 0 || hints[iter + 1].y < 0))
                    {
                        if (!GetTile(hints[iter].x + xOffset + 1, hints[iter].y + yOffset + 1).isHintTraceDone(ifo))
                        {
                            reached = true;
                        }
                        else
                        {
                            if (!GetTile(hints[iter].x + xOffset + 1, hints[iter].y + yOffset + 1).isHintTraceDone(ifo))
                                makeHintTrace(hints[iter], hints[iter + 1]);
                            iter++;
                        }
                    }
                    else
                        iter++;
                }
                else
                iter++;
            }
            int maxIter = iter + count;
            if(iter== hints.Count - 1&& !reached)
            {
                return false;
            }
            while(iter < hints.Count - 1 && iter<maxIter )
            {
                makeHintTrace(hints[iter], hints[iter + 1]);
                iter++;
            }
            if (iter == hints.Count - 1)
            {
                ifo = Direction.Center;
                if (hints[hints.Count - 1].x < map.GetGoal().x)
                    ifo = Direction.East;
                else if (hints[hints.Count - 1].x > map.GetGoal().x)
                    ifo = Direction.West;
                else if (hints[hints.Count - 1].y < map.GetGoal().y)
                    ifo = Direction.North;
                else if (hints[hints.Count - 1].y > map.GetGoal().y)
                    ifo = Direction.South;

                if (!GetTile(hints[hints.Count - 1].x + xOffset + 1, hints[hints.Count - 1].y + yOffset + 1).isHintTraceDone(ifo))
                    makeHintTrace(hints[hints.Count - 1], map.GetGoal());
            }
            return true;
        }

        public void ResetTiles()
        {
            float xOffset = -(map.GetWidth() + 1) / 2.0f;
            float yOffset = -(map.GetHeight() + 1) / 2.0f;
            for (int r = 0; r < map.GetHeight() + 1; r++)
            {
                for (int c = 0; c < map.GetWidth() + 1; c++)
                {
                    tiles[r, c].reset();
                }
            }
            player.init(this, map.GetStart().x + xOffset + 1, map.GetStart().y + yOffset + 1);
           
        }
        
        Tile CreateTile(float x, float y)
        {
            GameObject tileGO = Instantiate(tilePF, transform);
            tileGO.name = "Tile_" + x + "_" + y;
            tileGO.transform.localPosition = new Vector3(x, y, 0);
            Tile tile = tileGO.GetComponent<Tile>();
            return tile;
        }

        public Tile GetTile(float x, float y)
        {
            float xOffset = -(map.GetWidth() + 1) / 2.0f;
            float yOffset = -(map.GetHeight() + 1) / 2.0f;

            int sx = (int)(x - xOffset+0.5);
            int sy =(int) (y - yOffset+0.5);

           
            return tiles[sy, sx];
        }
        public Tile GetTile(int x, int y)
        {
            return tiles[x, y];
        }
    }
}