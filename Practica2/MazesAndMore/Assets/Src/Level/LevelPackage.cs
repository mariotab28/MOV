using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/LevelGroup", order = 1)]
public class LevelPackage : ScriptableObject
{
    public TextAsset[] levels;
    public Color color; // Color de las tiles del nivel
    public Sprite buttonImage; // Imagen en el selector de grupos de nivel
    public Sprite buttonPressedImage; // Imagen al presionar el botón
    public string groupName; // Nombre de la categoría
}
