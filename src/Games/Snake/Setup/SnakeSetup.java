package Games.Snake.Setup;

import Games.GameAbstractions.GameSetup;
import Games.Snake.Gameplay.SnakeGame;
import Games.Snake.UI.SnakeUI;
import Games.GameAbstractions.UI;

public class SnakeSetup extends GameSetup {

    private final int sideLength = 20;

    // EFFECTS: sets up game, ui, drawing board, and starts the game
    @Override
    public void setGame() {

        SnakeFactory snakeFactory = new SnakeFactory(sideLength);
        this.game = snakeFactory.makeGame();
    }

    @Override
    protected UI getUI() {
        return new SnakeUI((SnakeGame) this.game, this.sideLength);
    }

}
