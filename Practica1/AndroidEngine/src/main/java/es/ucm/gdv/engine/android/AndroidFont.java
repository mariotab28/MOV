package es.ucm.gdv.engine.android;

import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.gdv.engine.Font;

public class AndroidFont implements Font {
    boolean isBold;
    int color;
    float size;
    Typeface font;

    public AndroidFont(InputStream path, float size, boolean isBold) {
        this.isBold = isBold;
        this.size = size;
        this.color = color;


        Typeface.createFromAsset(/*this.getAssets()*/null, path.toString());

    }

    @Override
    public String getFontName() {
        return null;
    }

    @Override
    public float getFontSize() {
        return size;
    }

    @Override
    public boolean isBold() {
        return isBold;
    }
}
