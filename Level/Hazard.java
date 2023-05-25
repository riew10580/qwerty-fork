/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Entities.Player;
import static Game.GamePanel.SCALE;
import static Game.GamePanel.TILES_DSIZE;
import Utility.LoadSave;
import static Utility.LoadSave.SPIKE;
import java.awt.Rectangle;

/**
 *
 * @author user
 */
public abstract class Hazard extends MapPoint {

    public Hazard(int x, int y, String file) {
        super(x, y, file);
    }

    @Override
    public boolean isGettingTouchBy(Player p) {
        int px = (int) p.getHitbox().getX();
        int py = (int) p.getHitbox().getY();
        return this.collision.contains(p.getHitbox()) || this.collision.contains(px, py);
    }

    @Override
    public void activateTouchedBehavior(Player p) {
        p.respawn();
    }

    @Override
    protected void initCollision() {
        this.collision = new Rectangle(x, y, SCALE * TILES_DSIZE, SCALE * TILES_DSIZE);
    }

}
