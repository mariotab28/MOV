using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScreenScaler : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        Resolution WorkingRes = new Resolution();
        WorkingRes.height = 1920;
        WorkingRes.width = 1080;

        float Multiplier = ((float)WorkingRes.width / (float)WorkingRes.height)/((float)Display.main.renderingWidth / (float)Display.main.renderingHeight);

        float Multiplier2 = ((float)WorkingRes.height / (float)WorkingRes.width)/((float)Display.main.renderingHeight / (float)Display.main.renderingWidth);

        if ((float)Display.main.renderingWidth / (float)Display.main.renderingHeight >= ((float)WorkingRes.width / (float)WorkingRes.height))
        {
            float help = Multiplier2;
            Multiplier2 = Multiplier;
            Multiplier = help;
            if (Multiplier > Multiplier2)
                Multiplier2 = 1;
            else if (Multiplier2 > Multiplier)
                Multiplier = 1;
        }
        else
        {
            float help = Multiplier2;
            Multiplier2 = Multiplier;
            Multiplier = help;
            if (Multiplier < Multiplier2)
                Multiplier2 = 1;
            else if (Multiplier2 < Multiplier)
                Multiplier = 1;
        }
        transform.localScale =new Vector3(transform.localScale.x * Multiplier, transform.localScale.y * Multiplier2);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
