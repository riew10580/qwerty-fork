package Game;

import Utility.KeyboardInputs;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    // +
    private Game game;
    public final static int TILES_DSIZE = 32;
    public final static int SCALE = 2;
    public final static int TILES_WIDTH= 26;                                        // Visible Tile on screen in Width
    public final static int TILES_HEIGHT = 14;                                      // Visible Tile on screen in Height
    public final static int TILES_SIZE = TILES_DSIZE * SCALE;
    public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;
    // +
    public Game getGame(){
        return this.game;
    }

    // +
    public GamePanel(Game game){
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
    }
// same in src/main/GamePanel.java
//    public void setPanelSize(){
//        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
//        setPreferredSize(size);
//    }

// same in src/main/GamePanel.java
//    @Override
//    public void paintComponent(Graphics g){
//        Graphics2D g2d = (Graphics2D) g;
//        super.paintComponent(g2d);
//        game.render(g2d);
//    }

}
