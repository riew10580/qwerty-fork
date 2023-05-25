package Utility;

import Game.GamePanel;
import java.awt.event.*;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel mouse;

    public MouseInputs(GamePanel gamepanel) {
        this.mouse = gamepanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.mouse.getGame().getPlayer().doesAttack();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1) {
//            System.out.println("yyy");
//            this.mouse.getGame().getPlayer().AttackEnd();
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("dragged");
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
