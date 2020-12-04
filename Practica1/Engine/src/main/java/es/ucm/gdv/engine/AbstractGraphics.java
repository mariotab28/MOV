package es.ucm.gdv.engine;

public abstract class AbstractGraphics implements Graphics {

    /**
     * Dimensiones lógicas del juego.
     */
    protected int targetWidth, targetHeight;

    protected int screenWidth, screenHeight;

    /**
     * Recibe una coordena x en base al ancho de la pantalla
     * del dispositivo y la devuelve ajustada a las dimensiones lógicas
     * del juego.
     *
     * @param x Coordenada en las dimensiones de la pantalla del dispositivo.
     * @return Coordenada ajustada a la lógica del juego.
     */
    public double adjustToTargetWidth(double x) {
        //x * (targetWidth / getWidth());
        return (x / getWidth()) * targetWidth;
    }

    /**
     * Recibe una coordena y en base al alto de la pantalla
     * del dispositivo y la devuelve ajustada a las dimensiones lógicas
     * del juego.
     *
     * @param y Coordenada en las dimensiones de la pantalla del dispositivo.
     * @return Coordenada ajustada a la lógica del juego.
     */
    public double adjustToTargetHeight(double y) {
        return (y / getHeight()) * targetHeight;
    }

    /**
     * Establecen las dimensiones lógicas del juego.
     */
    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    /**
     * Devuelve el ancho de la pantalla.
     * @return ancho de la pantalla.
     */
    public int getWidth() {
        return screenWidth;
    }

    /**
     * Devuelve el alto de la pantalla.
     * @return alto de la pantalla.
     */
    public int getHeight() {
        return screenHeight;
    }
}
