/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Entities.Player;
import Utility.LoadSave;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// +
public class Status {

    private Player player;
    private BufferedImage portrait;

    public Status(Player p) {
        this.player = p;
        this.portrait = LoadSave.Loadimage(LoadSave.PORTRAIT);
    }

    public void render(Graphics2D g) {
        drawHPBar(g);
        g.drawImage(portrait, 10, 10, null);
        
        g.setColor(Color.black);
        g.setFont(new Font("ZF #2ndPixelus", Font.PLAIN, 50));
        g.drawString(""+ player.getHP() + " /100", 670, 45);
    }

    private void drawHPBar(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(148, 30, 504, 14);
        
        double greenlength = ((player.getHP())/100.0)*500;
        g.setColor(Color.green);
        g.fillRect(150, 32,(int) greenlength, 10);
        
        g.setColor(Color.red);
        g.fillRect(150+ (int) greenlength, 32, (int) (500 - greenlength), 10);
        
    }
}
