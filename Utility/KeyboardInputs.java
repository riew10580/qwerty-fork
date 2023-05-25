package Utility;

import Game.GamePanel;
import java.awt.event.*;

public class KeyboardInputs implements KeyListener {

    private GamePanel keyboard;
    private boolean check = true;

    public KeyboardInputs(GamePanel gamepanel) {
        this.keyboard = gamepanel;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.keyboard.getGame().getPlayer().setLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            this.keyboard.getGame().getPlayer().setRight(true);

        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            this.keyboard.getGame().getPlayer().setUp(true);

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.keyboard.getGame().getPlayer().setDown(true);

        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.keyboard.getGame().getPlayer().setJump(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            this.keyboard.getGame().getPlayer().Interact();
        }

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.keyboard.getGame().getPlayer().setLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            this.keyboard.getGame().getPlayer().setRight(false);;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            this.keyboard.getGame().getPlayer().setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_S && check) {
            this.keyboard.getGame().getPlayer().setDown(false);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.keyboard.getGame().getPlayer().setJump(false);
        }
    }
}
