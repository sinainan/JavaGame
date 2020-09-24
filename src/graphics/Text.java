package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {

    /**
     *
     * @param g Grahpics
     * @param text De tekst dat er moet komen
     * @param posX posittie x
     * @param posY posittie y
     * @param center Of het wel gecentereerd moet worden of niet
     * @param c Kleur
     */
    public static void drawString(Graphics g, String text, int posX, int posY, boolean center, Color c)
    {
        g.setColor(c);
        int x = posX;
        int y = posY;
        FontMetrics fm = g.getFontMetrics();
        if(center)
        {

            x = x - fm.stringWidth(text)/2;
            y = (y - fm.getHeight()/2) + fm.getAscent();
        }

        g.drawString(text, x, y);
    }
}