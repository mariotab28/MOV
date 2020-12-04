package es.ucm.gdv.engine;

public interface Game {
    void update(float deltaTime);
    void render();

    /**
     * Devuelve las dimensiones lógicas del juego.
     */
    int getWidth();
    int getHeight();
}
