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
                updateGame();
                repaint();
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
                // Reset player on enemy collision
                player.setPosition(100, 100); // Respawn point
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
        drawLevel(g);
        drawPlayer(g);
        drawEnemies(g);
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
}
