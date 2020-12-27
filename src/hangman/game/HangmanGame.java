package hangman.game;

import game.domain.UI;
import game.domain.Updatable;
import game.domain.Game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HangmanGame extends Game {

    private String gameString;
    private Map<String, List<String>> guesses;
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
    public HangmanGame(String gameString) {

        this.gameString = gameString;
        this.guesses = new HashMap<>();
        this.guesses.put(gameString, new ArrayList<>());
        this.gameString = gameString;
        this.possibleGuesses = new ArrayList<String>();
        this.gameStringLetters = new ArrayList<String>();

        this.setPossibleGuesses();
        this.setGameStringLetters();
        this.setDisplayString();
    }

    @Override
    protected UI getUI() {
        return new HangmanUI(this);
    }

    // MODIFIES: this
    // EFFECTS: checks to see if the game is over
    //          - if there are no letters left to guess, set result to "WON"
    //          - if there are no more moves, set result to "LOST"
    //          - turn all buttons off
    //          - or else continue the game
    public boolean checkGameEnd() {

        if (this.getGameStringLettersSize() == 0) {

            result = "WON";
            for (JButton button : buttons) {

                button.setEnabled(false);
            }
            return true;
        }

        if (getMoves() == 0) {

            result = "LOST";
            for (JButton button : buttons) {

                button.setEnabled(false);
            }
            return true;

        }
        return false;
    }

    public void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
    }

    private void setPossibleGuesses() {

        String guessList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for (int i = 0; i < guessList.length(); i++) {

            this.possibleGuesses.add(guessList.substring(i, i + 1));
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all unique letters to a List
    private void setGameStringLetters() {

        for (int i = 0; i < gameString.length(); i++) {

            String gameStringLetter = gameString.substring(i, i + 1);

            if (!gameStringLetters.contains(gameStringLetter) && possibleGuesses.contains(gameStringLetter)) {

                gameStringLetters.add(gameStringLetter);
            }
        }
    }

    // EFFECTS: returns size of gameStringLetters
    public int getGameStringLettersSize() {

        return this.gameStringLetters.size();
    }

    // REQUIRES: gameString.length() >= 1
    // MODIFIES: this
    // EFFECTS: creates that String that will be displayed to the player
    private void setDisplayString() {

        for (int i = 0; i < gameString.length(); i++) {

            if (this.possibleGuesses.contains(gameString.substring(i, i + 1))) {

                displayString += "_ ";
            } else {

                displayString += gameString.charAt(i) + " ";
            }
        }
        displayString = displayString.substring(4);
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

    // EFFECTS: returns possibleGuesses
    public List<String> getPossibleGuesses() {

        return this.possibleGuesses;
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

    public int getMoves() {

        return this.moves;
    }

    public String getDisplayString() {

        return this.displayString;
    }

    public void setUI(HangmanUI ui) {

        this.ui = ui;
        this.buttons = ui.getButtons();
    }

    public Updatable getUpdatable() {

        return this.updatable;
    }

    public String getResult() {

        return this.result;
    }

    public String getGameString() {

        return this.gameString;
    }
}
