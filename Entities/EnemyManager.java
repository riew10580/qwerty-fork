package Entities;

import Token.PlayerAttack;
import static Constants.Constants.*;
import Game.Game;
import Level.LevelManager;
import static Utility.HelpMethod.CanMove;
import Utility.LoadSave;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {

    private Game game;
    private ArrayList<Slime> slimes;
    private ArrayList<RangedMob> ranges;

    public EnemyManager(Game game) {
        this.game = game;
        addEnemies();
    }

    public void loadNewEnemies() {
        this.slimes.clear();
        this.ranges.clear();
        addEnemies();
    }

    public void render(Graphics2D g) {
        update();
        drawEnemies(g, game.getxLvlOffset());

    }

    public void update() {
        updateSlime();
        updateRangedMob();
    }

    public void updateSlime() {
        for (Slime s : slimes) {
            if (s.isActive()) {
                if (s.isOnScreen()) {
                    s.update();

                }
            } else {
                slimes.remove(slimes.indexOf(s));
                break; // prevent some sort of error 
            }

        }
    }

    public void updateRangedMob() {
        for (RangedMob r : ranges) {
            if (r.isActive()) {
                if (r.isOnScreen()) {
                    r.update();

                }
            } else {
                ranges.remove(ranges.indexOf(r));
                break; // prevent some sort of error 
            }

        }
    }

    public Game getGame() {
        return game;
    }

    private void drawEnemies(Graphics2D g, int xLvlOffset) {
        drawSlime(g);
        drawRanges(g);
    }

    private void drawSlime(Graphics2D g) {
        for (Slime s : slimes) {
            if (s.isOnScreen()) {
                s.setOpacity(g);
                if (s.facing == -1) {
                    g.drawImage(s.getSlimeSprite()[s.getEnemyActions()][s.getAnimationIndex()], s.getX() - game.getxLvlOffset(), s.getY() - 32, 128, 128, null);
                } else {
                    g.drawImage(s.getSlimeSprite()[s.getEnemyActions()][s.getAnimationIndex()], s.getX() - game.getxLvlOffset() + 128, s.getY() - 32, 128 * -1, 128, null);
                }
            }
        }
    }

    private void drawRanges(Graphics2D g) {
        for (RangedMob r : ranges) {
            if (r.isOnScreen()) {
                r.setOpacity(g);
                if (r.facing == -1) {
                    g.drawImage(r.getRangeSprite()[r.getEnemyActions()][r.getAnimationIndex()], r.getX() - game.getxLvlOffset(), r.getY(), 64, 64, null);
                } else {
                    g.drawImage(r.getRangeSprite()[r.getEnemyActions()][r.getAnimationIndex()], r.getX() - game.getxLvlOffset() + 64, r.getY(), 64 * -1, 64, null);
                }
                if (r.getArrow() != null) {
                    g.drawImage(r.getArrow().getArrowImg()[r.getArrow().getAnimationIndex()], r.getArrow().getX(), r.getArrow().getY(), 64 * r.getArrow().getDirection() * -1, 64, null);
                }
            }
        }
    }

    private void addEnemies() {
        slimes = LoadSave.getSlimes(game.getLevelmanager());
        ranges = LoadSave.getRangedMob(game.getLevelmanager());
    }

    public ArrayList<Slime> getSlimes() {
        return slimes;
    }

    public ArrayList<RangedMob> getRanges() {
        return ranges;
    }

}
