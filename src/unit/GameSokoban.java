package unit;

import java.awt.Graphics;
import java.awt.Image;

import graphics.Assets;
import Game.Window;
import io.KeyBoard;
import states.LevelSelectorState;
import states.State;
import ui.Button;
import ui.Click;

public class GameSokoban {

    public static final int TILESIZE = 48;

    private int[][] maze;

    private int[][] copy;

    private int player_row, player_col;

    private int count = 0;

    private Image texture;

    private int xOffset, yOffset;

    public static long time, lastTime;

    public static final int DELAY = 150;

    private Button restart, back;

    private boolean solved;

    private int plaStartRow, plaStartCol;

    private LevelSelectorState levelSelectorState;

    public static int ID = 0;

    private int id;

    /**
     *
     * @param maze -> Dit is de doolhof waar de tekst bestanden van de levels wordt ingevoerd.
     *             Nadat de level is ingevoerd in maze wordt het in copy gezet om de originale maze niet kwijt te raken.
     * @param player_row Dit is de player row dit hij uit de tekst bestand pakt
     * @param player_col Dit is de player collom die hij uit de tekst bestand pakt
     * @param levelSelectorState Dit is de functie waar hij de level selecteerd en laad in deze class
     */
    public GameSokoban(int[][] maze, int player_row, int player_col, LevelSelectorState levelSelectorState){
        this.levelSelectorState = levelSelectorState;
        this.maze = maze;
        ID ++;
        id = ID;
        copy = new int[maze.length][maze[0].length];
        for(int row = 0; row < maze.length; row++){
            for(int col = 0; col < maze[row].length; col ++)
                copy[row][col] = maze[row][col];
            plaStartRow = player_row;
            plaStartCol = player_col;
            this.player_row = player_row;
            this.player_col = player_col;
            if(ID == 1)
                solved = true;
            else
                solved = false;
            xOffset = (Window.WIDTH - maze[0].length*TILESIZE)/2;
            yOffset = (Window.HEIGHT - maze.length*TILESIZE)/2;
            texture = Speler.playerFront;
            restart = new Button("RESTART", 100, Window.HEIGHT/2, new Click(){

                @Override
                public void onClick() {
                    reset();
                    count = 0;

                }},
                    Assets.font30);
            back = new Button("BACK", Window.WIDTH - 100, Window.HEIGHT/2, new Click(){

                @Override
                public void onClick() {
                    State.currentState = levelSelectorState;

                }},
                    Assets.font30);


        }

        time = 0;
        lastTime = System.currentTimeMillis();
    }

    /**
     * Reset functie reset de maze
     */
    private void reset(){
        for(int row = 0; row < maze.length; row++)
            for(int col = 0; col < maze[row].length; col ++)
                maze[row][col] = copy[row][col];
        texture = Speler.playerFront;
        player_row = plaStartRow;
        player_col = plaStartCol;

    }

    /***
     *      Dit is de keyboard functie dit steeds kijkt of een een update gamaakt wordt van de keyboard
     */
    public void update(){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();


        if(KeyBoard.UP && time > DELAY){
            check(Speler.upX, Speler.upY);
            texture = Speler.playerBack;
        }
        if(KeyBoard.LEFT && time > DELAY){
            check(Speler.leftX, Speler.leftY);
            texture = Speler.playerLeft;
        }
        if(KeyBoard.DOWN && time > DELAY){
            check(Speler.downX, Speler.downY);
            texture = Speler.playerFront;
        }
        if(KeyBoard.RIGHT && time > DELAY){
            check(Speler.rightX, Speler.rightY);
            texture = Speler.playerRight;
        }



        restart.update();
        back.update();

        for(int row = 0; row < maze.length; row++)
            for(int col = 0; col < maze[row].length; col ++)
                if(maze[row][col] == 2)
                    return;

        levelSelectorState.getGameSokobans()[id].setSolved(true);
        State.currentState = levelSelectorState;

    }


    /***
     *
     * @param row Hier wordt de row gecheckt
     * @param col hier wordt de collom gecheckt
     */
    public void check(int row, int col){
        if(maze[player_row + row][player_col + col] != 1){ // Hier wordt de muren gecontrolleerd met de row en de collom
            if(maze[player_row + row][player_col + col] == 2 || maze[player_row + row][player_col + col] == 4){
                if(maze[player_row + row*2][player_col + col*2] == 1 ||
                        maze[player_row + row*2][player_col + col*2] == 2 ||
                        maze[player_row + row*2][player_col + col*2] == 4)
                    return;
                if(maze[player_row + row][player_col + col] == 4){
                    maze[player_row + row][player_col + col] = 3;
                    if(maze[player_row + row*2][player_col + col*2] == 3)
                        maze[player_row + row*2][player_col + col*2] = 4;
                    else
                        maze[player_row + row*2][player_col + col*2] = 2;
                }else{
                    maze[player_row + row][player_col + col] = 0;
                    if(maze[player_row + row*2][player_col + col*2] == 3)
                        maze[player_row + row*2][player_col + col*2] = 4;
                    else
                        maze[player_row + row*2][player_col + col*2] = 2;

                }


            }


            player_row += row;
            player_col += col;


            if(row == +1 || col == +1 || row == -1 || col == -1) { // Dit is de count voor aantal verplaatsingen
                count = count + 1;
            }


        }
        time = 0;
    }

    /***
     *
     * @param g Dit is de Graphics module waar de images worden geladen
     */

    public void render(Graphics g){

        restart.render(g);
        back.render(g);


        for(int row = 0; row < maze.length; row++){
            for(int col = 0; col < maze[row].length; col ++){
                g.drawImage(Assets.floor, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
                if(maze[row][col] == 1)
                    g.drawImage(Assets.wall, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
                if(maze[row][col] == 2)
                    g.drawImage(Assets.boxOff, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
                if(maze[row][col] == 3)
                    g.drawImage(Assets.spot, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
                if(maze[row][col] == 4)
                    g.drawImage(Assets.boxOn, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
            }
        }

        g.drawImage(texture, xOffset + player_col*TILESIZE, yOffset + player_row*TILESIZE, null);
        g.drawString("Moves : " + count, 300, 50);




    }

    /***
     *
     * @return geeft een return waarde true
     */
    public boolean isSolved(){return solved;}

    /**
     *
     * @param bool zet de level op solved true
     */
    public void setSolved(boolean bool){solved = bool;}
}
