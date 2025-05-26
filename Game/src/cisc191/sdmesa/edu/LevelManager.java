package cisc191.sdmesa.edu;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private final List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        levels = new ArrayList<>();
        loadLevels();
        currentLevelIndex = 0;
    }

    private void loadLevels() {
        // Level 1
        Level level1 = new Level();
        level1.getPlatforms().add(new Platform(600, 200, 150, 20));
        levels.add(level1);

        // Level 2
        Level level2 = new Level();
        level2.getPlatforms().clear();
        level2.getPlatforms().add(new Platform(50, 500, 200, 20));
        level2.getPlatforms().add(new Platform(300, 450, 200, 20));
        level2.getPlatforms().add(new Platform(550, 400, 150, 20));
        level2.getEnemies().add(new Enemy(300, 420, 40, 30, 300, 500, 2));
        levels.add(level2);

     // Level 3 - Final level with goal
        Level level3 = new Level();
        level3.getPlatforms().clear();

        Platform p1 = new Platform(100, 500, 150, 20);
        Platform p2 = new Platform(300, 400, 150, 20);
        Platform p3 = new Platform(500, 300, 150, 20);  // Last platform

        level3.getPlatforms().add(p1);
        level3.getPlatforms().add(p2);
        level3.getPlatforms().add(p3);

        level3.getEnemies().add(new Enemy(100, 470, 40, 30, 100, 250, 3));

        // Place the goal on top of the last platform (p3)
        int goalX = p3.getX() + p3.getWidth() - 40; // Near the right edge of platform 3
        int goalY = p3.getY() - 60;                  // On top of platform 3 (adjust height as needed)

        // Assuming Level has a setGoal(Goal goal) method:
        level3.setGoal(new Goal(goalX, goalY));

        levels.add(level3);

    }


    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void goToNextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
        }
    }

    public boolean hasNextLevel() {
        return currentLevelIndex < levels.size() - 1;
    }
}
