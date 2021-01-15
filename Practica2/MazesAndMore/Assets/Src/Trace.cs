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
        private bool start = false;
        private Color color;
        private bool visible=false;
        private float maxSize = 0.75f;
        private float minSize = 0.25f;

        private Vector2 initialSize;
        private float startTime;
        private float actualTime;
        private float speed;

        public Transform tran;
        public SpriteRenderer spRender;
        public int Xdir;
        public int Ydir;
        private bool onPause;


        // Update is called once per frame

        private void Start()
        {
            Vector2 size = spRender.size;
            onPause = false;

        }
        void Update()
        {
            if (!onPause)
            {
                if (start && actualTime <= startTime + maxTime)
                {
                    actualTime = Time.time;
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


                }
                else if (start && actualTime >= startTime + maxTime)
                {

                    if (enable)
                        EnableDraw();
                    else
                        DisableDraw();
                    visible = enable;
                    spRender.enabled = visible;
                    start = false;
                }
            }
        }

        public void Pause()
        {
            onPause = true;
        }
        public void Resume()
        {
            onPause = false;
            float diff = Time.time - actualTime;
           
            startTime += diff;
        }

        public void DrawTrace(float time, bool fromCenter, int enable, Color colorTrace)
        {
            timer = 0;
            maxTime = time;
            this.fromCenter = fromCenter;
            if (enable == 0)
            {
                startTime = Time.time;
                this.enable = false;
            }
            else
            {
                this.enable = true;
            }
            if(colorTrace!= Color.clear)
            color = colorTrace;

            Vector2 size = spRender.size;

            speed = 1 / time;
            start = true;
            if (enable==1||enable==-1)
            {
                startTime = Time.time;
                if ((Xdir > 0 && !fromCenter) || (Xdir < 0 && fromCenter))
                    size.x = -minSize;
                if ((Ydir > 0 && !fromCenter) || (Ydir < 0 && fromCenter))
                    size.y = -minSize;
                initialSize = size;
                spRender.size = size;
            }

        }

        public void DrawTraceLater(float time, bool fromCenter, int enable, Color colorTrace,float secondsUntil)
        {
            timer = 0;
            maxTime = time;
            this.fromCenter = fromCenter;
            if (enable == 0)
            {
                startTime = Time.time+secondsUntil-time/2;
                this.enable = false;
            }
            else
            {
                this.enable = true;
            }
            if (colorTrace != Color.clear)
                color = colorTrace;
            start = true;
            Vector2 size = spRender.size;
            initialSize = size;
            speed = 1 / time;

            
            if (enable == 1 || enable == -1)
            {
                startTime = Time.time + secondsUntil;
                if ((Xdir > 0 && !fromCenter) || (Xdir < 0 && fromCenter))
                    size.x = -minSize;
                if ((Ydir > 0 && !fromCenter) || (Ydir < 0 && fromCenter))
                    size.y = -minSize;
                initialSize = size;
                spRender.size = size;
            }
        }

        public bool isEnable()
        {
            return enable;
        }

        private void EnableDraw()
        {   
            if(actualTime>startTime)
            spRender.enabled = true;
           
            if (fromCenter)
            {
                Vector2 v=new Vector2(Xdir, Ydir);
                v.x = -v.x * 0.125f + Mathf.Abs(v.x * 0.125f);
                v.y = -v.y * 0.125f + Mathf.Abs(v.y * 0.125f);

                tran.localPosition = v;

               
                Vector2 size = initialSize;
                Vector2 objSize = new Vector2(size.x + Xdir * (maxSize - minSize), size.y + Ydir * (maxSize - minSize));


                float distCovered = (actualTime - startTime) * speed;

                // Fraction of journey completed equals current distance divided by total distance.
                float fractionOfJourney = distCovered / 0.5f;


                size = Vector2.Lerp(size, objSize, fractionOfJourney);

                spRender.size = size;
               
                wasCenter = true;
            }
            else
            {
                
                Vector2 v = new Vector2(Xdir, Ydir);
                v.x = v.x * 0.625f + Mathf.Abs(v.x * 0.125f);
                v.y = v.y * 0.625f + Mathf.Abs(v.y * 0.125f);

                tran.localPosition = v;

                

                Vector2 size = initialSize;
                Vector2 objSize = new Vector2(size.x - Xdir * (maxSize - minSize), size.y - Ydir * (maxSize - minSize));


                float distCovered = (actualTime - startTime) * speed;

                // Fraction of journey completed equals current distance divided by total distance.
                float fractionOfJourney = distCovered / 0.5f;


                size = Vector2.Lerp(size, objSize, fractionOfJourney);

      
                spRender.size = size;
                wasCenter = false;
            }
        }

        private void DisableDraw()
        {
            if(actualTime >= startTime + maxTime)
            {
                spRender.enabled = false;
            }
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


                Vector2 size = initialSize;
                Vector2 objSize = new Vector2(size.x - Xdir * multiplier * (maxSize - minSize), size.y - Ydir *multiplier* (maxSize - minSize));


                float distCovered = (actualTime - startTime) * speed;

                // Fraction of journey completed equals current distance divided by total distance.
                float fractionOfJourney = distCovered / 0.5f;


                size = Vector2.Lerp(size, objSize, fractionOfJourney);

               

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

                Vector2 size = initialSize;
                Vector2 objSize = new Vector2(size.x - Xdir * multiplier * (maxSize - minSize), size.y - Ydir * multiplier * (maxSize - minSize));


                float distCovered = (actualTime - startTime) * speed;

                // Fraction of journey completed equals current distance divided by total distance.
                float fractionOfJourney = distCovered / 0.5f;


                size = Vector2.Lerp(size, objSize, fractionOfJourney);

                spRender.size = size;
            }
        }
    }
}
