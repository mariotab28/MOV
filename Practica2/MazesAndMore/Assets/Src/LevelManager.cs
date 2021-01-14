using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // temporal

        public BoardManager boardManager;
        public PlayerMovement player;
        private Color levelColor;
        private GameManager gameManager;
        private bool onPause=false;
        public Text text;

        public void Init(GameManager gameManager)
        {
            levelColor = Color.white;
            if (boardManager != null)
                boardManager.Init(this);
            if (player != null)
                player.setLevelManager(this);
        }

        public void ResetLevel()
        {
            onPause = false;
            boardManager.Resume();
            player.Resume();
            boardManager.ResetTiles();
        }

        public void UseHint()
        {
            boardManager.HintUsed();
        }

        public void setLevelName(string name,int lvl)
        {
            text.text = name + " - " + lvl.ToString();
        }
        public void LoadLevel(TextAsset levelFile)
        {
            Map map = Map.GetMapFromJson(levelFile);
            boardManager.SetMap(map);

        }
        public void SetLevelColor(Color color)
        {
            levelColor = color;
        }
        public Color GetLevelColor()
        {
            return levelColor;
        }
        public void Pause()
        {
            if (!onPause)
            {
                onPause = true;
                boardManager.Pause();
                player.Pause();
            }
            else
            {
                onPause = false;
                boardManager.Resume();
                player.Resume();
            }

        }
        public void LevelComplete()
        {
            Debug.Log("WIN");
            //boardManager.ResetTiles();

        }
    }
}