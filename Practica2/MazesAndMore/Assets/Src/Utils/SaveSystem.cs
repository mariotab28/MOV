using System.Collections;
using System.Security.Cryptography;
using System.Collections.Generic;
using System.Text;
using UnityEngine;

namespace MazesAndMore
{
    public class GameData
    {
        public int numOfHints;
        public List<LevelState[]> levelProgress;
    }

    public enum LevelState
    {
        LOCKED, UNLOCKED, COMPLETED
    }

    public static class SaveSystem
    {
        // --------------------
        // Clases serializables
        // --------------------
        [System.Serializable]
        public class JSONLevelGroup
        {
            public List<int> levelStates; // estado de los niveles (0 = LOCKED, 1 = UNLOCKED, 2 = COMPLETED) 
        }

        [System.Serializable]
        public class JSONSaveData
        {
            public string saveId; // Código hash para comprobar modificaciones 
            public int hints; // nº de pistas desbloqueadas
            public List<JSONLevelGroup> levelGroups; // niveles desbloqueados/completados por grupo
        }


        // --------------------
        // Métodos
        // --------------------

        // Serializa el progreso del jugador y lo almacena en PlayerPrefs
        public static void SaveGameData(GameData data)
        {
            JSONSaveData jsonData = ToJSONSaveData(data); // Crea un JSONSaveData con los datos de la partida
            string hashCode = GenerateHashCode(jsonData); // Genera un hash a partir de jsonData
            jsonData.saveId = hashCode; // Añade el hash a jsonData
            string json = JsonUtility.ToJson(jsonData);
            PlayerPrefs.SetString("progress", json);
        }

        // Carga el progreso del jugador, si no hay progreso guardado guarda el estado inicial del juego
        public static void LoadGameData(ref GameData data)
        {
            string json = PlayerPrefs.GetString("progress");

            if (json == "") // No hay progreso guardado 
            {
                SaveGameData(data); // Guarda el estado inicial por defecto
                return;
            }

            JSONSaveData jsonData = JsonUtility.FromJson<JSONSaveData>(json);

            // Comprobación del código hash de la partida
            string saveDataHash = jsonData.saveId;
            string testHash = GenerateHashCode(jsonData); // Genera un hash de comprobación
            if (saveDataHash != testHash) // Si los hash no coinciden => Sobreescribir la partida guardada
            {
                SaveGameData(data); // Guarda el estado inicial por defecto
                return;
            }

            // Carga el número de pistas
            data.numOfHints = jsonData.hints;

            // Carga del progreso de los niveles
            List<JSONLevelGroup> jsonGroups = jsonData.levelGroups;
            for (int i = 0; i < data.levelProgress.Count; i++)
            {
                JSONLevelGroup group = jsonGroups[i];
                for (int j = 0; j < group.levelStates.Count; j++)
                    data.levelProgress[i][j] = (LevelState)group.levelStates[j];
            }
        } // LoadGameData

        // Devuelve un JSONSaveData con los datos actuales del progreso y el nº de pistas del jugador
        static JSONSaveData ToJSONSaveData(GameData data)
        {
            JSONSaveData jsonData = new JSONSaveData();
            // nº de pistas desbloqueadas
            jsonData.hints = data.numOfHints;
            // progreso en los niveles
            List<JSONLevelGroup> groups = new List<JSONLevelGroup>();
            for (int g = 0; g < data.levelProgress.Count; g++)
            {
                List<int> states = new List<int>(); // Lista con el estado de cada nivel
                int levelsLength = data.levelProgress[g].Length;
                int level = 0;
                bool locked = false;
                // Guarda el estado de los niveles hasta encontrar el primero bloqueado (a partir de él, todos están bloqueados y no se guardan)
                while (level < levelsLength && !locked)
                {
                    LevelState state = data.levelProgress[g][level];
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
            jsonData.levelGroups = groups; // Establece la lista de grupos

            return jsonData;
        } // ToJSONSaveData

        // Genera un código hash SHA-1 a partir de los datos de la partida
        public static string GenerateHashCode(JSONSaveData data)
        {
            // Generar string a partir de los datos de pistas y progreso en niveles
            string input = "";

            if (data == null || data.levelGroups == null)
                return input;

            input += data.hints.ToString(); // Nº de pistas
            foreach (JSONLevelGroup group in data.levelGroups)
                for (int i = 0; i < group.levelStates.Count; i++)
                    input += group.levelStates[i].ToString(); // Progreso de los niveles

            // Generar el código hash usando el algoritmo SHA-1
            using (SHA1Managed sha1 = new SHA1Managed())
            {
                var hash = sha1.ComputeHash(Encoding.UTF8.GetBytes(input));
                var sb = new StringBuilder(hash.Length * 2);

                foreach (byte b in hash)
                    sb.Append(b.ToString("x2")); // Escribe en sb el byte b en formato hexadecimal

                return sb.ToString();
            }
        }
    }
}
