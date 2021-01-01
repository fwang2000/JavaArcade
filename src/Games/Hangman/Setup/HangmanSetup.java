package Games.Hangman.Setup;

import Games.GameAbstractions.GameSetup;
import Games.Hangman.Gameplay.HangmanFactory;
import Games.Hangman.Gameplay.HangmanGame;
import Games.Hangman.UI.HangmanUI;
import Games.GameAbstractions.UI;

import java.util.Scanner;

public class HangmanSetup extends GameSetup {

    private Scanner reader;

    public HangmanSetup() {

        this.reader = new Scanner(System.in);
    }

    // MODIFIES: Scanner reader object
    // EFFECTS: determines number of players and which corresponding mode/method to call
    @Override
    public void setGame() {

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
        HangmanFactory hmf = new HangmanFactory(gameString);
        this.game = hmf.makeGame();
    }

    @Override
    protected UI getUI() {
        return new HangmanUI((HangmanGame) this.game);
    }

}
