using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    private Touch touch;
    private Vector2 beginTouchPosition, endTouchPosition;

    public PlayerMovement movement;
    
    void Start()
    {
        if (!movement) Debug.LogError("Missing PlayerMovement component!");
    }
    
    void Update()
    {
        if (Input.touchCount > 0)
        {
            touch = Input.GetTouch(0);

            switch (touch.phase)
            {
                case TouchPhase.Began: // Dedo presionado
                    beginTouchPosition = touch.position;
                    break;
                case TouchPhase.Ended: // Dedo levantado
                    endTouchPosition = touch.position;
                    // Comprueba si ha habido desplazamiento
                    if (beginTouchPosition != endTouchPosition)
                        Swipe();
                    break;
            }
        }
    }

    // Gestiona el evento de swipe
    void Swipe()
    {
        float xSwipe = endTouchPosition.x - beginTouchPosition.x;
        float ySwipe = endTouchPosition.y - beginTouchPosition.y;

        if (Mathf.Abs(xSwipe) >= Mathf.Abs(ySwipe))
        {
            // Swipe horizontal
            Debug.Log("HORIZONTAL SWIPE");
            if (xSwipe > 0) movement.MoveRight();
            else movement.MoveLeft();
        }
        else
        {
            // Swipe Vertical
            Debug.Log("VERTICAL SWIPE");
            if (ySwipe > 0) movement.MoveUp();
            else movement.MoveDown();
        }
    }
}
