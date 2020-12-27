package snake.game;

import game.domain.Game;
import game.domain.GameSetup;
import game.domain.UI;

import javax.swing.*;

public class SnakeSetup extends GameSetup {

    private final int sideLength = 20;

    // EFFECTS: sets up game, ui, drawing board, and starts the game
    @Override
    public void makeGame() {

        this.game = new SnakeGame(this.sideLength);
    }

    @Override
    protected UI getUI() {
        return new SnakeUI((SnakeGame) this.game, this.sideLength);
    }

}
