using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class ExitGame : MonoBehaviour
    {
        // Start is called before the first frame updat
        public void Close()
        {
            Application.Quit();
        }
    }
}