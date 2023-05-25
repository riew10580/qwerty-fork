package Utility;

import static Constants.Constants.TYPE_RANGE;
import Level.Spike;
import static Constants.Constants.TYPE_SLIME;
import Entities.Enemy;
import Entities.RangedMob;
import Entities.Slime;
import Game.GamePanel;
import Level.Checkpoint;
import Level.Goal;
import Level.LevelManager;
import Level.MapPoint;
import Level.Spawn;
import java.io.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PORTRAIT = "Portrait.png";

    public static final String PLAYER_SPRITES = "player_sprites.png";
    public static final String SLIME_SPRITES = "slime_sprites.png";
    public static final String RANGE_SPRITES = "range_sprites.png";
    public static final String ARROW = "fireball.png";

//     public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String BG_IMAGE = "background3.png";

    public static final String SPIKE = "spike.png";
    public static final String CHECKPOINT = "checkpoint.png";
    public static final String GOAL = "goal.png";

    public static final String LEVEL_SPRITES = "level_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one.png";
    public static final String LEVEL_TWO_DATA = "level_two.png";
    public static final String LEVEL_THREE_DATA = "level_three.png";
    public static final String LEVEL_FOUR_DATA = "level_four.png";

    public static BufferedImage Loadimage(String file) {
        BufferedImage img = null;
        InputStream image = LoadSave.class.getResourceAsStream("imgs/" + file);           // in case error try LoadSave.class.getResourceAsStream("/" + file);
        try {
            img = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String getLevel(int currentLevelNumber) {
        switch (currentLevelNumber) {
            case 1 -> {
                return LEVEL_ONE_DATA;
            }
            case 2 -> {
                return LEVEL_TWO_DATA;
            }
            case 3 -> {
                return LEVEL_THREE_DATA;
            }
            case 4 -> {
                return LEVEL_FOUR_DATA;
            }
        }

        return "";
    }

    public static int[][] GetLevelData(int currentLevelNumber) {
        BufferedImage img = Loadimage(getLevel(currentLevelNumber));
        int levelData[][] = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j += 1) {
            for (int i = 0; i < img.getWidth(); i += 1) {
                Color color = new Color(img.getRGB(i, j));      // Color object RGB from image at x=i, y=j
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelData[j][i] = value;                        // define value for each x, y of the map
            }                                                       // this value will be use to decide if block at x,y is walkable
        }
        return levelData;                                                       //
    }

    public static ArrayList<Slime> getSlimes(LevelManager lvm) {
        BufferedImage img = Loadimage(getLevel(lvm.getCurrentLevelNumber()));
        int levelData[][] = new int[img.getHeight()][img.getWidth()];
        ArrayList<Slime> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j += 1) {
            for (int i = 0; i < img.getWidth(); i += 1) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == TYPE_SLIME) {
                    list.add(new Slime(i * GamePanel.TILES_SIZE, j * GamePanel.TILES_SIZE, lvm.getGame()));
                }
            }
        }
        return list;
    }

    public static ArrayList<MapPoint> getMappoint(int currentLevelNumber) {
        BufferedImage img = Loadimage(getLevel(currentLevelNumber));
        int levelData[][] = new int[img.getHeight()][img.getWidth()];
        ArrayList<MapPoint> list = new ArrayList<>();;
        for (int j = 0; j < img.getHeight(); j += 1) {
            for (int i = 0; i < img.getWidth(); i += 1) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                switch (value) {
                    case 255 -> {
                        list.add(new Checkpoint(i * GamePanel.TILES_SIZE, j * GamePanel.TILES_SIZE));
                    }
                    case 20 -> {
                        list.add(new Spike(i * GamePanel.TILES_SIZE, j * GamePanel.TILES_SIZE));
                    }
                }
            }
        }
        return list;
    }

    public static Spawn getSpawn(int currentLevelNumber) {
        BufferedImage img = Loadimage(getLevel(currentLevelNumber));
        int levelData[][] = new int[img.getHeight()][img.getWidth()];
        Spawn sp = new Spawn(0, 0); // prevent eror;
        for (int j = 0; j < img.getHeight(); j += 1) {
            for (int i = 0; i < img.getWidth(); i += 1) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == 200) {
                    return new Spawn(i * GamePanel.TILES_SIZE, j * GamePanel.TILES_SIZE);
                }
            }
        }
        return sp; // prevent error
    }

    public static Goal createGoal(int currentLevelNumber, LevelManager mn) {
        BufferedImage img = Loadimage(getLevel(currentLevelNumber));
        int levelData[][] = new int[img.getHeight()][img.getWidth()];
        Goal goal = new Goal(0, 0, mn);
        for (int j = 0; j < img.getHeight(); j += 1) {
            for (int i = 0; i < img.getWidth(); i += 1) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == 0) {
                    return new Goal(i * GamePanel.TILES_SIZE, j * GamePanel.TILES_SIZE, mn);
                }
            }
        }
        return goal; // prevent error
    }

    public static ArrayList<RangedMob> getRangedMob(LevelManager lvm) {
        BufferedImage img = Loadimage(getLevel(lvm.getCurrentLevelNumber()));
        int levelData[][] = new int[img.getHeight()][img.getWidth()];
        ArrayList<RangedMob> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j += 1) {
            for (int i = 0; i < img.getWidth(); i += 1) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == TYPE_RANGE) {
                    list.add(new RangedMob(i * GamePanel.TILES_SIZE, j * GamePanel.TILES_SIZE, lvm.getGame()));
                }
            }
        }
        return list;
    }
}
