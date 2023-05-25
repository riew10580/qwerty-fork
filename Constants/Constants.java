package Constants;

import Entities.Entity;
import Game.GamePanel;

public class Constants {

    // PLAYER
    public static final int IDLE = 0;
    public static final int RUNNING = 1;
    public static final int JUMP = 2;
    public static final int FALLING = 3;
    public static final int ATTACK = 4;
    public static final int HIT = 5;

    // PLAYER
    // ENEMY TYPE
    public static final int TYPE_SLIME = 100;
    public static final int TYPE_RANGE = 200;

    // ENEMY TYPE
    // ENEMY ACTIONS
    public static final int SLIME_IDLE = 1;
    public static final int SLIME_MOVING = 0;                                      // how do they move anyway ?
    public static final int SLIME_DIE = 2;

    public static final int RANGE_IDLE = 0;
    public static final int RANGE_MOVING = 1;
    public static final int RANGE_DIE = 2;

    // ENEMY ACTIONS
    public static int GetSpriteAmount(int actions) {
        switch (actions) {
            case IDLE -> {
                return 6;
            }
            case RUNNING -> {
                return 6;
            }
            case JUMP -> {
                return 4;
            }
            case FALLING -> {
                return 2;
            }
            case HIT -> {
                return 6;
            }
            case ATTACK -> {
                return 2;
            }
            default -> {
                return 1;
            }
        }

    }

    public static int GetSpriteAmount(int enemyType, int enemyActions) {
        switch (enemyType) {
            case TYPE_SLIME -> {
                switch (enemyActions) {
                    case SLIME_IDLE -> {
                        return 0;
                    }
                    case SLIME_MOVING -> {
                        return 10;
                    }
                    case SLIME_DIE -> {
                        return 0;
                    }
                    default -> {
                        return 0;
                    }
                }
            }
            case TYPE_RANGE -> {
                switch (enemyActions) {
                    case RANGE_IDLE -> {
                        return 6;
                    }
                    default -> {
                        return 0;
                    }
                }
            }
        }
        return 0;       // prevent netbean warning
    }
}
