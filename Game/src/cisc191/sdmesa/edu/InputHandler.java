package cisc191.sdmesa.edu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener { 
	private boolean moveLeft, moveRight, jump; 
 
public InputHandler() { 
	moveLeft = false; 
	moveRight = false; 
	jump =  false; 
} 
public boolean isMoveLeft() { return moveLeft; }
public boolean isMoveRight() { return moveRight; }
public boolean isJump() { return jump; }
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
 
} 
