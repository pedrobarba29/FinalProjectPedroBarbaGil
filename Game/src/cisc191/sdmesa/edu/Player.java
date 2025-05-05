package cisc191.sdmesa.edu;

import java.awt.Rectangle;

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
    }

    public void update(InputHandler input) {
        // Horizontal Movement
        velocityX = 0;
        if (input.isMoveLeft()) velocityX = -SPEED;
        if (input.isMoveRight()) velocityX = SPEED;

        // Jumping
        if (input.isJump() && !isJumping) {
            velocityY = JUMP_STRENGTH;
            isJumping = true;
        }

        // Apply gravity
        velocityY += GRAVITY;
        if (velocityY > MAX_FALL_SPEED) {
            velocityY = MAX_FALL_SPEED;
        }

        // Apply movement
        x += velocityX;
        y += velocityY;

        // Keep player within screen bounds (horizontal)
        if (x < 0) x = 0;
        if (x + width > SCREEN_WIDTH) x = SCREEN_WIDTH - width;

        // Basic vertical floor limit (prevent falling below screen)
        if (y + height > SCREEN_HEIGHT) {
            y = SCREEN_HEIGHT - height;
            velocityY = 0;
            isJumping = false;
        }
    }

    public void checkCollision(Level level) {
        for (Platform platform : level.getPlatforms()) {
            if (x + width > platform.getX() && x < platform.getX() + platform.getWidth() &&
                y + height > platform.getY() && y + height < platform.getY() + platform.getHeight()) {

                // Land on top of the platform
                y = platform.getY() - height;
                velocityY = 0;
                isJumping = false;
            }
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isJumping = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
