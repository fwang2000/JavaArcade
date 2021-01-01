package Games.Snake.Setup;

import Games.CharacterAbstractions.Direction;
import Games.GameAbstractions.Game;
import Games.GameAbstractions.GameFactory;
import Games.Snake.Domain.Snake;
import Games.Snake.Gameplay.SnakeGame;

import javax.swing.Timer;

public class SnakeFactory implements GameFactory {

    private int sideLength;
    private Snake snake;

    public SnakeFactory(int sidelength){

        this.sideLength = sidelength;
        this.setSnake();
    }

    @Override
    public Game makeGame() {

        return new SnakeGame(sideLength, snake);
    }

    private void setSnake() {

        this.snake = new Snake(sideLength/2, sideLength/2, Direction.DOWN);
    }
}
