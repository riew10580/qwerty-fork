/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import static Constants.Constants.RANGE_DIE;
import Token.Arrow;
import static Constants.Constants.RANGE_IDLE;
import static Constants.Constants.RANGE_MOVING;
import static Constants.Constants.SLIME_DIE;
import static Constants.Constants.SLIME_IDLE;
import static Constants.Constants.SLIME_MOVING;
import static Constants.Constants.TYPE_RANGE;
import Game.Game;
import Utility.HelpMethod;
import static Utility.HelpMethod.isLOSclear;
import Utility.LoadSave;
import static Utility.LoadSave.RANGE_SPRITES;
import java.awt.image.BufferedImage;

/**
 *
 * @author user
 */
public class RangedMob extends Enemy {

    private BufferedImage[][] rangeSprite;
    private Arrow arrow = null;

    public RangedMob(int x, int y, Game game) {
        super(x, y, 64, 64, TYPE_RANGE, game);
        this.attackDamage = 15;
        this.HP = 40;
        this.PlayerDetectionRangeX = 2000;
        this.PlayerDectectionRangeY = 1500;
        this.attackRange = this.PlayerDetectionRangeX;
        this.walkSpeed = 5;
        this.setEnemyActions(RANGE_IDLE);
        loadAnimations();
        this.allowAttack = true;
    }

    public Arrow getArrow() {
        return arrow;
    }

    @Override
    protected void updateBehavior() {
        if (isPlayerInSight(game.getPlayer(), game.getLevelmanager().getCurrentLevel().getlevelData(), 0)) {
            turntoPlayer(game.getPlayer());
            attackPlayer(game.getPlayer());
        }
    }

    @Override
    public void update() {
        updateAnimations();
        updateHitbox();
        PlayerInteraction(game.getPlayer());
        updateBehavior();
        updateAttackInterval();
        move(game.getLevelmanager().getCurrentLevel().getlevelData());

        updateArrow();
    }

    @Override
    protected void attackPlayer(Player p) {
        if (allowAttack) {
            if (Math.abs(p.getY() - this.getY()) <= 50) {
                if (this.arrow == null) {
                    this.arrow = new Arrow(this, p);
                    this.allowAttack = false;
                }
            }
        }
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    @Override
    public void updateHitbox() {
        // temporary
        hitbox.x = x - game.getxLvlOffset();
        hitbox.y = y;
    }

    @Override
    public void loadAnimations() {
        BufferedImage img = LoadSave.Loadimage(RANGE_SPRITES);
        rangeSprite = new BufferedImage[1][6];
        for (int j = 0; j < rangeSprite.length; j += 1) {
            for (int i = 0; i < rangeSprite[j].length; i += 1) {
                rangeSprite[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    @Override
    protected void setAnimation() {
        int startAni = this.enemyActions;
        if (this.getHP() <= 0) {
            this.setEnemyActions(RANGE_DIE);
        }
        if (moving) {
            this.setEnemyActions(RANGE_MOVING);
        } else {
            this.setEnemyActions(RANGE_IDLE);
        }

        if (inAir) {
            this.setEnemyActions(RANGE_IDLE);
        }

        if (allowAnimReset && startAni != this.enemyActions) {                                         // change ani = reset to first frame
            //somehow this thing is exclusive for attack animation
            allowAnimReset = false;
            frameTime = 0;
            animationIndex = 0;
        }
    }

    @Override
    public void move(int[][] levelData) {
        // this mob doesn't move;
    }

    public BufferedImage[][] getRangeSprite() {
        return rangeSprite;
    }

    private void updateArrow() {
        try {
            arrow.launch();
            arrow.updateAnimations();
        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void updateAttackInterval() {
        if (!allowAttack) {
            attacktick++;
        }
        if (attacktick >= attackInterval) {
            this.allowAttack = true;
            attacktick = 0;
        }
    }

}
