package es.ucm.gdv.engine;

public interface Game {
    void update(float deltaTime);
    void render();
    void handleInput();

    /**
     * Devuelve las dimensiones lógicas del juego.
     */
    int getWidth();
    int getHeight();
}
