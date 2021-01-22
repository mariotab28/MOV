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
        public bool goingBack;
    }
    public class TraceController : MonoBehaviour
    {
        private int counterNorth=0, counterSouth=0, counterEast=0, counterWest=0;
        public Trace north;
        public Trace south;
        public Trace east;
        public Trace west;
 
        public void reset()
        { 
            north.reset();
            south.reset();
            west.reset();
            east.reset();
            counterNorth = 0;
            counterEast = 0;
            counterSouth = 0;
            counterWest = 0;
        }

        public void Pause()
        {
            north.Pause();
            south.Pause();
            east.Pause();
            west.Pause();
        }
    
        public void Resume()
        {
            north.Resume();
            south.Resume();
            east.Resume();
            west.Resume();
        }
        public void makeTrace(TraceInfo info,Color colorTrace, float secondsUntil)
        {
            
            bool fromCenter = false;

            switch (info.from)
            {
                
                case Direction.North:
                    counterNorth++;
                    if (info.to == Direction.Center)
                    {
                        north.DrawTraceLater(info.time, fromCenter, counterNorth, colorTrace, secondsUntil, info.goingBack);
                    }
                    else
                    {
                        north.DrawTraceLater(info.time / 2, fromCenter, counterNorth, colorTrace, secondsUntil, info.goingBack);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;

                case Direction.South:
                    counterSouth++;
                    if (info.to == Direction.Center)
                        south.DrawTraceLater(info.time, fromCenter, counterSouth, colorTrace, secondsUntil, info.goingBack);
                    else
                    {
                        south.DrawTraceLater(info.time/2, fromCenter, counterSouth, colorTrace, secondsUntil, info.goingBack);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;
                case Direction.East:
                    counterEast++;
                    if (info.to == Direction.Center)
                        east.DrawTraceLater(info.time, fromCenter, counterEast, colorTrace, secondsUntil, info.goingBack);
                    else
                    {
                        east.DrawTraceLater(info.time/2, fromCenter, counterEast, colorTrace, secondsUntil, info.goingBack);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;
                case Direction.West:
                    counterWest++;
                    if (info.to == Direction.Center)
                        west.DrawTraceLater(info.time, fromCenter, counterWest, colorTrace, secondsUntil, info.goingBack);
                    else
                    {
                        west.DrawTraceLater(info.time/2, fromCenter, counterWest, colorTrace, secondsUntil, info.goingBack);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;
                case Direction.Center:
                    switch (info.to)
                    {
                        case Direction.North:
                            counterNorth--;
                            fromCenter = true;
                            north.DrawTraceLater(info.time, fromCenter, counterNorth, colorTrace, secondsUntil, info.goingBack);
                            break;
                        case Direction.South:
                            counterSouth--;
                            fromCenter = true;
                            south.DrawTraceLater(info.time, fromCenter, counterSouth, colorTrace, secondsUntil, info.goingBack);
                            break;
                        case Direction.East:
                            counterEast--;
                            fromCenter = true;
                            east.DrawTraceLater(info.time, fromCenter, counterEast, colorTrace, secondsUntil, info.goingBack);
                            break;
                        case Direction.West:
                            counterWest--;
                            fromCenter = true;
                            west.DrawTraceLater(info.time, fromCenter, counterWest, colorTrace, secondsUntil, info.goingBack);
                            break;
                    }
                    break;
            }

            
        }

        public bool isTraceDone(Direction info)
        {
            switch (info)
            {
                case Direction.North:
                    return north.isEnable();
                case Direction.South:
                    return south.isEnable();                    
                case Direction.East:
                    return east.isEnable();             
                case Direction.West:
                    return west.isEnable();                   
                case Direction.Center:
                    return false;                
            }
            return false;
            
        }

        private void fromCenterSwitch(TraceInfo info, Color colorTrace, float secondsUntil)
        {

            switch (info.to)
            {
                case Direction.North:
                    counterNorth--;
                    north.DrawTraceLater(info.time/2.0f, true, counterNorth, colorTrace, (info.time / 2.0f)+ secondsUntil,info.goingBack);
                    break;
                case Direction.South:
                    counterSouth--;
                    south.DrawTraceLater(info.time/2.0f, true, counterSouth, colorTrace, (info.time / 2.0f) + secondsUntil, info.goingBack);
                    break;
                case Direction.East:
                    counterEast--;
                    east.DrawTraceLater(info.time/2.0f, true, counterEast, colorTrace, (info.time / 2.0f) + secondsUntil, info.goingBack);
                    break;
                case Direction.West:
                    counterWest--;
                    west.DrawTraceLater(info.time/2.0f, true, counterWest, colorTrace, (info.time / 2.0f) + secondsUntil, info.goingBack);
                    break;
            }
        }

    }
}
