using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class LevelMenuUI : MonoBehaviour
    {
        LevelPackage[] levelPackages;

        public GameObject groupSelectUI;
        public GameObject levelSelectUI;
        public GameObject groupContainer;
        public GameObject levelContainer;
        public Text levelsTitleText;
        public ButtonConfiguration groupButtonPrefab;
        public LevelButtonConfiguration levelButtonPrefab;

        ButtonConfiguration[] groupButtons;
        LevelButtonConfiguration[] levelButtons;

        void Start()
        {
            // Pide los grupos de niveles al GameManager
           
            levelPackages = GameManager.instance.GetLevelPackages();
            groupButtons = new ButtonConfiguration[levelPackages.Length];
            // Crea los elementos de la interfaz del selector de grupos de niveles
            SetUpLevelGroupsUI();
        }

        // Añade un botón a la escena por cada paquete de niveles
        void SetUpLevelGroupsUI()
        {
            if (!groupSelectUI)
            {
                Debug.LogError("Error: Level selection canvas not found.");
                return;
            }
            
            for (int i = 0; i < levelPackages.Length; i++)
            {
                groupButtons[i] = AddGroupButton(levelPackages[i], i);
            }
        }

        // Instancia un botón para acceder a los niveles de un determinado paquete de niveles
        ButtonConfiguration AddGroupButton(LevelPackage group, int index)
        {
            ButtonConfiguration button = Instantiate(groupButtonPrefab, groupContainer.transform);
            button.Configure(group.buttonImage, group.buttonPressedImage, group.groupName, this, index);
            
            return button;
        }

        // Pasa al menú de selección de nivel de un determinado grupo de niveles
        public void ShowLevelsFromGroup(int index)
        {
            groupSelectUI.SetActive(false); // Desactiva la interfaz de selección de grupo
            LevelPackage group = levelPackages[index];
            int size = group.levels.Length;
            levelButtons = new LevelButtonConfiguration[size];

            for (int i = 0; i < size; i++)
            {
                levelButtons[i] = AddLevelButton(i + 1, group.color, index);
            }

            levelsTitleText.text = group.groupName;
            levelSelectUI.SetActive(true);
        }

        // Añade un botón por cada nivel y lo configura en función del paquete del nivel y del progreso del jugador
        LevelButtonConfiguration AddLevelButton(int number, Color groupColor, int groupIndex)
        {
            LevelButtonConfiguration button = Instantiate(levelButtonPrefab, levelContainer.transform);
            button.Configure(number, !GameManager.instance.IsLevelUnlocked(groupIndex, number - 1), GameManager.instance.IsLevelCompleted(groupIndex, number - 1), groupColor, groupIndex);

            return button;
        }
    }
}