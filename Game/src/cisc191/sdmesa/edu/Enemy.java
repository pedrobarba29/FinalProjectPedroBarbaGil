package cisc191.sdmesa.edu;

import java.awt.*;

public class Enemy {
    private int x, y, width, height;
    private int speed;
    private int direction = 1; // 1 = right, -1 = left
    private int leftBound, rightBound;

    public Enemy(int x, int y, int width, int height, int leftBound, int rightBound, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.speed = speed;
    }

    public void update() {
        x += direction * speed;

        // Change direction at bounds
        if (x <= leftBound || x + width >= rightBound) {
            direction *= -1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
