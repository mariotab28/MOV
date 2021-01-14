﻿using System;
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
        public void reset()
        {
            TraceInfo help = new TraceInfo();

            if(counterNorth>0)
            {
                counterNorth = 1;
                help.from = Direction.Center;
                help.to = Direction.North;
                help.time = 0;
                makeTrace(help,Color.clear,0);
            }
            else if(counterNorth<0)
            {
                counterNorth = -1;
                help.from = Direction.North;
                help.to = Direction.Center;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }
            if (counterSouth > 0)
            {
                counterSouth = 1;
                help.from = Direction.Center;
                help.to = Direction.South;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }
            else if (counterSouth < 0)
            {
                counterSouth = -1;
                help.from = Direction.South;
                help.to = Direction.Center;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }
            if (counterEast > 0)
            {
                counterEast = 1;
                help.from = Direction.Center;
                help.to = Direction.East;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }
            else if (counterEast < 0)
            {
                counterEast = -1;
                help.from = Direction.East;
                help.to = Direction.Center;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }
            if (counterWest > 0)
            {
                counterWest = 1;
                help.from = Direction.Center;
                help.to = Direction.West;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }
            else if (counterWest < 0)
            {
                counterWest = -1;
                help.from = Direction.West;
                help.to = Direction.Center;
                help.time = 0;
                makeTrace(help, Color.clear, 0);
            }

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
           // Debug.Log(info.from);
            //Debug.Log(info.to+" To");
            switch (info.from)
            {
                
                case Direction.North:
                    counterNorth++;
                    if (info.to == Direction.Center)
                    {
                        north.DrawTraceLater(info.time, fromCenter, counterNorth, colorTrace, secondsUntil);
                    }
                    else
                    {
                        //Debug.Log("hola");
                        north.DrawTraceLater(info.time / 2, fromCenter, counterNorth, colorTrace, secondsUntil);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;

                case Direction.South:
                    counterSouth++;
                    if (info.to == Direction.Center)
                        south.DrawTraceLater(info.time, fromCenter, counterSouth, colorTrace, secondsUntil);
                    else
                    {
                        //Debug.Log("hola");
                        south.DrawTraceLater(info.time/2, fromCenter, counterSouth, colorTrace, secondsUntil);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;
                case Direction.East:
                    counterEast++;
                    if (info.to == Direction.Center)
                        east.DrawTraceLater(info.time, fromCenter, counterEast, colorTrace, secondsUntil);
                    else
                    {
                        east.DrawTraceLater(info.time/2, fromCenter, counterEast, colorTrace, secondsUntil);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;
                case Direction.West:
                    counterWest++;
                    if (info.to == Direction.Center)
                        west.DrawTraceLater(info.time, fromCenter, counterWest, colorTrace, secondsUntil);
                    else
                    {
                        west.DrawTraceLater(info.time/2, fromCenter, counterWest, colorTrace, secondsUntil);
                        fromCenterSwitch(info, colorTrace, secondsUntil);
                    }
                    break;
                case Direction.Center:
                    switch (info.to)
                    {
                        case Direction.North:
                            counterNorth--;
                            fromCenter = true;
                            north.DrawTraceLater(info.time, fromCenter, counterNorth, colorTrace, secondsUntil);
                            break;
                        case Direction.South:
                            counterSouth--;
                            fromCenter = true;
                            south.DrawTraceLater(info.time, fromCenter, counterSouth, colorTrace, secondsUntil);
                            break;
                        case Direction.East:
                            counterEast--;
                            fromCenter = true;
                            east.DrawTraceLater(info.time, fromCenter, counterEast, colorTrace, secondsUntil);
                            break;
                        case Direction.West:
                            counterWest--;
                            fromCenter = true;
                            west.DrawTraceLater(info.time, fromCenter, counterWest, colorTrace, secondsUntil);
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
                    break;
                case Direction.South:
                    return south.isEnable();
                    break;
                case Direction.East:
                    return east.isEnable();
                    break;
                case Direction.West:
                    return west.isEnable();
                    break;
                case Direction.Center:
                    return false;
                    break;
            }
            return false;
            
        }

        private void fromCenterSwitch(TraceInfo info, Color colorTrace, float secondsUntil)
        {

            switch (info.to)
            {
                case Direction.North:
                    counterNorth--;
                    north.DrawTraceLater(info.time/2.0f, true, counterNorth, colorTrace, (info.time / 2.0f)+ secondsUntil);
                    break;
                case Direction.South:
                    counterSouth--;
                    south.DrawTraceLater(info.time/2.0f, true, counterSouth, colorTrace, (info.time / 2.0f) + secondsUntil);
                    break;
                case Direction.East:
                    counterEast--;
                    east.DrawTraceLater(info.time/2.0f, true, counterEast, colorTrace, (info.time / 2.0f) + secondsUntil);
                    break;
                case Direction.West:
                    counterWest--;
                    west.DrawTraceLater(info.time/2.0f, true, counterWest, colorTrace, (info.time / 2.0f) + secondsUntil);
                    break;
            }
        }

    }
}
