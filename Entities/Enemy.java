package Entities;

import Token.PlayerAttack;
import Constants.Constants;
import static Constants.Constants.SLIME_MOVING;
import Game.Game;
import Game.GamePanel;
import static Game.GamePanel.GAME_HEIGHT;
import static Game.GamePanel.GAME_WIDTH;
import static Game.GamePanel.SCALE;
import static Game.GamePanel.TILES_DSIZE;
import Utility.HelpMethod;
import static Utility.HelpMethod.CanMove;
import static Utility.HelpMethod.IsSolid;
import java.awt.Rectangle;
import static Utility.HelpMethod.isLOSclear;

public abstract class Enemy extends Entity {

    protected int attackRange;
    protected int PlayerDetectionRangeX, PlayerDectectionRangeY;
    protected int enemyType;                                                 // type of enemy i.e slime , skeleton, creeper, etc
    protected int enemyActions;                                                 // i.e 1 for IDLE 2 for RUNNING 3 for ATTACK 4 for DEAD
    protected boolean allowAttack;
    protected int attackInterval;
    protected int attacktick;
    protected int walkSpeed;
    protected boolean alert;
    protected EnemyManager enemyManager;

    public Enemy(int x, int y, int width, int height, int enemyType, Game game) {
        super(x, y, width, height, game);
        this.enemyType = enemyType;
        this.PlayerDetectionRangeX = 500;
        this.PlayerDectectionRangeY = 300;
        this.allowAttack = true;
        this.attackInterval = 300;
        this.alert = false;
    }

    public void updateAnimations() {
        setAnimation();
        updateIFrame();
        frameTime += 1;        
        if (frameTime >= frameDuration) {
            // as Frames exceed frametime, it move to next Frame
            animationIndex += 1;
            frameTime = 0;
            if (animationIndex >= Constants.GetSpriteAmount(enemyType, enemyActions)) {        // exceed Sprite amount for that actions
                animationIndex = 0;                                                            // reset animation to frame 1
            }
        }
    }

    public boolean isOnScreen() {
        return (this.x - game.getxLvlOffset() >= -200 && this.x - game.getxLvlOffset() <= GAME_WIDTH);

    }
    
    protected void setEnemyActions(int enemyActions) {
        this.enemyActions = enemyActions;
    }

    public void update() {
        updateAnimations();
        updateHitbox();
        PlayerInteraction(game.getPlayer());
        updateBehavior();
        updateAttackInterval();
        move(game.getLevelmanager().getCurrentLevel().getlevelData());
    }

    public void PlayerInteraction(Player player) {
        checkContactPlayer(player);
        this.isGettingHit(player);
    }

    public void isGettingHit(Player p) {
        if (p.getAttackbox() != null && !iframe && this.hitbox.intersects(p.getAttackbox().getHitbox())) {
            this.getHit(p);
        }

    }

    private void checkContactPlayer(Player player) {
        if (!player.iframe && this.hitbox.intersects(player.getHitbox())) {     // if player is not in iframe player will get hit
            player.getHit(this);
        }
    }

    protected boolean isPlayerInSight(Player p, int[][] levelData, int xLvlOffset) {
        int deltaX = Math.abs(p.getX() - this.getX());
        int deltaY = Math.abs(p.getY() - this.getY());
        if (deltaX <= PlayerDetectionRangeX && deltaY <= PlayerDectectionRangeY && isLOSclear(p, this, levelData, xLvlOffset)) {
            this.alert = true;
            return true;
        } else {
            this.alert = false;
            return false;
        }

    }

    protected abstract void updateBehavior();

    protected void turntoPlayer(Player p) {
        if (facing != -1 && p.getX() < this.getX()) {
            this.facing = -1;
        } else if (facing != 1 && p.getX() >= this.getX()) {
            this.facing = 1;
        }
    }

    protected boolean PlayerInAttackRange(Player p) {
        int deltaX = Math.abs(p.getX() - this.getX());
        return deltaX <= attackRange;
    }

    protected abstract void attackPlayer(Player p);

    protected void updateAttackInterval() {
        if (!allowAttack) {
            attacktick++;
            if (attacktick >= attackInterval) {
                this.allowAttack = true;
                attacktick = 0;
            }
        }
    }

    protected void move(int[][] levelData) {
        if (!alert && checkEdge(levelData)) {
            this.facing *= -1;
        } else if (alert && checkEdge(levelData)) {
            updatePos(0, levelData);
            this.animationIndex = 0;
            return;
        }
        if (this.facing == -1) {
            updatePos(-walkSpeed, levelData);
        } else if (this.facing == 1) {
            updatePos(walkSpeed, levelData);
        }

    }
    
    public int getEnemyActions() {
        return enemyActions;
    }

    protected void updatePos(int walkSpeed, int[][] levelData) {
        checkInAir(levelData);
        this.x += walkSpeed;
        if (walkSpeed != 0) {
            this.moving = true;
        } else if (walkSpeed == 0) {
            this.moving = false;
        }
    }

    private boolean checkEdge(int[][] levelData) {
        // left edge
        boolean con1 = CanMove((hitbox.x + walkSpeed * facing), (int) (hitbox.y), hitbox.width*0, hitbox.height, levelData, game.getxLvlOffset());
        // right edge
        boolean con2 = CanMove((hitbox.x + walkSpeed * facing) + hitbox.width, (int) (hitbox.y), hitbox.width*0, hitbox.height, levelData, game.getxLvlOffset());
        boolean con3 = IsSolid(x + walkSpeed * facing * 2, y, levelData, 0);
        boolean con4 = IsSolid((int) (x + this.getHitbox().getWidth() + walkSpeed * facing * 2), y, levelData, 0);
        if (con1 || con2 || con3 || con4) {
            return true;
        }
        return false;
    }
}
