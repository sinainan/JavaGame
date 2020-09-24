package unit;

import graphics.Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Speler {

    public static Image playerLeft, playerBack, playerRight, playerFront;
    public static int upX = -1;
    public static int upY = 0;
    public static int leftX = 0;
    public static int leftY = -1;
    public static int downX = 1;
    public static int downY = 0;
    public static int rightX = 0;
    public static int rightY = 1;

    /**
     * Initialiseerd de platjes
     */
    public static void init() {

        playerLeft = loadImage("/player/links.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        playerBack = loadImage("/player/achter.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        playerFront = loadImage("/player/voor.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);
        playerRight = loadImage("/player/rechts.png").getScaledInstance(GameSokoban.TILESIZE, GameSokoban.TILESIZE, BufferedImage.SCALE_DEFAULT);


    }

    /**
     *
     * @param path Image buffer van de platjes
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
}
