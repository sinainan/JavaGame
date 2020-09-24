package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import graphics.Assets;
import graphics.Text;
import io.MouseManager;
import Game.Window;
import unit.GameSokoban;
import ui.Button;
import ui.Click;

public class LevelSelectorState extends State{
    private final int DOUBLETILESIZE = 64;
    private GameSokoban[] gameSokobans = new GameSokoban[30];
    private final int xOffset = (Window.WIDTH - DOUBLETILESIZE*6)/2;
    private final int yOffset = (Window.HEIGHT - DOUBLETILESIZE*5)/2;

    private Button back;

    /**
     *
     * Hier laad hij een level van de spel met een for loop en met de back knop kan je terug naar de menu
     */
    public LevelSelectorState(Window window) {
        super(window);

        for(int i = 0; i < gameSokobans.length; i++)
            gameSokobans[i] = loadLevel("/levels/"+i+".txt");

        back = new Button("BACK", Window.WIDTH/2, Window.HEIGHT - 100, new Click(){

            @Override
            public void onClick() {
                State.currentState = window.getMenuState();
            }

        }, Assets.font30);


    }

    @Override
    public void update(){
        back.update();
    }

    /**
     *
     * @param g Laaden van de graphics en checkt of de spel is opgelost of hij de nieuwe spel kan unlocken
     */
    @Override
    public void render(Graphics g){
        back.render(g);
        int counter = 1;
        g.setFont(Assets.font30);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                Rectangle bounds = new Rectangle(xOffset + j*DOUBLETILESIZE,
                        yOffset + i*DOUBLETILESIZE, DOUBLETILESIZE, DOUBLETILESIZE);
                if(bounds.contains(MouseManager.x, MouseManager.y)){
                    if(MouseManager.left && gameSokobans[counter-1].isSolved()){
                        ((GameState)window.getGameState()).setGameSokoban(gameSokobans[counter-1]);
                        State.currentState = window.getGameState();
                    }
                    g.drawImage(Assets.outline2, bounds.x, bounds.y, null);
                    if(gameSokobans[counter-1].isSolved())
                        Text.drawString(g, counter+"", xOffset + DOUBLETILESIZE/2 + j*DOUBLETILESIZE,
                                yOffset + DOUBLETILESIZE/2 + i*DOUBLETILESIZE, true, Color.black);
                    else
                        Text.drawString(g,"?", xOffset + DOUBLETILESIZE/2 + j*DOUBLETILESIZE,
                                yOffset + DOUBLETILESIZE/2 + i*DOUBLETILESIZE, true, Color.black);
                }else{
                    g.drawImage(Assets.outline, bounds.x, bounds.y, null);
                    if(gameSokobans[counter-1].isSolved())
                        Text.drawString(g, counter+"", xOffset + DOUBLETILESIZE/2 + j*DOUBLETILESIZE,
                                yOffset + DOUBLETILESIZE/2 + i*DOUBLETILESIZE, true, Color.orange);
                    else
                        Text.drawString(g,"?", xOffset + DOUBLETILESIZE/2 + j*DOUBLETILESIZE,
                                yOffset + DOUBLETILESIZE/2 + i*DOUBLETILESIZE, true, Color.white);
                }
                counter ++;
            }
        }

    }

    /**
     *
     * @param path  Hier laad hij de tekst bestanden van de levels en voert hij ze in de maze en de player
     * @return
     */
    private GameSokoban loadLevel(String path){

        String file = loadFileAsString(path);
        String[] numbers = file.split("\\s+");
        int cols = parseInt(numbers[0]);
        int rows = parseInt(numbers[1]);
        int player_col = parseInt(numbers[2]);
        int player_row = parseInt(numbers[3]);
        int[][] maze = new int[rows][cols];
        for(int row = 0; row < rows; row++)
            for(int col = 0; col < cols; col++)
                maze[row][col] = parseInt(numbers[(col + (row*cols)) + 4]);

        return new GameSokoban(maze, player_row, player_col, this);
    }

    public GameSokoban[] getGameSokobans(){return gameSokobans;}


    /**
     *
     * @param path met deze leest hij de tekst bestand en zet het om in een string
     * @return
     */
    public static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();
        try{
            InputStream in = LevelSelectorState.class.getResourceAsStream(path);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null){
                builder.append(line+ "\n");
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     *
     * @param numero Hier parse hij alle strings in een int
     * @return
     */
    public static int parseInt(String numero){
        try{
            return Integer.parseInt(numero);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

}
