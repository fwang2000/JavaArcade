package Games.Snake.UI;

import Exceptions.IllegalDirectionChangeException;
import Games.CharacterAbstractions.Direction;
import Games.Snake.Domain.Snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private Snake snake;

    public KeyboardListener(Snake snake) {

        this.snake = snake;
    }

    // REQUIRES: KeyEvent
    // EFFECTS: sets direction based on direction arrow key pressed,
    //          catches exception if snake tries to go in opposite direction
    @Override
    public void keyPressed(KeyEvent ke) {

        try {

            if (ke.getKeyCode() == KeyEvent.VK_UP) {

                this.snake.setDirection(Direction.UP);

            } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {

                this.snake.setDirection(Direction.DOWN);

            } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {

                this.snake.setDirection(Direction.LEFT);

            } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {

                this.snake.setDirection(Direction.RIGHT);

            }

        } catch (IllegalDirectionChangeException e) {

            System.out.println("Can't Change To That Direction");
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }
}
