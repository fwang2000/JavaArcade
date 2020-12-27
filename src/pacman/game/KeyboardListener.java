package pacman.game;

import game.domain.Direction;
import pacman.domain.Pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private Pacman pacman;

    public KeyboardListener(Pacman pacman) {

        this.pacman = pacman;
    }

    // REQUIRES: KeyEvent
    // EFFECTS: sets direction based on direction arrow key pressed
    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getKeyCode() == KeyEvent.VK_UP) {

            this.pacman.setDirection(Direction.UP);

        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {

            this.pacman.setDirection(Direction.DOWN);

        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {

            this.pacman.setDirection(Direction.LEFT);

        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {

            this.pacman.setDirection(Direction.RIGHT);

        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }
}
