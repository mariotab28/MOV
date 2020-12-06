package es.ucm.gdv.engine.desktop;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;


public class Font  implements es.ucm.gdv.engine.Font{
    boolean bold=false;
    java.awt.Font baseFont =null;

    String fontName;
    float _size=1;

    /**
     * Devuelve la instancia de Font creada con el archivo y los parametros necesarios
     * @param is inputStream ya abierto del que se obtiene la fuente
     * @return
     */
    public Font( InputStream is,float size, boolean isBold)
    {

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
        else
            baseFont= baseFont.deriveFont(java.awt.Font.PLAIN, _size);

    }

    /**
    * Devuelve el nombre de la fuente
     **/
    public String getFontName()
    {

        return fontName;
    }
    /**
     * Devuelve el tamaño de la fuente
     **/
    public float getFontSize()
    {

        return baseFont.getSize();
    }


    /**
     * Devuelve si la fuente esta en negrita
     **/
    public boolean isBold() {
        return bold;
    }



    /**
     * Devuelve la fuente de java, esto solo se puede usar desde el desktopEngine y consecuentes.
     * Solo se tiene que usar y se usa en desktopGraphics
     **/
    public  java.awt.Font getFont()
    {
       return baseFont;

    }
}
