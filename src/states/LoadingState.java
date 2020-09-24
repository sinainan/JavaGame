package states;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;
import graphics.Text;
import Game.Window;

public class LoadingState extends State{

    private final String NAME = "Sokoban Mario...";
    private String text = "";
    private int index = 0;
    private long time, lastTime;

    /**
     * Laaden van de loadingstate
     */
    public LoadingState(Window window){
        super(window);
        time = 0;
        lastTime = System.currentTimeMillis();
    }

    /***
     *   Tijdens het laden van de  loading state wordt gekeken hoelang het moet duren en wanneer hij naar de menu state moet gaan
     */
    @Override
    public void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(time > 150){

            text = NAME.substring(0, index);
            if(index < NAME.length()){
                index ++;

            }else{
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                State.currentState = window.getMenuState();
            }
            time = 0;
        }

    }


    /**
     *
     * @param g Laden van graphics en fonts
     */
    @Override
    public void render(Graphics g) {
        g.setFont(Assets.font50);
        Text.drawString(g, text, Window.WIDTH/2, Window.HEIGHT/2, true, Color.WHITE);
    }

}
