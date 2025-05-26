package cisc191.sdmesa.edu;

import java.awt.*;
import javax.swing.*;

public class Enemy {
    private int x, y, width, height;
    private int speed;
    private int direction = 1;
    private int leftBound, rightBound;

    private Image[] walkSprites;
    private int frameIndex = 0;
    private int frameDelay = 0;
    private final int FRAME_DELAY_MAX = 12;

    public Enemy(int x, int y, int width, int height, int leftBound, int rightBound, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.speed = speed;

        walkSprites = new Image[] {
            loadImage("/sprites/enemy_walk1.png"),
            loadImage("/sprites/enemy_walk2.png")
        };
    }

    private Image loadImage(String path) {
        Image img = new ImageIcon(getClass().getResource(path)).getImage();
        if (img == null) {
            System.err.println("Failed to load image: " + path);
        }
        return img;
    }

    public void update() {
        x += direction * speed;

        // Animate walk cycle
        frameDelay++;
        if (frameDelay >= FRAME_DELAY_MAX) {
            frameDelay = 0;
            frameIndex = (frameIndex + 1) % walkSprites.length;
        }

        // Bounce at bounds
        if (x <= leftBound || x + width >= rightBound) {
            direction *= -1;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(walkSprites[frameIndex], x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
