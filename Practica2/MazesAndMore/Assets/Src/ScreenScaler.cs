using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScreenScaler : MonoBehaviour
{
    public bool bg;
    // Start is called before the first frame update
    void Start()
    {
        Resolution WorkingRes = new Resolution();
        WorkingRes.height = 1920;
        WorkingRes.width = 1080;

        
        float Multiplier = ((float)WorkingRes.width / (float)WorkingRes.height) / ((float)Display.main.renderingWidth / (float)Display.main.renderingHeight);

        float Multiplier2 = ((float)WorkingRes.height / (float)WorkingRes.width)/((float)Display.main.renderingHeight / (float)Display.main.renderingWidth);
        float mult = 0;
        if (!bg)
        {
            Multiplier = 1;

            mult = Mathf.Min(Multiplier, Multiplier2);
        }
        else
        {
            mult = Mathf.Max(Multiplier, Multiplier2);
        }
       
        transform.localScale =new Vector3(transform.localScale.x * mult, transform.localScale.y * mult);

        

    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
