package es.ucm.gdv.offtheline;

public interface GameState {
    void update(float deltaTime);
    void render();
    void start();
}
