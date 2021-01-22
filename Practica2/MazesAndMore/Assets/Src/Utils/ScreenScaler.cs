using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScreenScaler : MonoBehaviour
{
    public bool bg;

    void Start()
    {
        Resolution WorkingRes = new Resolution();
        WorkingRes.height = 1920;
        WorkingRes.width = 1080;
       
        
        float Multiplier = ((float)WorkingRes.width / (float)WorkingRes.height) / ((float)Screen.width / (float)Screen.height);

        float Multiplier2 = ((float)WorkingRes.height / (float)WorkingRes.width)/((float)Screen.height / (float)Screen.width);
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
}
