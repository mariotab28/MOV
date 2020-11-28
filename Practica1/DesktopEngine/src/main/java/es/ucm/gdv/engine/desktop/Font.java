package es.ucm.gdv.engine.desktop;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;


public class Font  implements es.ucm.gdv.engine.Font{
    boolean bold=false;
    java.awt.Font baseFont =null;
    int _size=1;
    public Font(InputStream is,int size, boolean isBold,java.awt.Graphics g)
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

        if(bold)
            g.setFont(baseFont.deriveFont(java.awt.Font.BOLD, _size));
        else
            g.setFont(baseFont.deriveFont(java.awt.Font.PLAIN, _size));

    }



}
