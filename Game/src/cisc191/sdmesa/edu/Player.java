package cisc191.sdmesa.edu; 
 
public class Player { 
	private int x, y, width, height; 
	private int velocityX, velocityY; 
	private int health, score; 
	private boolean isJumping; 
 
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
 
} 
