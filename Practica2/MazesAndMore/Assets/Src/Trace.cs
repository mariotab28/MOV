using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace MazesAndMore
{
    public class Trace : MonoBehaviour
    {
        private float timer=0;
        private float maxTime=0;
        private bool fromCenter;
        private bool wasCenter;
        private bool enable=false;
        private Color color;
        private bool visible=false;
        private float maxSize = 0.75f;

        public Transform tran;
        public SpriteRenderer spRender;
        public int Xdir;
        public int Ydir;

        public bool start = false;

        // Update is called once per frame
        public void Start()
        {
            if(start)
            {
                DrawTrace(2.0f, false, 1, Color.red);
            }
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

                timer += Time.deltaTime;

            }
            else if (visible != enable && timer >= maxTime)
            {
                timer = maxTime;
                if (enable)
                    EnableDraw();
                else
                    DisableDraw();
                visible = enable;
                DrawTrace(2.0f, false, 0, Color.red);
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
        }

        private void EnableDraw()
        {
            if(fromCenter)
            {
                Vector2 v=new Vector2(Xdir, Ydir);
                v.x = -v.x * 0.125f + Mathf.Abs(v.x * 0.125f);
                v.y = -v.y * 0.125f + Mathf.Abs(v.y * 0.125f);

                tran.localPosition = v;

                Vector2 size = spRender.size;
                size.x = Xdir * (maxSize * (timer / maxTime));
                size.y = Ydir * (maxSize * (timer / maxTime));
                if (size.x == 0) size.x = 0.25f;
                if (size.y == 0) size.y = 0.25f;
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
                size.x = -Xdir * (maxSize * (timer / maxTime));
                size.y = -Ydir * (maxSize * (timer / maxTime));
                if (size.x == 0) size.x = 0.25f;
                if (size.y == 0) size.y = 0.25f;
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
                size.x += multiplier*Xdir * (-maxSize * /*(timer / maxTime)*/Time.deltaTime / maxTime);
                size.y += multiplier * Ydir * (-maxSize * /*(timer / maxTime)*/Time.deltaTime / maxTime);
                if (size.x == 0) size.x = 0.25f;
                if (size.y == 0) size.y = 0.25f;
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
                size.x += multiplier * Xdir * (-maxSize * /*(timer / maxTime)*/Time.deltaTime/maxTime);
                size.y += multiplier * Ydir * (-maxSize * /*(timer / maxTime)*/Time.deltaTime / maxTime);
                if (size.x == 0) size.x = 0.25f;
                if (size.y == 0) size.y = 0.25f;
                spRender.size = size;
            }
        }
    }
}
