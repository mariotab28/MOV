using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Back : MonoBehaviour
    {
        public GameObject backObj;
        public GameObject thisObj;


        // Update is called once per frame
        void Update()
        {
          
            if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Return))
            {
                if (backObj != null )
                    backObj.SetActive(true);
                if (thisObj != null)
                    thisObj.SetActive(false);
            }
        }
    }
}
