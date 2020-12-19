using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
#if UNITY_EDITOR
        public int levelToPlay;
#endif

        static GameManager instance;

        void Start()
        {
            if (instance != null)
            {
                instance.levelManager = levelManager;
                DestroyImmediate(gameObject);
                return;
            }
        }

        void Update()
        {

        }
    }
}
