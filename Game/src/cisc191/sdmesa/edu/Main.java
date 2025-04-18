package cisc191.sdmesa.edu;

import javax.swing.JFrame;

public class Main {	
	public static void main(String[] args) {
	    try {
	        JFrame frame = new JFrame("Platformer Game");
	        Game game = new Game();

	        frame.add(game);
	        frame.setSize(800, 600);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	    } catch (Exception e) {
	        System.err.println("Failed to launch the game: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}