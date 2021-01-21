using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class ButtonConfiguration : MonoBehaviour
    {
        LevelMenuUI menu; // Controlador del menú de selección de niveles
        int groupIndex; // Índice del grupo de niveles del botón

        // Componentes del botón
        public Image imageComponent;
        public Button buttonComponent;
        public Text buttonText;
        public Text progressText;

        private void Start()
        {
            if (!imageComponent || !buttonComponent || !buttonText || !progressText)
                Debug.LogError("Error: Missing components for button configuration!");
        }

        public void Configure(Sprite image, Sprite pressedImage, string text, int progress, LevelMenuUI menuController, int index)
        {
            imageComponent.sprite = image; // Imagen de fondo del botón
            SpriteState spriteState = new SpriteState();
            spriteState.pressedSprite = pressedImage;
            buttonComponent.spriteState = spriteState; // Imagen de fondo al pulsar el botón
            buttonText.text = text; // Texto del botón 
            progressText.text = progress.ToString() + "%"; // Progress text

            menu = menuController;
            groupIndex = index;
        }

        public void Clicked()
        {
            menu.ShowLevelsFromGroup(groupIndex);
        }
    }
}