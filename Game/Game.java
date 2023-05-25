package Game;

import Entities.EnemyManager;
import Level.LevelManager;
import Utility.LoadSave;
import Entities.Player;
import Entities.Slime;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Game implements Runnable {

    private EnemyManager enemyManager;
    private GameWindow gamewindow;
    private GamePanel gamePanel;
    private Player player;
    private LevelManager levelmanager;
    private Thread gameThread = new Thread(this);
    private BufferedImage bgimg;
    private final int FPS_SET = 120;
    public int xLvlOffset;
    // Border help moving Graphics
    private int leftBorder = (int) (0.4 * GamePanel.GAME_WIDTH);                             // Left border is at 20% of GamePanel Width from left (X)
    private int rightBorder = (int) (0.6 * GamePanel.GAME_WIDTH);                            // Left border is at 20% of GamePanel Width from right (X)
    private int lvlTilesWide = LoadSave.GetLevelData(1)[0].length;                            // Level length ?
    private int maxTilesOffset = lvlTilesWide - GamePanel.TILES_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * GamePanel.TILES_SIZE;

    public Game() {

        bgimg = LoadSave.Loadimage(LoadSave.BG_IMAGE);
        levelmanager = new LevelManager(this);
        enemyManager = new EnemyManager(this);

        player = new Player(levelmanager.getSpawnPoint().getX(), levelmanager.getSpawnPoint().getY(), 36, 53, this);
        player.setCheckpoint(levelmanager.getSpawnPoint());
        player.checkInAir(levelmanager.getCurrentLevel().getlevelData());

        gamePanel = new GamePanel(this);
        gamePanel.setFocusable(true);
        gamewindow = new GameWindow(gamePanel);

        startGameLoop();
    }

    public void startNewLevel() {
        levelmanager.LoadNextLevel();

        player.setPlayerLocation(levelmanager.getSpawnPoint().getX(), levelmanager.getSpawnPoint().getY());
        player.setCheckpoint(levelmanager.getSpawnPoint());
        player.checkInAir(levelmanager.getCurrentLevel().getlevelData());
        player.setHP(player.getMaxHP());
        enemyManager.loadNewEnemies();
    }

    public void reStartLevel() {
        levelmanager.ReloadThisLevel();
        player.setHP(player.getMaxHP());
        player.setPlayerLocation(levelmanager.getSpawnPoint().getX(), levelmanager.getSpawnPoint().getY());
        player.checkInAir(levelmanager.getCurrentLevel().getlevelData());
        enemyManager.loadNewEnemies();
    }

    public int getxLvlOffset() {
        return xLvlOffset;
    }

    public LevelManager getLevelmanager() {
        return levelmanager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void checkCloseToBorder() {
        int playerX = player.getHitbox().x;
        int diff = playerX;
        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;                                               // Move screen
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }
        xLvlOffset = Math.max(Math.min(xLvlOffset, maxLvlOffsetX), 0);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void render(Graphics2D g) {
        g.drawImage(bgimg, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
        levelmanager.render(g);
        player.render(g);
        enemyManager.render(g);
        checkCloseToBorder();
    }

    public void startGameLoop() {
        gameThread.start();
    }

    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;                           // game drawn
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
            }
        }
    }

}
