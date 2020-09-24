package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Text;
import Game.Window;
import ui.Button;
import ui.Click;

public class MenuState extends State{

    private ArrayList<Button> buttons = new ArrayList<Button>();


    /**
     *
     * @param window  De menu wordt gemaakt met twee knoppen een play en een exit knop
     */
    public MenuState(Window window){
        super(window);
        buttons.add(new Button("PLAY", Window.WIDTH/2, Window.HEIGHT/2 + 50, new Click(){



            @Override
            public void onClick() {
                State.currentState = window.getLevelSelectorState();
            }}, Assets.font48));
        buttons.add(new Button("EXIT", Window.WIDTH/2, Window.HEIGHT/2 + 150, new Click(){

            @Override
            public void onClick() {
                System.exit(1);
            }}, Assets.font48));
    }

    /**
     *  een update check
     */
    @Override
    public void update() {
        for(int i = 0; i < buttons.size(); i++)
            buttons.get(i).update();
    }

    /**
     *
     * @param g Het aanmaken van de tekst
     */
    @Override
    public void render(Graphics g) {
        g.setFont(Assets.font48);
        Text.drawString(g, "SOKOBAN MARIO", Window.WIDTH/2, Window.HEIGHT/2 - 200, true, Color.white);
        for(int i = 0; i < buttons.size(); i++)
            buttons.get(i).render(g);
    }

}
