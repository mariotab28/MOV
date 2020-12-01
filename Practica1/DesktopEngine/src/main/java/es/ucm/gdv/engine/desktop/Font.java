package es.ucm.gdv.engine.desktop;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;


public class Font  implements es.ucm.gdv.engine.Font{
    boolean bold=false;
    java.awt.Font baseFont =null;

    String fontName;
    float _size=1;
    public Font( InputStream is,float size, boolean isBold)
    {



       // InputStream is= engine.openInputStream(FontName);
        try {
            baseFont= java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bold=isBold;
        _size=size;



        fontName=baseFont.getName();
        if(bold)
            baseFont= baseFont.deriveFont(java.awt.Font.BOLD, _size);
            //g.setFont(baseFont.deriveFont(java.awt.Font.BOLD, _size));
        else
            baseFont= baseFont.deriveFont(java.awt.Font.PLAIN, _size);
           // g.setFont(baseFont.deriveFont(java.awt.Font.PLAIN, _size))




    }

    public String getFontName()
    {

        return fontName;
    }
    public float getFontSize()
    {

        return baseFont.getSize();
    }



    public boolean isBold() {
        return bold;
    }

    public  java.awt.Font getFont()
    {
       return baseFont;

    }
}
