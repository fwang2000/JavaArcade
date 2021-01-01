package Games.Hangman.Gameplay;

import Games.GameAbstractions.Game;
import Games.GameAbstractions.GameFactory;

import java.util.ArrayList;
import java.util.List;

public class HangmanFactory implements GameFactory {

    private String gameString;
    private String displayString;
    private List<String> possibleGuesses;
    private List<String> gameStringLetters;

    public HangmanFactory(String gameString) {

        this.gameString = gameString.toUpperCase().trim();
        this.setPossibleGuesses();
        this.setGameStringLetters();
        this.setDisplayString();
    }

    @Override
    public Game makeGame() {

        return new HangmanGame(gameString, displayString, possibleGuesses, gameStringLetters);
    }

    private void setPossibleGuesses() {

        this.possibleGuesses = new ArrayList<>();
        String guessList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for (int i = 0; i < guessList.length(); i++) {

            this.possibleGuesses.add(guessList.substring(i, i + 1));
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all unique letters to a List
    private void setGameStringLetters() {

        this.gameStringLetters = new ArrayList<>();
        for (int i = 0; i < gameString.length(); i++) {

            String gameStringLetter = gameString.substring(i, i + 1);

            if (!gameStringLetters.contains(gameStringLetter) && possibleGuesses.contains(gameStringLetter)) {

                gameStringLetters.add(gameStringLetter);
            }
        }
    }

    // REQUIRES: gameString.length() >= 1
    // MODIFIES: this
    // EFFECTS: creates that String that will be displayed to the player
    private void setDisplayString() {

        this.displayString = "";
        for (int i = 0; i < gameString.length(); i++) {

            if (this.possibleGuesses.contains(gameString.substring(i, i + 1))) {

                displayString += "_ ";

            } else {

                displayString += gameString.charAt(i) + " ";
            }
        }
    }
}
