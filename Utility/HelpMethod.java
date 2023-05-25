package Utility;

import Entities.Enemy;
import Entities.Player;
import Game.Game;
import Game.GamePanel;
import static Game.GamePanel.SCALE;
import static Game.GamePanel.TILES_SIZE;
import java.awt.Rectangle;

public class HelpMethod {

    //src/utilz/HelpMethods.java 10-17
    public static boolean CanMove(int x, int y, int width, int height, int[][] levelData, int xLvlOffset) {
        if (IsSolid(x, y, levelData, xLvlOffset) == false) {
            if (IsSolid(x + width, y + height, levelData, xLvlOffset) == false) {
                if (IsSolid(x + width, y, levelData, xLvlOffset) == false) {
                    if (IsSolid(x, y + height, levelData, xLvlOffset) == false) {
                        return true;                                                                                    // If every corner of hitbox is not colliding with any solid tile
                    }
                }
            }
        }
        return false;
    }
    // +
    public static boolean inAirCheck(int x, int y, int[][] levelData) {
        int Xindex = x / GamePanel.TILES_SIZE;
        int Yindex = y / GamePanel.TILES_SIZE;
        int value = levelData[Yindex][Xindex];
        if (value == 11) {                                                                                                // If Player is in mapping color R11, they are inAir
            return true;
        }
        return false;
    }

//    public static boolean IsSolid(int x, int y, int[][] levelData, int xLvlOffset) {                                                     // Is not walkable
//        // int x, y is Player position
//        int maxWidth = (levelData[0].length) * TILES_SIZE ;                                                      // right most position of level
//        if (x < 0 || x >= maxWidth) {                                                                                     // exceed map edge, not walkable
//            return true;
//        }
//        if (y < 0 || y >= GamePanel.GAME_HEIGHT) {                                                                        // exceed map edge, not walkable
//            return true;
//        }
//        int Xindex = ((x + xLvlOffset) / TILES_SIZE);
//        int Yindex = (y / TILES_SIZE);
//        // levelData array contain value which define walkable or not
//        try{
//            int value = levelData[Yindex][Xindex];
//            if (value >= 48 || value < 0 || value != 11) {                                                                    // certain mapping color is not walkable
//                return true;
//            }
//            else {
//                return false;
//            }
//        }
//        catch(Exception ex){
//            return true;
//        }
//    }

//    public static boolean IsEntityOnFloor(Rectangle hitbox, int[][] levelData, int xLvlOffset) {
//        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData, xLvlOffset)) {                          // btm left
//            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData, xLvlOffset)) {         // btm right
//                return false;
//                // hitbox.y + 1 because we check under hitbox's feet
//            }
//        }
//
//        return true;
//    }

    // +
    public static boolean isLOSclear(Player p, Enemy e, int[][] levelData, int xLvlOffset) {
        if (p.getX() <= e.getX()) {
            return isAllTileSolid(p.getX(), e.getY(), e.getX(), levelData, xLvlOffset);
        } else {
            return isAllTileSolid(e.getX(), e.getY(), p.getX(), levelData, xLvlOffset);
        }
    }
    // same in src/utilz/HelpMethods.java 118-123
    public static boolean isAllTileSolid(int x, int y, int x2, int[][] lvlData, int xLvlOffset) {
        for (int i = 0; i <= x2 - x; i++) {
            if (IsSolid(x + i, y, lvlData, xLvlOffset)) {
                return false;
            }
        }
        return true;
    }
}
