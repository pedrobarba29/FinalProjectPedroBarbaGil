package cisc191.sdmesa.edu;

import java.util.ArrayList;
import java.util.List;

public class Level { 
	private List<Platform> platforms; 
 
public Level() { 
	platforms = new ArrayList<>(); 
	
	platforms.add(new Platform(100, 500, 200, 20));
    platforms.add(new Platform(400, 400, 200, 20));
    platforms.add(new Platform(200, 300, 150, 20));
} 
 
} 
