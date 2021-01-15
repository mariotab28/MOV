using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class LevelButtonConfiguration : MonoBehaviour
    {
        int levelIndex;

        // Componentes del botón
        public Image imageComponent;
        public Image lockImage;
        public Text numberText;

        // Colores
        public Color unfinishedColor;
        public Color unfinishedTextColor;
        public Color finishedTextColor;
        public Color lockedColor;
        public Color lockedLockImageColor;

        void Start()
        {

        }

        public void Configure(int number, bool locked, bool finished, Color groupColor)
        {
            levelIndex = number + 1;
            if (locked)
            {
                lockImage.gameObject.SetActive(true);
                imageComponent.color = lockedColor;
                lockImage.color = lockedLockImageColor;
            }
            else if (finished)
            {
                numberText.gameObject.SetActive(true);
                numberText.text = number.ToString();
                numberText.color = finishedTextColor;
                imageComponent.color = groupColor;
            }
            else // unfinished
            {
                numberText.gameObject.SetActive(true);
                numberText.text = number.ToString();
                numberText.color = unfinishedTextColor;
                imageComponent.color = unfinishedColor;
            }
        }

        public void Clicked()
        {
            print("LOAD LEVEL " + levelIndex);
        }
    }
}