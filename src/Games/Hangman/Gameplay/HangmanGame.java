package Games.Hangman.Gameplay;

import Games.Hangman.UI.HangmanUI;
import Games.GameAbstractions.Game;
import Games.GameAbstractions.Updatable;

import javax.swing.*;
import java.util.List;

public class HangmanGame extends Game {

    private String gameString;
    private List<String> possibleGuesses;
    private List<String> gameStringLetters;
    private String displayString;
    private String guess;
    private int moves = 6;
    private Updatable updatable;
    private String result;
    private HangmanUI ui;
    private List<JButton> buttons;

    // REQUIRES: parameter must be a String
    // EFFECTS: defines the gameString, implements the Scanner, and creates a new Hangman object
    public HangmanGame(String gameString, String displayString, List<String> possibleGuesses, List<String> gameStringLetters) {

        this.gameString = gameString;
        this.displayString = displayString;
        this.possibleGuesses = possibleGuesses;
        this.gameStringLetters = gameStringLetters;
    }

    // MODIFIES: this
    // EFFECTS: checks to see if the game is over
    //          - if there are no letters left to guess, set result to "WON"
    //          - if there are no more moves, set result to "LOST"
    //          - turn all buttons off
    //          - or else continue the game
    public boolean checkGameEnd() {

        if (this.getGameStringLettersSize() == 0 || getMoves() == 0) {

            for (JButton button : buttons) {

                button.setEnabled(false);
            }

            if (getMoves() == 0) {

                this.result = "LOST";

            } else {

                this.result = "WON";
            }

            return true;
        }
        return false;
    }

    // REQUIRES: parameter 1 must be a String
    // MODIFIES: this
    // EFFECTS: checks to see if the guess was in the gameString.
    //          If true, it will update the display. Else, it will say it isn't in the string.
    //          Removes the guess if it is in possibleGuesses
    public void guess(String letterGuess) {

        this.guess = letterGuess;

        if (possibleGuesses.contains(guess)) {

            if (gameString.contains(guess)) {

                this.gameStringLetters.remove(guess);
                this.updateDisplayString();

            } else {

                moves--;
            }

            possibleGuesses.remove(guess);

        }
    }

    // REQUIRES: gameString.length() >= 1
    // MODIFIES: this
    // EFFECTS: displayString replaces the _ with the guessed letter
    public void updateDisplayString() {

        for (int i = 0; i < gameString.length(); i++) {

            if (gameString.substring(i, i + 1).equals(this.guess)) {

                if (i == gameString.length() - 1) {

                    displayString = displayString.substring(0, displayString.length() - 2) + guess + " ";

                } else if (i == 0) {

                    displayString = guess + " " + displayString.substring(2);

                } else {

                    displayString = displayString.substring(0, (2 * i)) + guess + " "
                            + displayString.substring((2 * i) + 2);
                }

            }
        }
    }

    public void setUI(HangmanUI ui) {

        this.ui = ui;
        this.buttons = ui.getButtons();
    }

    // EFFECTS: returns size of gameStringLetters
    public int getGameStringLettersSize() {

        return this.gameStringLetters.size();
    }

    public int getMoves() {

        return this.moves;
    }

    public String getDisplayString() {

        return this.displayString;
    }

    public String getResult() {

        return this.result;
    }

    public String getGameString() {

        return this.gameString;
    }

    public List<String> getPossibleGuesses() {

        return this.possibleGuesses;
    }
}
