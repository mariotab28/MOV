package es.ucm.gdv.offtheline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import es.ucm.gdv.engine.android.AndroidEngine;

/**
 * Prueba de concepto de renderizado activo en Android.
 *
 * Esta clase implementa la actividad principal de la aplicación.
 * En condiciones normales (una aplicación más compleja) la
 * implementación se distribuiría en más clases y se haría más
 * versátil.
 *
 * Para que funcione, en el módulo se debe incluir un directorio
 * de Assets y guardar en él el fichero "Bangers-Regular.ttf" con
 * la fuente de letra, que será cargada en ejecución para pintar un
 * rótulo en pantalla.
 */
public class MainActivity extends AppCompatActivity {
    AndroidEngine engine;
    OffTheLineLogic logic;

    //--------------------------------------------------------------------

    /**
     * Método llamado por Android al lanzar la aplicación.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: FONT
        //_font = Typeface.createFromAsset(this.getAssets(), "Bangers-Regular.ttf");

        // Creación del motor y la lógica del juego
        engine = new AndroidEngine(this);
        OffTheLineLogic logic = new OffTheLineLogic(engine);
        engine.setGame(logic);

        // Creación del motor y la lógica del juego
        AndroidEngine engine = new AndroidEngine();
        OffTheLineLogic logic = new OffTheLineLogic(engine);
        engine.setGame(logic);

        // Preparamos el contenido de la actividad.
        setContentView((View) engine.getGraphics());

        // Inicio del bucle principal del motor
        //engine.mainLoop();
    } // onCreate

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad va a pasar a primer plano,
     * estando en la cima de la pila de actividades y completamente
     * visible.
     *
     * Es llamado durante la puesta en marcha de la actividad (algo después
     * de onCreate()) y también después de un periodo de pausa (notificado
     * a través de onPause()).
     */
    @Override
    protected void onResume() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        //TODO: Avisar al graphics o a engine?
        engine.resume();

    } // onResume

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad ha dejado de ser la de
     * primer plano. Es un indicador de que el usuario está, de alguna
     * forma, abandonando la actividad.
     */
    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        //TODO: Avisar al graphics o a engine?
        engine.pause();

    } // onPause


    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    /**
     * Fuente usada para escribir el texto que se muestra moviéndose de lado a lado.
     * Se carga en el onCreate y se usa en MySurfaceView.
     */
    //protected Typeface _font;

    /*
            if (_font != null) {
                // Tenemos fuente. Vamos a escribir texto.
                // Preparamos la configuración de formato en el
                // objeto _paint que utilizaremos en cada frame.
                _paint.setTypeface(_font);
                _paint.setFakeBoldText(true);
                _paint.setColor(0xFFFFFFFF);
                _paint.setTextSize(80);
            }

        } // MySurfaceView
    */
} // class MainActivity
