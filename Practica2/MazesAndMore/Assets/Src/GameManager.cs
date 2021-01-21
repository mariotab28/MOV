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

        public int numOfHints;
        public bool noMoreAds;
        public bool isLevelScene = false;

        public static GameManager instance;

        void Awake()
        {
            if (instance != null)
            {
                if (isLevelScene) // Si es la escena del nivel, carga el nivel
                {
                    instance.levelManager = levelManager;
                    instance.LoadLevel();
                }
                DestroyImmediate(gameObject);
                return;
            }
            else
            {
                instance = this;
                InitGameProgress(); // Inicializa los datos del progreso del juego
                DontDestroyOnLoad(gameObject);

                LoadGameData(); // Carga el progreso guardado
            }
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
                levelManager.Init(groupToLoadIndex);
                levelManager.SetLevelColor(levelPackages[groupToLoadIndex].color);
                levelManager.setLevelName(levelPackages[groupToLoadIndex].groupName, levelToLoadIndex);
                levelManager.LoadLevel(levelPackages[groupToLoadIndex].levels[levelToLoadIndex]);
            }
            else
                Debug.LogError("Error: Level Manager not found!");
        }

        public int GetNumberOfHints()
        {
            return numOfHints;
        }

        // Añade un nº determinado de pistas y guarda la partida
        public void AddHints(int amount)
        {
            numOfHints += amount;
            SaveGameData();
        }

        // Quita un nº determinado de pistas y guarda la partida
        public void RemoveHints(int amount)
        {
            Mathf.Max(0, numOfHints -= amount);
            SaveGameData();
        }

        public void NoMoreAds()
        {
            noMoreAds = !noMoreAds;
        }

        // --------------- GESTIÓN DEL PROGRESO ---------------

        // Lista con el progreso en los niveles de cada grupo 
        List<LevelState[]> levelProgress = new List<LevelState[]>();

        // Marca el nivel indicado como COMPLETADO y el siguiente como DESBLOQUEADO, y guarda la partida
        public void LevelCompleted(int group, int level)
        {
            levelProgress[group][level] = LevelState.COMPLETED; // Nivel completado
            if (level < levelProgress[group].Length && levelProgress[group][level + 1] == LevelState.LOCKED)
                levelProgress[group][level + 1] = LevelState.UNLOCKED; // Nivel desbloqueado (el siguiente)
            SaveGameData(); // Guarda el progreso
        }

        // Inicializa el diccionario con el progreso del juego al valor por defecto de los niveles.
        void InitGameProgress()
        {
            foreach (LevelPackage group in levelPackages)
            {
                LevelState[] levels = new LevelState[group.levels.Length];
                // Estado por defecto de los niveles: nivel 0 desbloqueado y el resto bloqueados
                levels[0] = LevelState.UNLOCKED;
                for (int i = 1; i < group.levels.Length; i++)
                    levels[i] = LevelState.LOCKED;
                levelProgress.Add(levels);
            }
        }

        // Devuelve true si el nivel está marcado como COMPLETADO
        public bool IsLevelCompleted(int groupIndex, int levelIndex)
        {
            return levelProgress[groupIndex][levelIndex] == LevelState.COMPLETED;
        }

        // Devuelve true si el nivel está marcado como DESBLOQUEADO
        public bool IsLevelUnlocked(int groupIndex, int levelIndex)
        {
            return levelProgress[groupIndex][levelIndex] == LevelState.UNLOCKED;
        }

        // Devuelve el porcentaje de progreso en un grupo de niveles
        public int GetGroupProgress(int groupIndex)
        {
            float total = levelPackages[groupIndex].levels.Length;
            float completed = 0;
            foreach (LevelState state in levelProgress[groupIndex])
                if (state == LevelState.COMPLETED) completed+=1.000000f;

            return Mathf.RoundToInt((completed / total) * 100);
        }
        
        // Serializa el progreso del jugador y el nº de pistas desbloqueadas
        public void SaveGameData()
        {
            GameData data = new GameData();
            data.numOfHints = numOfHints;
            data.levelProgress = levelProgress;
            SaveSystem.SaveGameData(data);
        }

        // Carga el progreso del jugador
        public void LoadGameData()
        {
            GameData data = new GameData();
            data.numOfHints = numOfHints;
            data.levelProgress = levelProgress;
            SaveSystem.LoadGameData(ref data);
            numOfHints = data.numOfHints;
            levelProgress = data.levelProgress;
        }
    }
}