package Entities;

import Token.PlayerAttack;
import Game.Game;
import Utility.HelpMethod;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {

    protected Game game;
    protected int x, y, width, height;
    protected Rectangle hitbox;
    protected int HP, maxHP, attackDamage;
    protected boolean active;
    protected boolean moving;

    // +
    protected int facing;
    protected double airSpeed = 0;
    protected double gravity = 0.2;
    protected boolean inAir = false;
    protected double fallSpeedAfterCollision = 0.1;

    protected int frameDuration = 15, frameTime, animationIndex;

    protected int iframetick, iframeDuration = 75;

    // + : add for delay invicible
    protected boolean iframe;

    // + :
    protected boolean allowAnimReset = true;

    public Entity(int x, int y, int width, int height, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // +
        hitbox = new Rectangle(x, y, width, height);
        this.facing = 1;
        this.active = true;
        this.game = game;
        // */
    }

    public Game getGame() {
        return game;
    }

    public int getFacing() {
        return facing;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void checkInAir(int[][] levelData) {
        if (!HelpMethod.IsEntityOnFloor(this.hitbox, levelData, game.getxLvlOffset())) {
            inAir = true;
        }

        if (inAir) {
            if (HelpMethod.CanMove((hitbox.x), (int) (hitbox.y + airSpeed), hitbox.width, hitbox.height, levelData, game.getxLvlOffset())) {
                this.y += airSpeed;
                airSpeed += gravity;
            } else {
                if (airSpeed > 0) {
                    inAir = false;
                    airSpeed = 0;
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
            }
        }
    }

    public abstract void updateHitbox();

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public abstract void loadAnimations();

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    // +
    protected void updateIFrame() {
        if (this.iframe) {
            iframetick++;
            if (iframetick > iframeDuration) {
                iframe = false;
                iframetick = 0;
            }
        }
    }

    // +
    // get hit by PlayerAttack
    public void getHit(Entity e) {
        if (!this.iframe) {
            this.setHP(this.getHP() - e.getAttackDamage());
            this.iframe = true;
            checkAlive();
        }
    }

    private void checkAlive() {
        if (this.getHP() <= 0) {
            this.active = false;
        }
    }

    // +
    // getHit by Hazard point (spike, hole, etc)
    public void getHit() {
        this.setHP(this.getHP() - (int) this.getMaxHP() / 10);
        this.iframe = true;
        checkAlive();
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    protected abstract void setAnimation();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // + : from stackoverflow; set opacity entity
    public void setOpacity(Graphics2D g) {
        if (iframe) {
            AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
            g.setComposite(alcom);
        } else {
            AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0);
            g.setComposite(alcom);
        }
    }
}
