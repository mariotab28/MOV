using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
        public LevelPackage[] levelPackages;
#if UNITY_EDITOR
        public int debugLevel;
#endif
        int groupToLoadIndex;
        int levelToLoadIndex;

        public static GameManager instance;

        void Start()
        {
            //StartNewScene(); //temp
            if (instance != null)
            {
                instance.levelManager = levelManager;
                instance.LoadLevel();
                DestroyImmediate(gameObject);
                return;
            }
            else
            {
                instance = this;
                DontDestroyOnLoad(gameObject);
            }
        }

        void Update()
        {

        }

        public LevelPackage[] GetLevelPackages()
        {
            return levelPackages;
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

        public void LoadLevel(int groupIndex, int levelIndex)
        {
            SceneManager.LoadScene(1);
            groupToLoadIndex = groupIndex;
            levelToLoadIndex = levelIndex;
        }

        private void LoadLevel()
        {
            //Debug.Log("GROUP: " + groupToLoadIndex + " - LEVEL: " + levelToLoadIndex);
            if (levelManager)
                levelManager.LoadLevel(levelPackages[groupToLoadIndex].levels[levelToLoadIndex]);
            else
                Debug.LogError("Error: Level Manager not found!");
        }
    }
}