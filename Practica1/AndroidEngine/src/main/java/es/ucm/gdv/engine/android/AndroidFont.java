package es.ucm.gdv.engine.android;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import es.ucm.gdv.engine.Font;

public class AndroidFont implements Font {
    boolean isBold;
    float size;
    Typeface font;

    public AndroidFont(String path, float size, boolean isBold, AssetManager assetMgr) {
        this.isBold = isBold;
        this.size = size;

        font = Typeface.createFromAsset(assetMgr, path);
    }

    @Override
    public String getFontName() {
        return font.toString();
    }

    public Typeface getFont() {
        return font;
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
