package Game;

import Utility.MouseInputs;
import javax.swing.JFrame;

public class GameWindow{
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.add(gamePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addMouseMotionListener(new MouseInputs(gamePanel));
        jframe.addMouseListener(new MouseInputs(gamePanel));
        jframe.pack();
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }    
}
