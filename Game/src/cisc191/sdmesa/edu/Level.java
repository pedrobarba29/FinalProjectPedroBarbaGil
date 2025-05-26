package cisc191.sdmesa.edu;

import java.util.List;
import java.util.ArrayList;

public class Level {
    private final List<Platform> platforms;
    private final List<Enemy> enemies;
    private Goal goal;

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }


    public Level() {
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        loadDefaultPlatforms();
    }

    private void loadDefaultPlatforms() {
        platforms.add(new Platform(100, 500, 200, 20));
        platforms.add(new Platform(400, 400, 200, 20));
        platforms.add(new Platform(200, 300, 150, 20));

        // Add an enemy on the first platform
        enemies.add(new Enemy(120, 470, 40, 30, 100, 300, 2));
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    private Platform goalPlatform;

    public Platform getGoalPlatform() {
        return goalPlatform;
    }

    public void setGoalPlatform(Platform goalPlatform) {
        this.goalPlatform = goalPlatform;
    }

}
