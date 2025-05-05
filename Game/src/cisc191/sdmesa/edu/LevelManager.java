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
        level2.getPlatforms().clear(); // Replace platforms
        level2.getPlatforms().add(new Platform(50, 500, 200, 20));
        level2.getPlatforms().add(new Platform(300, 450, 200, 20));
        level2.getPlatforms().add(new Platform(550, 400, 150, 20));
        levels.add(level2);
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
