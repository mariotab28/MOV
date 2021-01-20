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

        ButtonConfiguration AddGroupButton(LevelPackage group, int index)
        {
            ButtonConfiguration button = Instantiate(groupButtonPrefab, groupContainer.transform);
            button.Configure(group.buttonImage, group.buttonPressedImage, group.groupName, this, index);
            
            return button;
        }

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

        LevelButtonConfiguration AddLevelButton(int number, Color groupColor, int groupIndex)
        {
            LevelButtonConfiguration button = Instantiate(levelButtonPrefab, levelContainer.transform);
            button.Configure(number, false, true, groupColor, groupIndex); //TODO: en función del progreso

            return button;
        }
    }
}