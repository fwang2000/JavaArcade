package Games.Pacman.Setup;


import Games.GameAbstractions.GameSetup;
import Games.GameAbstractions.UI;
import Games.Pacman.Gameplay.PacmanGame;
import Games.Pacman.UI.PacmanUI;

public class PacmanSetup extends GameSetup {

    // EFFECTS: intros game and lives, sets up game, ui (application frame), drawing board, and starts the game

    @Override
    public void setGame() {

        System.out.println("Lives: 2");
        System.out.println("Good Luck");
        PacmanGameFactory pgf = new PacmanGameFactory();
        this.game = pgf.makeGame();
    }

    @Override
    protected UI getUI() {
        System.out.println("here");
        return new PacmanUI((PacmanGame) game, 20);
    }
}
