/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Game.GamePanel;
import Level.MapPoint;
import static Utility.LoadSave.SPIKE;
import java.awt.Graphics2D;

/**
 *
 * @author user
 */
public class Spike extends Hazard {

    public Spike(int x, int y) {
        super(x, y, SPIKE);
    }

    @Override
    void updateCollision(int xLvlOffset) {
        this.collision.x = this.x - xLvlOffset;
        this.collision.y = this.y;
    }

}
