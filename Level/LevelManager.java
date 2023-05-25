package Level;

import Entities.Player;
import Game.Game;
import Game.GamePanel;
import Utility.LoadSave;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private int currentLevelNumber;
    private BufferedImage levelimg[];
    private int leveldex;
    private Level currentLevel;
    private Game game;
    private ArrayList<MapPoint> mappoint;
    private Spawn spawnpoint;
    private Goal goal;

    private int lastLvlNumber = 4;

    public LevelManager(Game game) {
        currentLevelNumber = 1;
        loadLevel();                                                            // called TILESET
        this.game = game;
        currentLevel = new Level(LoadSave.GetLevelData(currentLevelNumber));
        initMappoint();
        initSpawn();
        initGoal();
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void ReloadThisLevel() {
        System.out.println("1");
        this.currentLevel = null;
        currentLevel = new Level(LoadSave.GetLevelData(currentLevelNumber));

        this.mappoint.clear();
        initMappoint();

        this.spawnpoint = null;
        initSpawn();

        game.getPlayer().setCheckpoint(spawnpoint);
        this.goal = null;

        initGoal();
        System.out.println("2");
    }

    public void LoadNextLevel() {
        currentLevelNumber += 1;
        if (currentLevelNumber > lastLvlNumber) {
            currentLevelNumber = 1;
        }
        currentLevel = new Level(LoadSave.GetLevelData(currentLevelNumber));

        this.mappoint.clear();
        initMappoint();
        this.spawnpoint = null;
        initSpawn();
        game.getPlayer().setCheckpoint(spawnpoint);
        this.goal = null;
        initGoal();

    }

//    public void loadLevel() {
//        BufferedImage level = LoadSave.Loadimage(LoadSave.LEVEL_SPRITES);
//        levelimg = new BufferedImage[48];
//        for (int j = 0; j < 4; j += 1) {
//            for (int i = 0; i < 12; i += 1) {
//                leveldex = j * 12 + i;
//                levelimg[leveldex] = level.getSubimage(i * 32, j * 32, 32, 32);
//            }
//        }
//    }

    public void render(Graphics2D g) {
        drawMap(g);
        drawPoint(g);
        drawGoal(g);
    }

    private void drawMap(Graphics2D g) {                                                     // Draw Map according to value from loadLevel 
        for (int j = 0; j < GamePanel.TILES_HEIGHT; j += 1) {                                                // also move by xLvlOffset
            for (int i = 0; i < currentLevel.getlevelData()[0].length; i += 1) {                                 // but only move img, not the map collision
                int index = currentLevel.getSpriteIndex(i, j);
                g.drawImage(levelimg[index], i * GamePanel.TILES_SIZE - game.getxLvlOffset(), j * GamePanel.TILES_SIZE, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE, null);
            }
        }

    }

    private void drawPoint(Graphics2D g) {
        for (MapPoint mp : mappoint) {
            mp.update(g, game.getxLvlOffset());
        }
    }

    private void drawGoal(Graphics2D g) {
        g.drawImage(goal.img, goal.getX() - game.getxLvlOffset(), goal.getY(), 64, 64, null);
        goal.updateCollision(game.getxLvlOffset());
    }

    public void isPlayerTouchingPoint(Player player) {
        for (MapPoint point : mappoint) {
            if (point.isGettingTouchBy(player)) {
                player.touched(point);
            }
        }

    }

    private void initMappoint() {
        this.mappoint = LoadSave.getMappoint(currentLevelNumber);
    }

    private void initSpawn() {
        this.spawnpoint = null;
        this.spawnpoint = LoadSave.getSpawn(currentLevelNumber);
    }

    public Spawn getSpawnPoint() {
        return this.spawnpoint;
    }

    private void initGoal() {
        this.goal = null;
        this.goal = LoadSave.createGoal(currentLevelNumber, this);
    }

    public boolean isPlayerinGoal(Player p) {
        return goal.collision.contains(p.getHitbox());
    }

    public Goal getGoal() {
        return this.goal;
    }

    public Game getGame() {
        return this.game;
    }

    public BufferedImage[] getLevelimg() {
        return levelimg;
    }

    public int getLeveldex() {
        return leveldex;
    }

    public ArrayList<MapPoint> getMappoint() {
        return mappoint;
    }

    public Spawn getSpawnpoint() {
        return spawnpoint;
    }

}
