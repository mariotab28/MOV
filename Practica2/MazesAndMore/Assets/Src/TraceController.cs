using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public enum Direction { Center, North, South, East, West }
    [Serializable]
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
       // public TraceInfo infoStart;
      /*  private void Start()
        {
            makeTrace(infoStart, Color.red);

            Invoke("help", infoStart.time+0.1f);
        }
        private void help()
        {
            TraceInfo nueva = infoStart;
            nueva.from = infoStart.to;
            nueva.to = infoStart.from;
            makeTrace(nueva, Color.red);
        }*/
        public void makeTrace(TraceInfo info,Color colorTrace)
        {
            bool fromCenter = false;
            switch (info.from)
            {
                case Direction.North:
                    counterNorth++;
                    if (info.to == Direction.Center)
                        north.DrawTrace(info.time, fromCenter, counterNorth, colorTrace);
                    else
                    {
                        north.DrawTrace(info.time/2, fromCenter, counterNorth, colorTrace);
                        fromCenterSwitch(info, colorTrace);
                    }
                    break;

                case Direction.South:
                    counterSouth++;
                    if (info.to == Direction.Center)
                        south.DrawTrace(info.time, fromCenter, counterSouth, colorTrace);
                    else
                    {
                        south.DrawTrace(info.time/2, fromCenter, counterSouth, colorTrace);
                        fromCenterSwitch(info, colorTrace);
                    }
                    break;
                case Direction.East:
                    counterEast++;
                    if (info.to == Direction.Center)
                        east.DrawTrace(info.time, fromCenter, counterEast, colorTrace);
                    else
                    {
                        east.DrawTrace(info.time/2, fromCenter, counterEast, colorTrace);
                        fromCenterSwitch(info, colorTrace);
                    }
                    break;
                case Direction.West:
                    counterWest++;
                    if (info.to == Direction.Center)
                        west.DrawTrace(info.time, fromCenter, counterWest, colorTrace);
                    else
                    {
                        west.DrawTrace(info.time/2, fromCenter, counterWest, colorTrace);
                        fromCenterSwitch(info, colorTrace);
                    }
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

        private void fromCenterSwitch(TraceInfo info, Color colorTrace)
        {
            switch (info.to)
            {
                case Direction.North:
                    counterNorth--;
                    north.DrawTraceLater(info.time/2.0f, true, counterNorth, colorTrace, info.time / 2.0f);
                    break;
                case Direction.South:
                    counterSouth--;
                    south.DrawTraceLater(info.time/2.0f, true, counterSouth, colorTrace, info.time / 2.0f);
                    break;
                case Direction.East:
                    counterEast--;
                    east.DrawTraceLater(info.time/2.0f, true, counterEast, colorTrace, info.time / 2.0f);
                    break;
                case Direction.West:
                    counterWest--;
                    west.DrawTraceLater(info.time/2.0f, true, counterWest, colorTrace, info.time / 2.0f);
                    break;
            }
        }

    }
}
