package es.ucm.gdv.engine;

public abstract class AbstractGraphics implements Graphics {

    /**
     * Dimensiones lógicas del juego.
     */
    protected int targetWidth, targetHeight;

    /**
     * Dimensiones de la ventana.
     */
    protected int screenWidth, screenHeight;

    /**
     * Factor de escalado del canvas.
     */
    protected float scaleFactor;

    /**
     * Traslación a realizar para centrar el canvas,
     * dejando bandas negras a los lados.
     */
    protected float canvasXOffset;
    protected float canvasYOffset;

    /**
     * Calcula el factor de escalado del canvas.
     * @return Factor de escalado del canvas.
     */
    protected float getScaleFactor() {
        float factor = 1.0f;

        /*El factor de escalado más pequeño es con el que nos quedamos, asegurando así que quepa
         el juego en la pantalla */
        if((float)screenWidth / (float)targetWidth <= (float)screenHeight / (float)targetHeight)
            factor = (float)screenWidth / (float)targetWidth;// Pantalla vertical (portrait) -> scaleFactor depende del ancho
        else
            factor = (float)screenHeight / (float)targetHeight; // Pantalla horizontal (landscape)-> scaleFactor depende del alto

        return factor;
    }

    /**
     * Ajusta el canvas para
     * centrarlo en la pantalla ocupando el máximo espacio
     * sin romper la relación de aspecto del ancho/alto lógicos.
     */
    public void initCanvas() {

        // Establece el factor de escala del canvas
        scaleFactor = getScaleFactor();

        // Establece la traslación necesaria para centrar
        // el canvas en la pantalla.
        canvasXOffset = ((screenWidth / 2) - (targetWidth * scaleFactor/2f));
        canvasYOffset = ((screenHeight / 2) - (targetHeight * scaleFactor/2));
    }

    /**
     * Recibe una coordena x en base al ancho de la pantalla
     * del dispositivo y la devuelve ajustada a las dimensiones lógicas
     * del juego.
     *
     * @param x Coordenada en las dimensiones de la pantalla del dispositivo.
     * @return Coordenada ajustada a la lógica del juego.
     */
    public double adjustToTargetWidth(double x) {
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
