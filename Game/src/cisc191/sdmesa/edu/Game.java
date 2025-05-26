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

    private Image background;
    private Image menuBackground;

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        setFocusable(true);

        levelManager = new LevelManager();
        player = new Player(100, 100);
        inputHandler = new InputHandler();

        background = new ImageIcon(getClass().getResource("/backgrounds/sky.png")).getImage();
        menuBackground = new ImageIcon(getClass().getResource("/backgrounds/menu_background.png")).getImage();

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

            if (enemy.getBounds().intersects(player.getBounds())) {
                player.takeDamage(1);
                if (player.getHealth() <= 0) {
                    player.setPosition(100, 100);
                    // Optionally reset health here
                }
            }
        }

        // Check for level goal (end of level)
        Goal goal = levelManager.getCurrentLevel().getGoal();
        if (goal != null && player.getBounds().intersects(goal.getBounds())) {
            if (!levelManager.hasNextLevel()) {
                gameState = GameState.GAME_OVER;
            } else {
                levelManager.goToNextLevel();
                player.setPosition(0, 100);
            }
        }

        // Automatically go to next level if walking off screen (optional)
        if (player.getX() + player.getWidth() >= WIDTH && levelManager.hasNextLevel()) {
            levelManager.goToNextLevel();
            player.setPosition(0, 100);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.MENU) {
            g.drawImage(menuBackground, 0, 0, getWidth(), getHeight(), null);
            drawMenu(g);
        } else if (gameState == GameState.PLAYING) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            drawLevel(g);
            drawGoal(g);
            drawPlayer(g);
            drawEnemies(g);
            drawHealthBar(g);
        } else if (gameState == GameState.GAME_OVER) {
            drawWinScreen(g);
        }
    }

    private void drawLevel(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (Platform p : levelManager.getCurrentLevel().getPlatforms()) {
            g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
        }
    }

    private void drawPlayer(Graphics g) {
        player.draw(g); // uses animated sprites
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : levelManager.getCurrentLevel().getEnemies()) {
            enemy.draw(g); // uses animated sprites
        }
    }

    private void drawGoal(Graphics g) {
        Goal goal = levelManager.getCurrentLevel().getGoal();
        if (goal != null) {
            goal.draw(g);
        }
    }

    private void drawHealthBar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int barX = 20;
        int barY = 20;
        int barWidth = 200;
        int barHeight = 25;
        int currentHealth = player.getHealth();
        int maxHealth = 100;

        float healthPercent = currentHealth / (float) maxHealth;
        int healthWidth = (int) (barWidth * healthPercent);

        // Background
        g2.setColor(new Color(50, 50, 50, 180));
        g2.fillRoundRect(barX, barY, barWidth, barHeight, 15, 15);

        // Fill
        Color fillColor = healthPercent > 0.5 ? new Color(76, 175, 80) :
                          healthPercent > 0.25 ? new Color(255, 193, 7) :
                          new Color(244, 67, 54);
        g2.setColor(fillColor);
        g2.fillRoundRect(barX, barY, healthWidth, barHeight, 15, 15);

        // Border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(barX, barY, barWidth, barHeight, 15, 15);

        // Label
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString("HP: " + currentHealth + " / " + maxHealth, barX + 10, barY + 18);
    }

    private void drawMenu(Graphics g) {
        String title = "2D Platformer";
        String prompt = "Press ENTER to Start";

        Font titleFont = new Font("Arial", Font.BOLD, 64);
        Font promptFont = new Font("Arial", Font.BOLD, 32);

        FontMetrics titleMetrics = g.getFontMetrics(titleFont);
        FontMetrics promptMetrics = g.getFontMetrics(promptFont);

        int titleX = (getWidth() - titleMetrics.stringWidth(title)) / 2;
        int titleY = 180;

        int promptX = (getWidth() - promptMetrics.stringWidth(prompt)) / 2;
        int promptY = 300;

        g.setFont(titleFont);
        g.setColor(Color.BLACK);
        g.drawString(title, titleX + 3, titleY + 3);
        g.setColor(new Color(255, 215, 0)); // Gold
        g.drawString(title, titleX, titleY);

        g.setFont(promptFont);
        g.setColor(Color.BLACK);
        g.drawString(prompt, promptX + 2, promptY + 2);
        g.setColor(Color.WHITE);
        g.drawString(prompt, promptX, promptY);
    }

    private Image winScreenImage = new ImageIcon(getClass().getResource("/backgrounds/win_screen.png")).getImage();

    private void drawWinScreen(Graphics g) {
        // Draw win screen background
        g.drawImage(winScreenImage, 0, 0, getWidth(), getHeight(), null);

        // Draw "You Win" text
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        String title = "🏆 YOU WIN! 🏆";
        String subtext = "Thanks for playing!";
        
        Font titleFont = new Font("Serif", Font.BOLD, 64);
        Font subFont = new Font("SansSerif", Font.PLAIN, 28);

        // Shadow effect
        g2.setFont(titleFont);
        int titleX = (getWidth() - g2.getFontMetrics().stringWidth(title)) / 2;
        int titleY = 250;

        g2.setColor(Color.BLACK);
        g2.drawString(title, titleX + 3, titleY + 3); // shadow
        g2.setColor(new Color(255, 215, 0)); // gold
        g2.drawString(title, titleX, titleY);

        // Subtext
        g2.setFont(subFont);
        int subX = (getWidth() - g2.getFontMetrics().stringWidth(subtext)) / 2;
        int subY = titleY + 60;

        g2.setColor(Color.WHITE);
        g2.drawString(subtext, subX, subY);
    }

}
