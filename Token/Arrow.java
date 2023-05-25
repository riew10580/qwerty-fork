package Token;

import Constants.Constants;
import Entities.Player;
import Entities.RangedMob;
import static Game.GamePanel.GAME_WIDTH;
import Utility.HelpMethod;
import static Utility.HelpMethod.CanMove;
import static Utility.HelpMethod.IsSolid;
import Utility.LoadSave;
import static Utility.LoadSave.ARROW;
import static Utility.LoadSave.RANGE_SPRITES;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author user
 */
// +
public class Arrow {

    private Rectangle hitbox;
    private RangedMob owner;
    private Player target;

    private int x, y;
    private int speed;
    private int angle;
    private BufferedImage[] arrowImg;
    private int direction;
    private int frameDuration = 15;
    private int frameTime;
    private int animationIndex;

    public Arrow(RangedMob owner, Player p) {
        this.owner = owner;
        this.target = p;
        loadAnimations();
        initHitbox(owner);
        this.speed = 5;
        this.direction = owner.getFacing();
    }

    public BufferedImage[] getArrowImg() {
        return arrowImg;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public RangedMob getOwner() {
        return owner;
    }

    private void initHitbox(RangedMob owner) {
        if (owner.getFacing() == 1) {
            this.hitbox = new Rectangle(owner.getHitbox().x + owner.getHitbox().width, owner.getHitbox().y, 40, 40);

        } else {
            this.hitbox = new Rectangle(owner.getHitbox().x - 36, owner.getHitbox().y, 40, 40);
        }

        this.x = hitbox.x;
        this.y = hitbox.y;
    }

    public void launch() {
        this.x += speed * direction;
        if (this.hitbox.intersects(target.getHitbox())) {
            target.getHit(owner);
            this.owner.setArrow(null);
        }

        if (!isOnScreen()) {
            this.owner.setArrow(null);
        }

        updateHitbox();
    }

    private void updateHitbox() {
        this.hitbox.x = this.x + 20;
        this.hitbox.y = y + 10;
    }

    public boolean isOnScreen() {
        return (this.x >= -200 && this.x <= GAME_WIDTH);

    }

    public void loadAnimations() {
        BufferedImage img = LoadSave.Loadimage(ARROW);
        arrowImg = new BufferedImage[6];
        for (int i = 0; i < arrowImg.length; i += 1) {
            arrowImg[i] = img.getSubimage(i * 32, 0, 32, 32);
        }
    }

    public void updateAnimations() {
        frameTime++;
        if (frameTime >= frameDuration) {
            animationIndex += 1;
            frameTime = 0;
            if (animationIndex >= 6) {        // exceed Sprite amount for that actions
                animationIndex = 0;                                                            // reset animation to frame 1
            }
        }

    }

    public int getAnimationIndex() {
        return animationIndex;

    }

    public int getDirection() {
        return direction;
    }
}
