package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import unit.GameSokoban;

public class Assets {



    public static Image floor, floor2, wall, boxOn, boxOff, spot, outline, outline2;

    public static Font font48;
    public static Font font30;
    public static Font font50;

    /**
     * Initialiseerd images en de fonts
     */
    public static void init()
    {

        floor = loadImage("/blocks/grond.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        floor2 = loadImage("/blocks/achtergrond.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        wall = loadImage("/blocks/Muur.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        boxOn = loadImage("/blocks/box2.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        boxOff = loadImage("/blocks/box1.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        spot = loadImage("/blocks/eindveld.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        outline = loadImage("/blocks/border1.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
        outline2 = loadImage("/blocks/border.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);

        font48 = loadFont("res/fonts/mario.ttf", 48);
        font50 = loadFont("res/fonts/mario.ttf", 50);
        font30 = loadFont("res/fonts/mario.ttf", 30);

    }

    /**
     *
     * @param path Hier wordt de locatie van de image opgehaald en de image gebufferd
     * @return
     */
    public static BufferedImage loadImage(String path)
    {
        try {
            return ImageIO.read(Assets.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param path Bij path wordt de loctie van de image opgehaald
     * @param size Bij size wordt de grote van de lettertype bepaald
     * @return
     */
    public static Font loadFont(String path, int size){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
