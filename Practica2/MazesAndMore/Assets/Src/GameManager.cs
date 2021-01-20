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
                levelManager.Init(groupToLoadIndex/*, playerLevel*/);
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
        enum LevelState
        {
            LOCKED, UNLOCKED, COMPLETED
        }

        // Lista con el progreso en los niveles de cada grupo 
        List<LevelState[]> levelProgress = new List<LevelState[]>();

        public void LevelCompleted(int group, int level)
        {
            levelProgress[group][level] = LevelState.COMPLETED; // Nivel completado
            if (level < levelProgress[group].Length)
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

        /*[System.Serializable]
        public class JSONLevel
        {
            public int index; // índice del nivel
            public int state; // estado del nivel (0 = LOCKED, 1 = UNLOCKED, 2 = COMPLETED)
        }*/

        [System.Serializable]
        public class JSONLevelGroup
        {
            public List<int> levelStates; // estado de los niveles (0 = LOCKED, 1 = UNLOCKED, 2 = COMPLETED) 
        }

        [System.Serializable]
        public class JSONSaveData
        {
            public int hints; // nº de pistas desbloqueadas
            public List<JSONLevelGroup> levelGroups; // niveles desbloqueados/completados por grupo
        }

        // Devuelve un JSONSaveData con los datos actuales del progreso y el nº de pistas del jugador
        JSONSaveData ToJSONSaveData()
        {
            JSONSaveData data = new JSONSaveData();
            // nº de pistas desbloqueadas
            data.hints = numOfHints;
            // progreso en los niveles
            List<JSONLevelGroup> groups = new List<JSONLevelGroup>();
            for(int g = 0; g < levelPackages.Length; g++)
            {
                List<int> states = new List<int>(); // Lista con el estado de cada nivel
                int levelsLength = levelPackages[g].levels.Length;
                int level = 0;
                bool locked = false;
                // Guarda el estado de los niveles hasta encontrar el primero bloqueado (a partir de él, todos están bloqueados)
                while (level < levelsLength && !locked)
                {
                    LevelState state = levelProgress[g][level];
                    if (state != LevelState.LOCKED) // Solo guardamos los niveles desbloqueados o completados
                        states.Add((int)state);
                    else
                        locked = true;
                    level++;
                }
                JSONLevelGroup levels = new JSONLevelGroup();
                levels.levelStates = states;
                groups.Add(levels); // Añade el grupo a la lista de grupos 
            }
            data.levelGroups = groups; // Establece la lista de grupos

            return data;
        }

        // Serializa el progreso del jugador y lo almacena
        public void SaveGameData()
        {
            JSONSaveData data = ToJSONSaveData();
            string json = JsonUtility.ToJson(data);
            PlayerPrefs.SetString("progress", json);
            print(json);
        }

        // Carga el progreso del jugador, si no hay progreso guardado guarda el estado inicial del juego
        // TODO: comprobar el HASH
        public void LoadGameData()
        {
            string json = PlayerPrefs.GetString("progress");

            if (json == "") // No hay progreso guardado 
            {
                SaveGameData(); // Guarda el estado inicial por defecto
                return;
            }

            JSONSaveData data = JsonUtility.FromJson<JSONSaveData>(json);

            // Carga el número de pistas
            numOfHints = data.hints;

            // Carga del progreso de los niveles
            List<JSONLevelGroup> jsonGroups = data.levelGroups;
            for (int i = 0; i < levelProgress.Count; i++)
            {
                JSONLevelGroup group = jsonGroups[i];
                for (int j = 0; j < group.levelStates.Count; j++)
                    levelProgress[i][j] = (LevelState)group.levelStates[j];
            }
        }
    }
}