package cisc191.sdmesa.edu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private boolean moveLeft, moveRight, jump;

    public boolean isMoveLeft() { return moveLeft; }
    public boolean isMoveRight() { return moveRight; }
    public boolean isJump() { return jump; }

    private boolean startGame;

    public boolean isStartGame() {
        return startGame;
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> moveLeft = true;
            case KeyEvent.VK_RIGHT -> moveRight = true;
            case KeyEvent.VK_SPACE -> jump = true;
            case KeyEvent.VK_ENTER -> startGame = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> moveLeft = false;
            case KeyEvent.VK_RIGHT -> moveRight = false;
            case KeyEvent.VK_SPACE -> jump = false;
            case KeyEvent.VK_ENTER -> startGame = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
    

}
