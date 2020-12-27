package hangman.game;

import game.domain.Game;
import game.domain.GameSetup;
import game.domain.UI;

import javax.swing.*;
import java.util.Scanner;

public class HangmanSetup extends GameSetup {

    private Scanner reader;

    public HangmanSetup() {

        this.reader = new Scanner(System.in);
    }

    // MODIFIES: Scanner reader object
    // EFFECTS: determines number of players and which corresponding mode/method to call
    @Override
    public void makeGame() {

        System.out.println("1 or 2 players (Enter Number:)");

        HangmanGameMode hgm;

        while (true) {

            int playerNum = reader.nextInt();
            reader.nextLine();

            if (playerNum == 1) {

                hgm = new SinglePlayerMode();
                break;

            } else if (playerNum == 2) {

                hgm = new DoublePlayerMode();
                break;

            } else {

                System.out.println("1 or 2 players only!");
            }
        }

        String gameString = hgm.selectString();
        this.game = new HangmanGame(gameString.toUpperCase().trim());
    }

    @Override
    protected UI getUI() {
        return new HangmanUI((HangmanGame) this.game);
    }

    // REQUIRES: parameter must be a string
    // EFFECTS: a Hangman game is instantiated and started
    private void play(String gameString) {

        HangmanGame hangmanGame = new HangmanGame(gameString.toUpperCase().trim());

        HangmanUI ui = new HangmanUI(hangmanGame);
        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                System.out.println("The drawing board hasn't been created yet");
            }
        }

        hangmanGame.setUpdatable(ui.getUpdatable());
        hangmanGame.play();
    }
}
