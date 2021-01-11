using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // temporal

        public BoardManager boardManager;

        void Start()
        {
            if (boardManager != null)
                boardManager.Init(this);
        }

        public void LoadLevel(TextAsset levelFile)
        {
            Map map = Map.GetMapFromJson(levelFile);
            boardManager.SetMap(map);
        }
    }
}