package Entities;

import Utility.LoadSave;
import Entities.Enemy;
import Constants.Constants;
import static Constants.Constants.*;
import Game.Game;
import static Game.GamePanel.TILES_SIZE;
import static Utility.HelpMethod.CanMove;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Slime extends Enemy {

    private BufferedImage[][] slimeSprite;

    public Slime(int x, int y, Game game) {
        super(x, y, 50, 37, TYPE_SLIME, game);
        this.attackDamage = 5;
        this.HP = 25;
        this.attackRange = this.PlayerDetectionRangeX;
        this.walkSpeed = 1;
        this.enemyActions = SLIME_MOVING;
        loadAnimations();
    }

    @Override
    public void loadAnimations() {
        // Store Animation frames in Array
        BufferedImage img = LoadSave.Loadimage(LoadSave.SLIME_SPRITES);
        slimeSprite = new BufferedImage[1][10];
        for (int j = 0; j < slimeSprite.length; j += 1) {
            for (int i = 0; i < slimeSprite[j].length; i += 1) {
                slimeSprite[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);
            }
        }
    }

    public BufferedImage[][] getSlimeSprite() {
        return slimeSprite;
    }

    @Override
    public void attackPlayer(Player p) {
        // this slime does nothing
        // it just only want to hug
    }

    @Override
    protected void updateBehavior() {
        if (isPlayerInSight(game.getPlayer(), game.getLevelmanager().getCurrentLevel().getlevelData(), 0)) {
            turntoPlayer(game.getPlayer());
        }
    }

    @Override
    protected void setAnimation() {
        // slime always move
        // so only move animation is needed
    }

    @Override
    public void updateHitbox() {
        hitbox.x = x - game.getxLvlOffset() + 40;
        hitbox.y = y + 28;
    }
}
