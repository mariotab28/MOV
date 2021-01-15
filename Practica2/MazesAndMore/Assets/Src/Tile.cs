using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
   
    public class Tile : MonoBehaviour
    {
        [Tooltip("Sprite para celda de hielo.")]
        public SpriteRenderer iceFloor;
        public SpriteRenderer goal;
        public SpriteRenderer northWall;
        public SpriteRenderer eastWall;
        public TraceController traces;
        public TraceController hintTraces;

        private Color color;

        public void EnableIce()
        {
            iceFloor.enabled = true;
        }

        public void DisableIce()
        {
            iceFloor.enabled = false;
        }

        public void EnableGoal()
        {
            goal.enabled = true;
            goal.color = color;
        }
        public void DisableGoal()
        {
            goal.enabled = false;
        }
        public void EnableTopWall()
        {
            northWall.enabled = true;
        }

        public void DisableTopWall()
        {
            northWall.enabled = false;
        }
        public void EnableRightWall()
        {
            eastWall.enabled = true;
        }

        public void DisableRightWall()
        {
            eastWall.enabled = false;
        }
        public void setColor(Color c)
        {
            color = c;
        }
        public void trace(TraceInfo info,float secondsUntil)
        {
            traces.makeTrace(info, color, secondsUntil);   
        }

        public void hintTrace(TraceInfo info)
        {
            hintTraces.makeTrace(info, Color.yellow, 0);
        }

        public void Pause()
        {
            traces.Pause();
            hintTraces.Pause();
        }
        public void Resume()
        {
            traces.Resume();
            hintTraces.Resume();
        }

        public bool isTraceDone(Direction info)
        {
            return (traces.isTraceDone(info));
        }
        public bool isHintTraceDone(Direction info)
        {
            return (hintTraces.isTraceDone(info));
        }
        public void reset()
        {
            traces.reset();
        }
        public bool isWallRight()
        {
            return eastWall.enabled;
        }
        public bool isWallTop()
        {
            return northWall.enabled;
        }

        public bool isIce()
        {
            return iceFloor.enabled;
        }
        public bool isGoal()
        {
            return goal.enabled;
        }
        public Vector3 getPosition()
        {
            return transform.localPosition;
        }
    }
}
