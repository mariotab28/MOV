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
import es.ucm.gdv.engine.android.AndroidGraphics;

/**
 * Actividad principal de la aplicación.
 * La clase crea el engine y la lógica.
 *
 * El bucle ppal. está en el engine.
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

        // Creación del motor y la lógica del juego
        engine = new AndroidEngine(this);
        logic = new OffTheLineLogic(engine);
        engine.setGame(logic);

        // Preparamos el contenido de la actividad.
        setContentView(((AndroidGraphics)engine.getGraphics()).getSurfaceView());
    } // onCreate

    /**
     * Notifica que la actividad va a pasar a primer plano.
     */
    @Override
    protected void onResume() {
        super.onResume();
        engine.resume();

    } // onResume

    /**
     * La actividad ha dejado de estar en primer plano.
     */
    @Override
    protected void onPause() {
        super.onPause();
        engine.pause();

    } // onPause
} // class MainActivity
