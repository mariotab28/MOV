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
        private Tile[,] tiles; // tiles [fila, columna]
        //private Wall[] walls;

        private Map map;
        private LevelManager levelManager;
        
        void Start()
        {

        }

        public void Init(LevelManager levelManager)
        {
            this.levelManager = levelManager;
        }

        public void SetMap(Map map)
        {
            tiles = new Tile[map.GetHeight()+1, map.GetWidth()+1];
            List<Point> mapIce;
            mapIce = map.GetIceTiles();
            List<Wall> mapWalls;
            mapWalls = map.GetWalls();

            float xOffset = -(map.GetWidth() + 1) / 2.0f;
            float yOffset = -(map.GetHeight() + 1) / 2.0f;

            Vector3 scale = new Vector3(map.GetWidth(), map.GetHeight());
           // Resolution mobileRes = Display.main.renderingHeight;
          


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
                    tile.setColor(Color.white);
                    // Propiedades del tile
                    if (map.GetGoal().x+1 == c && map.GetGoal().y+1 == r) tile.EnableGoal();
                   
                   

                    if (c == 0 && r!=0) tile.EnableRightWall();

                    if (r == 0 && c!=0) tile.EnableTopWall();

                    Point pos; pos.x = c-0.5f; pos.y = r-0.5f;
                    if (mapIce.Contains(pos)) 
                        tile.EnableIce();


                    Point originX; originX.x = c-1; originX.y = r;
                    Point destiny; destiny.x = c; destiny.y = r;
                    Wall helpX; helpX.o = originX; helpX.d = destiny;

                    if(mapWalls.Contains(helpX)) tile.EnableTopWall();

                    Point originY; originY.x = c ; originY.y = r-1;
                    Wall helpY; helpY.o = destiny; helpY.d = originY;

                    if (mapWalls.Contains(helpY)) tile.EnableRightWall();
                    
                }
            }

            // Instancia los muros del nivel
            
        }

        Tile CreateTile(float x, float y)
        {
            GameObject tileGO = Instantiate(tilePF, transform);
            tileGO.name = "Tile_" + x + "_" + y;
            tileGO.transform.localPosition = new Vector3(x, y, 0);
            Tile tile = tileGO.GetComponent<Tile>();
            return tile;
        }
    }
}