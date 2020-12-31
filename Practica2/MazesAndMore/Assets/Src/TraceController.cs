using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public enum Direction { Center, North, South, East, West }
    public struct TraceInfo
    {
        public Direction from;
        public Direction to;
        public float time;
    }
    public class TraceController : MonoBehaviour
    {
        private int counterNorth=0, counterSouth=0, counterEast=0, counterWest=0;
        // Start is called before the first frame update
        public Trace north;
        public Trace south;
        public Trace east;
        public Trace west;

        public void makeTrace(TraceInfo info,Color colorTrace)
        {
            bool fromCenter = false;
            switch(info.from)
            {
                case Direction.North:
                    counterNorth++;
                    north.DrawTrace(info.time,fromCenter,counterNorth, colorTrace);
                    break;
                case Direction.South:
                    counterSouth++;
                    south.DrawTrace(info.time, fromCenter, counterSouth, colorTrace);
                    break;
                case Direction.East:
                    counterEast++;
                    east.DrawTrace(info.time, fromCenter, counterEast, colorTrace);
                    break;
                case Direction.West:
                    counterWest++;
                    west.DrawTrace(info.time, fromCenter, counterWest, colorTrace);
                    break;
                case Direction.Center:
                    switch (info.to)
                    {
                        case Direction.North:
                            counterNorth--;
                            fromCenter = true;
                            north.DrawTrace(info.time, fromCenter, counterNorth, colorTrace);
                            break;
                        case Direction.South:
                            counterSouth--;
                            fromCenter = true;
                            south.DrawTrace(info.time, fromCenter, counterSouth, colorTrace);
                            break;
                        case Direction.East:
                            counterEast--;
                            fromCenter = true;
                            east.DrawTrace(info.time, fromCenter, counterEast, colorTrace);
                            break;
                        case Direction.West:
                            counterWest--;
                            fromCenter = true;
                            west.DrawTrace(info.time, fromCenter, counterWest, colorTrace);
                            break;
                    }
                    break;
            }

            
        }


    }
}
