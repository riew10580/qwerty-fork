/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Entities.Player;
import static Game.GamePanel.SCALE;
import static Game.GamePanel.TILES_DSIZE;
import static Utility.LoadSave.GOAL;
import java.awt.Rectangle;

/**
 *
 * @author user
 */
// ? cannot find
public class Goal extends MapPoint {

    private LevelManager manager;

    public Goal(int x, int y, LevelManager mn) {
        super(x, y, GOAL);
        this.manager = mn;
    }

    @Override
    protected void initCollision() {
        this.collision = new Rectangle(x, y, SCALE * TILES_DSIZE + 10, SCALE * TILES_DSIZE + 10);
    }

    @Override
    void updateCollision(int xLvlOffset) {
        this.collision.x = x - xLvlOffset;
        this.collision.y = y;
    }

    @Override
    public void activateTouchedBehavior(Player p) {
        manager.getGame().startNewLevel();
    }

}
