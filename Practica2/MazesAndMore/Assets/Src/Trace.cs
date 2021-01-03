using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace MazesAndMore
{
    public class Trace : MonoBehaviour
    {
        private float timer=0;
        private float maxTime=0;
        private float deltaTime = 0;
        private bool fromCenter;
        private bool wasCenter;
        private bool enable=false;
        private bool helpenable = false;
        private Color color;
        private bool visible=false;
        private float maxSize = 0.75f;
        private float minSize = 0.25f;

        public Transform tran;
        public SpriteRenderer spRender;
        public int Xdir;
        public int Ydir;



        // Update is called once per frame

        private void Start()
        {
            Vector2 size = spRender.size;


        }
        void Update()
        {
            if (visible != enable && timer < maxTime)
            {
                if (enable)
                {
                    spRender.color = color;
                    EnableDraw();
                }
                else
                {
                    spRender.color = color;
                    DisableDraw();
                    
                }
                deltaTime = Time.deltaTime;
                timer += deltaTime;

            }
            else if (visible != enable && timer >= maxTime)
            {
                timer = maxTime;
                if (enable)
                    EnableDraw();
                else
                    DisableDraw();
                visible = enable;
                spRender.enabled = visible;

            }
        }

        public void DrawTrace(float time, bool fromCenter, int enable, Color colorTrace)
        {
            timer = 0;
            maxTime = time;
            this.fromCenter = fromCenter;
            if (enable == 0)
                this.enable = false;
            else
            {
                this.enable = true;
            }
            color = colorTrace;

            Vector2 size = spRender.size;

            if (enable!=0)
            {
                if ((Xdir > 0 && !fromCenter) || (Xdir < 0 && fromCenter))
                    size.x = -minSize;
                if ((Ydir > 0 && !fromCenter) || (Ydir < 0 && fromCenter))
                    size.y = -minSize;
                spRender.size = size;
            }

        }

        public void DrawTraceLater(float time, bool fromCenter, int enable, Color colorTrace,float secondsUntil)
        {
            timer = time;
            maxTime = time;
            this.fromCenter = fromCenter;
            if (enable == 0)
                helpenable = false;
            else
            {
                helpenable = true;
            }
            color = colorTrace;
            Invoke("resetTimer", secondsUntil);
        }

        private void resetTimer()
        {
            if (!helpenable)
                this.enable = false;
            else
            {
                this.enable = true;
            }
            timer = 0;
            Vector2 size = spRender.size;
            if (helpenable)
            {
                if (Xdir > 0 && !fromCenter || Xdir < 0 && fromCenter)
                    size.x = -minSize;
                if (Ydir > 0 && !fromCenter || Ydir < 0 && fromCenter)
                    size.y = -minSize;
                spRender.size = size;
            }
        }

        private void EnableDraw()
        {
            spRender.enabled = true;
           
            if (fromCenter)
            {
                Vector2 v=new Vector2(Xdir, Ydir);
                v.x = -v.x * 0.125f + Mathf.Abs(v.x * 0.125f);
                v.y = -v.y * 0.125f + Mathf.Abs(v.y * 0.125f);

                tran.localPosition = v;

                Vector2 size = spRender.size;

                size.x += Xdir * ((maxSize - minSize) * deltaTime / maxTime);
                size.y += Ydir * ((maxSize - minSize) * deltaTime / maxTime);
                //if (size.x == 0) size.x = 0.25f;
                //if (size.y == 0) size.y = 0.25f;
                spRender.size = size;
               
                wasCenter = true;
            }
            else
            {
                Vector2 v = new Vector2(Xdir, Ydir);
                v.x = v.x * 0.625f + Mathf.Abs(v.x * 0.125f);
                v.y = v.y * 0.625f + Mathf.Abs(v.y * 0.125f);

                tran.localPosition = v;

                Vector2 size = spRender.size;

                size.x += -Xdir * ((maxSize - minSize) * deltaTime / maxTime);
                size.y += -Ydir * ((maxSize - minSize) * deltaTime / maxTime);
                //if (size.x == 0) size.x = 0.25f;
                //if (size.y == 0) size.y = 0.25f;
                spRender.size = size;
                wasCenter = false;
            }
        }

        private void DisableDraw()
        {
            
            if (fromCenter)
            {
                int multiplier = -1;
                if (wasCenter)
                {
                    Vector2 v = new Vector2(Xdir, Ydir);
                    v.x = v.x * 0.625f + Mathf.Abs(v.x * 0.125f);
                    v.y = v.y * 0.625f + Mathf.Abs(v.y * 0.125f);

                    if (Xdir != 0)
                        spRender.flipX = true;
                    else
                        spRender.flipY = true;
                    tran.localPosition = v;
                    multiplier = 1;
                }


                Vector2 size = spRender.size;
                size.x += multiplier * Xdir * (-(maxSize - minSize) * /*(timer / maxTime)*/deltaTime / maxTime);
                size.y += multiplier * Ydir * (-(maxSize - minSize) * /*(timer / maxTime)*/deltaTime / maxTime);
                //if (size.x == 0) size.x = 0.25f;
                //if (size.y == 0) size.y = 0.25f;
                spRender.size = size;
            }
            else
            {
                int multiplier = 1;
                if (!wasCenter)
                {
                    Vector2 v = new Vector2(Xdir, Ydir);
                    v.x = -v.x * 0.125f + Mathf.Abs(v.x * 0.125f);
                    v.y = -v.y * 0.125f + Mathf.Abs(v.y * 0.125f);

                    tran.localPosition = v;
                    if (Xdir != 0)
                        spRender.flipX = true;
                    else
                        spRender.flipY = true;
                    multiplier = -1;
                }
                Vector2 size = spRender.size;
                size.x += multiplier * Xdir * (-(maxSize-minSize) * /*(timer / maxTime)*/deltaTime / maxTime);
                size.y += multiplier * Ydir * (-(maxSize - minSize) * /*(timer / maxTime)*/deltaTime / maxTime);
               // if (size.x == 0) size.x = 0.25f;
                //if (size.y == 0) size.y = 0.25f;
                spRender.size = size;
            }
        }
    }
}
