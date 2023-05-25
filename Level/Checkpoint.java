/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Entities.Player;
import static Game.GamePanel.SCALE;
import static Game.GamePanel.TILES_DSIZE;
import Utility.LoadSave;
import static Utility.LoadSave.CHECKPOINT;
//import static Utility.LoadSave.CP;
import java.awt.Rectangle;

/**
 *
 * @author user
 */
// ? cannot find
public class Checkpoint extends MapPoint {

    public Checkpoint(int x, int y) {
        super(x, y - 12, CHECKPOINT);
    }

    @Override
    public void activateTouchedBehavior(Player p) {
        p.setCheckpoint(this);
    }

    @Override
    protected void initCollision() {
        this.collision = new Rectangle(x - 100, y - 100, TILES_DSIZE * SCALE * 3, TILES_DSIZE * SCALE * 3);
    }

    @Override
    void updateCollision(int xLvlOffset) {
        collision.x = this.x - 100 - xLvlOffset;
        collision.y = this.y - 100;
    }

}
