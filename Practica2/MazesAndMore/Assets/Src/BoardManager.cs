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

        public float yOffset = 0;
        public float xOffset = 0;

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
            tiles = new Tile[map.GetHeight(), map.GetWidth()];
            // Instancia las tiles del nivel
            for (int r = 0; r < map.GetHeight(); r++)
            {
                for (int c = 0; c < map.GetWidth(); c++)
                {
                    Tile tile = CreateTile(c + xOffset, r + yOffset);
                    tiles[r, c] = tile;

                    // Propiedades del tile
                    if (map.GetGoal().x == c && map.GetGoal().y == r) tile.EnableGoal();
                }
            }

            // Instancia los muros del nivel
            
        }

        Tile CreateTile(float x, float y)
        {
            GameObject tileGO = Instantiate(tilePF, transform);
            tileGO.name = "Tile_" + x + "_" + y;
            tileGO.transform.position = new Vector3(x, y, 0);
            Tile tile = tileGO.GetComponent<Tile>();
            return tile;
        }
    }
}