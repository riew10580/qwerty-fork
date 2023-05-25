/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Token;

import Entities.Player;
import java.awt.Rectangle;

public class PlayerAttack {

    private Rectangle hitbox;
    private int damage;
    private Player owner;

    public PlayerAttack(Player p) {
        this.damage = p.getAttackDamage();
        this.owner = p;
        initHitbox(p);
    }

    public Player getOwner() {
        return owner;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    private void initHitbox(Player p) {
        if (p.getFacing() == 1) {
            this.hitbox = new Rectangle(p.getHitbox().x + p.getHitbox().width, p.getHitbox().y, 50, 50);
        } else {
            this.hitbox = new Rectangle(p.getHitbox().x - 36, p.getHitbox().y, 50, 50);
//            System.out.println("xxx");
        }
    }

}
