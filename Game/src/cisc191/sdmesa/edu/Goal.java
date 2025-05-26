package cisc191.sdmesa.edu;

import java.awt.*;
import javax.swing.*;

public class Goal {
    private int x, y, width, height;
    private Image sprite;

    public Goal(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 60;
        sprite = new ImageIcon(getClass().getResource("/sprites/goal_flag.png")).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
