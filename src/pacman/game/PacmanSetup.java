package pacman.game;

import game.domain.Game;
import game.domain.GameSetup;
import game.domain.UI;
import pacman.domain.Pacman;

import javax.swing.*;


public class PacmanSetup extends GameSetup {

    private final int width = 40;
    private final int height = 12;
    // EFFECTS: intros game and lives, sets up game, ui (application frame), drawing board, and starts the game
    @Override
    public void makeGame() {

        System.out.println("Lives: 2");
        System.out.println("Good Luck");
        this.game = new PacmanGame(width, height);
    }

    @Override
    protected UI getUI() {

        return new PacmanUI((PacmanGame) game, 20);
    }
}
