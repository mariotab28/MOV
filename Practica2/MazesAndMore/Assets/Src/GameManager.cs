using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
        public LevelPackage[] levelPackages;
#if UNITY_EDITOR
        public int debugLevel;
#endif

        static GameManager instance;

        void Start()
        {
            StartNewScene(); //temp
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

        private void StartNewScene()
        {
            if (levelManager)
            {
#if UNITY_EDITOR
                levelManager.Init(this);
                levelManager.SetLevelColor(levelPackages[0].color);
                levelManager.setLevelName(levelPackages[0].name, debugLevel);
                levelManager.LoadLevel(levelPackages[0].levels[debugLevel]); // Carga debugLevel;
                
#endif
            }
        }


    }
}