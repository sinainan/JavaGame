package states;

import java.awt.Graphics;

import Game.Window;
import unit.GameSokoban;

public class GameState extends State{

    private GameSokoban gameSokoban;

    /**
     *    Hier wordt de state van het spel geladen en geupdate
     */

    public GameState(Window window) {super(window); }

    @Override
    public void update() {
        gameSokoban.update();
    }

    @Override
    public void render(Graphics g) {
        gameSokoban.render(g);
    }

    public void setGameSokoban(GameSokoban gameSokoban){
        this.gameSokoban = gameSokoban;
    }

}