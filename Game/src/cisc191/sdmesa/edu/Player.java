package cisc191.sdmesa.edu;

import java.awt.*;
import javax.swing.*;

public class Player {
    private int x, y, width, height;
    private int velocityX, velocityY;
    private int health, score;
    private boolean isJumping;

    private final int SPEED = 5;
    private final int JUMP_STRENGTH = -15;
    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;

    private Image[] walkSprites;
    private int frameIndex = 0;
    private int frameDelay = 0;
    private final int FRAME_DELAY_MAX = 10;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 60;
        this.velocityX = 0;
        this.velocityY = 0;
        this.health = 100;
        this.score = 0;
        this.isJumping = false;

        walkSprites = new Image[] {
            loadImage("/sprites/player_walk1.png"),
            loadImage("/sprites/player_walk2.png")
        };
    }

    private Image loadImage(String path) {
        Image img = new ImageIcon(getClass().getResource(path)).getImage();
        if (img == null) {
            System.err.println("Failed to load image: " + path);
        }
        return img;
    }

    public void update(InputHandler input) {
        velocityX = 0;
        if (input.isMoveLeft()) velocityX = -SPEED;
        if (input.isMoveRight()) velocityX = SPEED;

        if (input.isJump() && !isJumping) {
            velocityY = JUMP_STRENGTH;
            isJumping = true;
        }

        velocityY += GRAVITY;
        if (velocityY > MAX_FALL_SPEED) velocityY = MAX_FALL_SPEED;

        x += velocityX;
        y += velocityY;

        if (x < 0) x = 0;
        if (x + width > SCREEN_WIDTH) x = SCREEN_WIDTH - width;
        if (y + height > SCREEN_HEIGHT) {
            y = SCREEN_HEIGHT - height;
            velocityY = 0;
            isJumping = false;
        }

        // Animate only if moving
        if (velocityX != 0) {
            frameDelay++;
            if (frameDelay >= FRAME_DELAY_MAX) {
                frameDelay = 0;
                frameIndex = (frameIndex + 1) % walkSprites.length;
            }
        } else {
            frameIndex = 0;
            frameDelay = 0;
        }
    }

    public void checkCollision(Level level) {
        for (Platform platform : level.getPlatforms()) {
            if (x + width > platform.getX() && x < platform.getX() + platform.getWidth() &&
                y + height > platform.getY() && y + height < platform.getY() + platform.getHeight()) {
                y = platform.getY() - height;
                velocityY = 0;
                isJumping = false;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(walkSprites[frameIndex], x, y, width, height, null);
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isJumping = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getHealth() { return health; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
