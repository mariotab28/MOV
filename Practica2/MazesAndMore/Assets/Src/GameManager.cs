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

        public int amountOfHints;
        // int playerLevel;
        public bool noMoreAds;


        public static GameManager instance;

        void Awake()
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

        public void LoadMainMenu()
        {
            SceneManager.LoadScene(0);
        }

        private void LoadLevel()
        {
            //Debug.Log("GROUP: " + groupToLoadIndex + " - LEVEL: " + levelToLoadIndex);
            if (levelManager)
            {
                levelManager.Init(groupToLoadIndex,amountOfHints/*, playerLevel*/);
                levelManager.SetLevelColor(levelPackages[groupToLoadIndex].color);
                levelManager.setLevelName(levelPackages[groupToLoadIndex].groupName, levelToLoadIndex);
                levelManager.LoadLevel(levelPackages[groupToLoadIndex].levels[levelToLoadIndex]);
            }
            else
                Debug.LogError("Error: Level Manager not found!");
        }
        public void setAmmountOfHints(int hints)
        {
            amountOfHints = hints;
        }

        public void NoMoreAds()
        {
            noMoreAds = !noMoreAds;
        }
    }
}