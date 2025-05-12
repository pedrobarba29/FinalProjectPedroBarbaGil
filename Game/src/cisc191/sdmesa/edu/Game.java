package cisc191.sdmesa.edu;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int FPS = 60;

    private Player player;
    private LevelManager levelManager;
    private InputHandler inputHandler;
    private Thread gameThread;
    
    private GameState gameState = GameState.MENU;


    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        setFocusable(true);

        levelManager = new LevelManager();
        player = new Player(100, 100);
        inputHandler = new InputHandler();

        addKeyListener(inputHandler);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / FPS;

        while (true) {
            long now = System.nanoTime();
            if ((now - lastTime) >= nsPerTick) {
                if (gameState == GameState.PLAYING) {
                    updateGame();
                }
                repaint();
                if (gameState == GameState.MENU && inputHandler.isStartGame()) {
                    gameState = GameState.PLAYING;
                }

                lastTime = now;
            }
        }
    }


    private void updateGame() {
        player.update(inputHandler);
        player.checkCollision(levelManager.getCurrentLevel());

        // Update enemies
        for (Enemy enemy : levelManager.getCurrentLevel().getEnemies()) {
            enemy.update();

            // Collision check
            if (enemy.getBounds().intersects(player.getBounds())) {
                player.takeDamage(1); // Damage per frame of contact
            if (player.getHealth() <= 0) {
                // Reset game or show game over
                player.setPosition(100, 100);
                // Reset health or restart level
                // Example:
                // player.resetHealth(); // You'd have to implement this method
                }

            }

        }

        if (player.getX() + player.getWidth() >= WIDTH && levelManager.hasNextLevel()) {
            levelManager.goToNextLevel();
            player.setPosition(0, 100);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameState == GameState.MENU) {
        	drawMenu(g);
        } else if (gameState == GameState.PLAYING) {
        drawLevel(g);
        drawPlayer(g);
        drawEnemies(g);
        drawHealthBar(g);
        }
    }        

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : levelManager.getCurrentLevel().getEnemies()) {
            enemy.draw(g);
        }
    }


    private void drawLevel(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (Platform p : levelManager.getCurrentLevel().getPlatforms()) {
            g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
    
    private void drawHealthBar(Graphics g) {
        int maxHealth = 100;
        int barWidth = 200;
        int barHeight = 20;
        int currentHealth = player.getHealth();

        g.setColor(Color.GRAY);
        g.fillRect(20, 20, barWidth, barHeight);
        g.setColor(Color.GREEN);
        g.fillRect(20, 20, (int)((currentHealth / (double)maxHealth) * barWidth), barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(20, 20, barWidth, barHeight);
    }
    
    private void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("2D Platformer", 280, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press ENTER to Start", 290, 300);
    }

}
