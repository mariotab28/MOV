package es.ucm.gdv.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //InputStream inputStream = null;
        AssetManager assetManager = this.getAssets();

        try (InputStream inputStream = assetManager.open("java.png")){

            _sprite = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            android.util.Log.e("MainActivity", "Error cyrano sprite");
        }

        _myView = new MyView(this);
        setContentView(_myView);
    }//onCreate

    protected void onResume() {
        super.onResume();
        _myView.resume();
    }

    protected void onPause() {
        super.onPause();
        _myView.pause();
    }

    Bitmap _sprite;
    MyView _myView;

    class MyView extends SurfaceView implements Runnable{

        Thread _renderThread;
        volatile boolean _running = false; // volatile -> "Compilador, cuando vayas a usar esta variable, leelo siempre en memoria"

        public MyView(Context context) {
            super(context);
            if(_sprite != null)
                _imageWidth = _sprite.getWidth();
        }

        double _x = 0;
        int _incX = 150;
        int _imageWidth;

        protected void update(double delta) {
            _x += _incX * delta;
            if(_x < 0) {
                _x = -_x;
                _incX *= -1;
            }
            else if (_x > getWidth() - _imageWidth) {
                _x = 2*getWidth() - _imageWidth - _x;
                _incX *= -1;
            }
        } //update

        protected void render(Canvas c) {
            c.drawColor(0xFF0000FF); // ARGB
            if(_sprite != null)
                c.drawBitmap(_sprite, (int)_x, 100, null);
        }

        public void run() {
            long lastFrameTime = System.nanoTime();
            SurfaceHolder holder = getHolder();
            while(_running) {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                double delta = (double) nanoElapsedTime / 1.0e9;
                // Update
                update(delta);
                // Render
                // TODO: Get Canvas associated with the View im working in
                while(!holder.getSurface().isValid())
                    ;

                Canvas c = holder.lockCanvas();
                render(c);
                holder.unlockCanvasAndPost(c);
            }
        } //run

        public void resume() {
            if(!_running) {
                _running = true;
                _renderThread = new Thread(this);
                _renderThread.start();
            }
        }

        public void pause() {
            _running = false;

            while(true)
            {
                try {
                _renderThread.join();
                break;
                } catch (InterruptedException e) {
                }
            }
        }


    }

}