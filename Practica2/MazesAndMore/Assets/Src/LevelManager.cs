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
        private bool onPause=false;
        public Text text;
        public Text hints;
        public GameObject VictoryPanel;
        private int levelNumber;
        private int group;
        private int amountOfHints;

        public void Init(int group,int amountOfHints/*,int playerLevel*/)
        {
            levelColor = Color.white;
            if (boardManager != null)
                boardManager.Init(this);
            if (player != null)
                player.setLevelManager(this);
            this.group = group;
            this.amountOfHints = amountOfHints;
            hints.text = amountOfHints.ToString();
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
           
            if (boardManager.HintUsed())
            {
                amountOfHints -= 1;
                hints.text = amountOfHints.ToString();
                GameManager.instance.setAmmountOfHints(amountOfHints);
            }
        }

        public void setLevelName(string name,int lvl)
        {
            levelNumber = lvl;
            text.text = name + " - " + (lvl+1).ToString();
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
            if (VictoryPanel)
                VictoryPanel.SetActive(true);

        }

        public void NextLevel()
        {
            GameManager.instance.LoadLevel(group, levelNumber+1);
        }
        public void BackToMenu()
        {
            GameManager.instance.LoadMainMenu();
        }
    }
}