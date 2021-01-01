package Games.Snake.Gameplay;

import Games.GameAbstractions.Game;
import Games.CharacterAbstractions.Piece;
import Games.GameAbstractions.TimerGame;
import Games.Snake.Domain.Apple;
import Games.Snake.Domain.Snake;
import Games.GameAbstractions.Updatable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SnakeGame extends TimerGame {

    private int width;
    private int height;
    private boolean continues = true;
    private Updatable updatable;
    private Snake snake;
    private Apple apple;

    public SnakeGame(int sideLength, Snake snake) {

        this.width = sideLength;
        this.height = sideLength;
        this.snake = snake;

        this.setApple();
        this.start(1000, 2000);
    }

    public boolean continues() {

        return continues;
    }

    public void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
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
