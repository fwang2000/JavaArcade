package snake.game;

import game.domain.Direction;
import game.domain.Game;
import game.domain.Piece;
import game.domain.UI;
import snake.domain.Apple;
import snake.domain.Snake;
import game.domain.Updatable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SnakeGame extends Game implements ActionListener {

    private int width;
    private int height;
    private boolean continues;
    private Updatable updatable;
    private Snake snake;
    private Apple apple;
    private Timer timer;

    public SnakeGame(int sideLength) {

        this.timer = new Timer(1000, null);

        this.width = sideLength;
        this.height = sideLength;
        this.continues = true;
        this.snake = new Snake(width / 2, height / 2, Direction.DOWN);

        this.setApple();

        this.timer.addActionListener(this);
        this.timer.setInitialDelay(2000);
        this.timer.start();
    }

    public boolean continues() {

        return continues;
    }

    public void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
    }

    @Override
    protected UI getUI() {
        return null;
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    // MODIFIES: this
    // EFFECTS: checks all snake functionality
    //          - if continues == true, move the snake
    //          - grow if snake eats an apple, and generate a new apple
    //          - check if the snake eats itself, and end game if so
    //          - update
    //          - speed up game/snake if it eats an apple
    //          - check if snake hits any boundaries
    @Override
    public void actionPerformed(ActionEvent ae) {

        this.checkGameEnd();
        this.eatApple();
        this.eatSelf();

        this.updatable.update();
        this.timer.setDelay(1000 / snake.getLength());

        this.checkBoundaries();
    }

    public Snake getSnake() {

        return this.snake;
    }

    public void setSnake(Snake snake) {

        this.snake = snake;
    }

    public Apple getApple() {

        return this.apple;
    }

    private void setApple() {

        Random randomizer = new Random();

        int appleX = randomizer.nextInt(width);
        int appleY = randomizer.nextInt(height);

        for (Piece piece : snake.getPieces()) {

            if (appleX == piece.getX() && appleY == piece.getY()) {

                appleX = randomizer.nextInt(width);
                appleY = randomizer.nextInt(height);
            }
        }

        this.apple = new Apple(appleX, appleY);
    }

    private void checkGameEnd() {

        if (this.continues) {

            this.snake.move();
        }
    }

    private void eatApple() {

        if (this.snake.runsInto(this.apple)) {

            this.snake.grow();
            this.setApple();
        }
    }

    private void eatSelf() {

        if (this.snake.runsIntoItself()) {

            this.continues = false;
        }
    }

    private void checkBoundaries() {

        for (Piece piece : snake.getPieces()) {

            if (piece.getX() < 0 || piece.getX() > width || piece.getY() < 0 || piece.getY() > height) {

                this.continues = false;
            }
        }
    }
}
