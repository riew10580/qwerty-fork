/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Entities.Player;

/**
 *
 * @author user
 */

// +
public interface Touchable {
    
    public boolean isGettingTouchBy(Player p);
    public void activateTouchedBehavior(Player p);
}
