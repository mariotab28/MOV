using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

namespace MazesAndMore
{
    public class Map
    {
        // Dimensiones del mapa
        int width;
        int height;

        // Posiciones de las pistas
        List<MapPosition> hints = new List<MapPosition>();
        // Posiciones de las casillas de hielo
        List<MapPosition> ice = new List<MapPosition>();
        // Muros del nivel
        List<MapWall> walls = new List<MapWall>();
        // Posición de inicio
        MapPosition start;
        // Posición de meta
        MapPosition goal;

        public int GetWidth()
        {
            return width;
        }

        public int GetHeight()
        {
            return height;
        }

        public List<MapPosition> GetHints()
        {
            return hints;
        }

        public List<MapPosition> GetIceTiles()
        {
            return ice;
        }

        public List<MapWall> GetWalls()
        {
            return walls;
        }

        public MapPosition GetStart()
        {
            return start;
        }

        public MapPosition GetGoal()
        {
            return goal;
        }


        // Devuelve un mapa creado a partir del Json especificado
        static public Map GetMapFromJson(TextAsset levelFile)
        {
            Map m = new Map();
            string json = levelFile.text; // String con el texto en formato Json

            JSONMap jsonMap = JsonUtility.FromJson<JSONMap>(json);
            m.InitMap(jsonMap);

            return m;
        }

        // Inicializa los atributos del mapa a partir de un JSONMap
        public void InitMap(JSONMap m)
        {
            width = m.c;
            height = m.r;
            start = new MapPosition(m.s.x, m.s.y);
            goal = new MapPosition(m.f.x, m.f.y);
            foreach (var hPos in m.h)
                hints.Add(new MapPosition(hPos.x, hPos.y));
            foreach (var iPos in m.i)
                ice.Add(new MapPosition(iPos.x, iPos.y));
            foreach (var wall in m.w)
                walls.Add(new MapWall(new MapPosition(wall.o.x, wall.o.y), new MapPosition(wall.d.x, wall.d.y)));
        }

        public struct MapWall
        {
            public MapPosition o; // origen del muro
            public MapPosition d; // destino del muro
            public MapWall(MapPosition origin, MapPosition dest)
            {
                this.o = origin;
                this.d = dest;
            }
        }

        public struct MapPosition
        {
            public float x;
            public float y;
            public MapPosition(float x, float y)
            {
                this.x = x;
                this.y = y;
            }
        }

        // =============================================
        // Clases para la deserialización de los niveles
        // =============================================
        [System.Serializable]
        public class JSONPoint
        {
            public float x = 0;
            public float y = 0;
        }

        [System.Serializable]
        public class JSONWall
        {
            public JSONPoint o;
            public JSONPoint d;
        }

        [System.Serializable]
        public class JSONMap
        {
            public int r; // rows
            public int c; // columns
            public JSONPoint s; // start
            public JSONPoint f; // finish
            public List<JSONPoint> h; // hints
            public List<JSONWall> w; // walls
            public List<JSONPoint> i; // ice
            public List<JSONPoint> e;
            public List<JSONPoint> t;
        }
    }
}