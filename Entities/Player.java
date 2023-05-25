package Entities;

import Token.PlayerAttack;
import UI.Status;
import Entities.Entity;
import Constants.Constants;
import Game.Game;
import static Game.GamePanel.TILES_SIZE;
import Level.Checkpoint;
import Level.LevelManager;
import Level.Touchable;
import Utility.HelpMethod;
import Utility.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Player extends Entity {

    private BufferedImage playerAnimation[][];
    private Status status;
    private Checkpoint checkpoint;
    private PlayerAttack attackbox;

    private int actions;
    private boolean attacking;
    private boolean allowPlayerAttack;

    private boolean left, up, right, down, jump;
    private int jumpSpeed = -7;

    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        this.facing = 1;
        this.HP = 100;
        this.maxHP = HP;
        this.attackDamage = 10;
        iframeDuration = 125;
        this.status = new Status(this);
        loadAnimations();
    }

    public PlayerAttack getAttackbox() {
        return attackbox;
    }

    public int getFacing() {
        return facing;
    }

    public Game getGame() {
        return this.game;
    }

    public void doesAttack() {
        if (allowPlayerAttack) {
            this.setAttacking(true);
            attackbox = new PlayerAttack(this);
        }
    }

    public void AttackEnd() {
        this.setAttacking(false);
        this.attackbox = null;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getHP() {
        return HP;
    }

    @Override
    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean getLeft() {
        return this.left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean getUp() {
        return this.up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean getRight() {
        return this.right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean getDown() {
        return this.down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    @Override
    public void loadAnimations() {
        // Store Animation frames in Array
        BufferedImage img = LoadSave.Loadimage(LoadSave.PLAYER_SPRITES);
        playerAnimation = new BufferedImage[5][6];
        for (int j = 0; j < playerAnimation.length; j += 1) {
            for (int i = 0; i < playerAnimation[j].length; i += 1) {
                playerAnimation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void jump() {
        if (attacking || inAir) {                                                            // if inAir , can't jump
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    public void updatePos() {

        moving = false;
        int speed1 = 0;
        if (jump) {
            this.actions = Constants.JUMP;
            jump();
        }
        if (!left && !right && !inAir) {
            this.actions = Constants.IDLE;
            moving = false;
            return;
        }
        if (left && !right) {
            this.actions = Constants.RUNNING;
            speed1 = -3;
            facing = -1;
        }
        if (right && !left) {
            this.actions = Constants.RUNNING;
            speed1 = 3;
            facing = 1;
        }
        if (!inAir) {
            this.allowPlayerAttack = true;
            if (HelpMethod.IsEntityOnFloor(hitbox, game.getLevelmanager().getCurrentLevel().getlevelData(), game.getxLvlOffset()) == false) {       // if not inAir and there's not floor, then Player is inAir                                               
                inAir = true;                                                   // aka walk of the ledge
            }
        }
        if (inAir) {
            this.allowPlayerAttack = false;
            if (HelpMethod.CanMove((hitbox.x), (int) (hitbox.y + airSpeed), hitbox.width, hitbox.height, game.getLevelmanager().getCurrentLevel().getlevelData(), game.getxLvlOffset())) {          // if Player remains inAir and is able to move inAir
                this.y += airSpeed;                                             // move Player down due to gravity
                airSpeed += gravity;                                            // increase downward speed as Player still remains inAir
                updateXPos(speed1);
            } else {
                if (airSpeed > 0) {
                    inAir = false;
                    this.allowPlayerAttack = true;
                    airSpeed = 0;
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(speed1);
            }
        } else {
            updateXPos(speed1);
        }
        moving = true;
    }

    public void updateXPos(int speed1) {
        if (!attacking & HelpMethod.CanMove((hitbox.x) + speed1, (hitbox.y), hitbox.width, hitbox.height, game.getLevelmanager().getCurrentLevel().getlevelData(), game.getxLvlOffset())) {                      // if next point in Player direction is walkable
            this.x += speed1;
        }
    }

    public void update(int xLvlOffset) {
        updatePos();
        setAnimation();
        updateAnimations();
        int Xindex = ((x + game.getxLvlOffset()) / TILES_SIZE);
        int Yindex = (y / TILES_SIZE);
    }

    public void setAnimation() {
        int startAni = this.actions;
        if (moving) {
            this.setActions(Constants.RUNNING);
        } else {
            this.setActions(Constants.IDLE);
        }

        if (inAir) {
            if (airSpeed < 0) {
                this.setActions(Constants.JUMP);
            } else {
                this.setActions(Constants.FALLING);
            }
        }
        if (allowPlayerAttack && attacking) {
            this.setActions(Constants.ATTACK);
        }
        // some weird animation glitch happen when change animation change to attack  FIXED
        if (allowAnimReset && startAni != this.actions) {                                         // change ani = reset to first frame
            //somehow this thing is exclusive for attack animation
            allowAnimReset = false;
            frameTime = 0;
            animationIndex = 0;

        }

    }

    public void updateAnimations() {
        frameTime += 1;
        if (frameTime >= frameDuration) {
            // as Frames exceed , it move to next Frame
            animationIndex += 1;
            frameTime = 0;
            if (animationIndex >= Constants.GetSpriteAmount(actions)) {                // reset to first frame of actions
                this.attackbox = null;
                allowAnimReset = true;
                animationIndex = 0;
                attacking = false;                                              // attack is done after an attack
            }
        }
    }

    @Override
    public void updateHitbox() {
        hitbox.x = x + 45 - game.getxLvlOffset();             // 40 is player Sprite Offset in X
        hitbox.y = y + 11;                                    // 11 is in Y
    }

    private void drawPlayer(Graphics2D g) {
        if (iframe && iframetick <= 75) {
            // set opacity
            AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
            g.setComposite(alcom);
        } else {
            AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0);
            g.setComposite(alcom);
        }

        if (facing == -1) {
            g.drawImage(playerAnimation[actions][animationIndex], x - game.getxLvlOffset() + 128, y, 128 * -1, 80, null);
        } else {
            g.drawImage(playerAnimation[actions][animationIndex], x - game.getxLvlOffset(), y, 128, 80, null);
        };
    }

    public void setCheckpoint(Checkpoint cp) {
        this.checkpoint = cp;
    }

    public void respawn() {
        setPlayerLocation(checkpoint.getX(), checkpoint.getY());
        getHit();
    }

    public void render(Graphics2D g) {
        this.update(game.getxLvlOffset());
        status.render(g);
        updateIFrame();
        drawPlayer(g);
        updateHitbox();
        checkPlayerTouchingPoint();
        checkAlive();
    }

    private void checkPlayerTouchingPoint() {
        game.getLevelmanager().isPlayerTouchingPoint(this);
    }

    public void touched(Touchable point) {
        point.activateTouchedBehavior(this);
    }

    public void Interact() {
        System.out.println("interacting");
        if (game.getLevelmanager().isPlayerinGoal(this)) {
            game.getLevelmanager().getGoal().activateTouchedBehavior(this);
        }
    }

    public void setPlayerLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void checkAlive() {
        if (this.HP <= 0) {
            game.reStartLevel();
        }
    }

}
