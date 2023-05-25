/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Entities.Player;
import Game.GamePanel;
import Utility.LoadSave;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author user
 */
public abstract class MapPoint implements Touchable {

    protected int x, y;
    protected Rectangle collision;
    protected BufferedImage img;

    public MapPoint(int x, int y, String file) {
        this.x = x;
        this.y = y;
        initCollision();
        initSprite(file);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean isGettingTouchBy(Player p) {
        return this.collision.contains(p.getHitbox());
    }

    protected abstract void initCollision();

    private void initSprite(String file) {
        this.img = LoadSave.Loadimage(file);
    }

    public void draw(Graphics2D g, int xLvlOffset) {
        g.drawImage(img, this.x - xLvlOffset, this.y, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE, null);
    }

    void update(Graphics2D g, int xLvlOffset) {
        updateCollision(xLvlOffset);
        draw(g, xLvlOffset);
    }

    abstract void updateCollision(int xLvlOffset);
}
