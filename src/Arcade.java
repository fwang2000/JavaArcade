import game.domain.Game;
import game.domain.GameSetup;
import hangman.game.HangmanSetup;
import pacman.game.PacmanSetup;
import snake.game.SnakeSetup;

import java.util.Scanner;

public class Arcade {

    private Scanner reader;

    // EFFECTS: instantiates Scanner that will read
    //          user input for methods in Arcade.
    public Arcade() {

        this.reader = new Scanner(System.in);
    }

    // EFFECTS: Prints out game options and initializes gameChooser method
    public void start() {

        this.printGameOptions();
        GameSetup gameSetup = this.chooseGame();
        gameSetup.playUI();
    }

    // MODIFIES: this
    // EFFECTS: Calls corresponding game based on user input
    private GameSetup chooseGame() {

        System.out.println("Please type which game you'd like to play:");

        String gameChoice = reader.nextLine().toUpperCase().trim();

        GameSetup gameSetup = this.setGameChoice(gameChoice);
        return gameSetup;
    }

    // REQUIRES: String parameter
    // EFFECTS: chooses the game/website based on the input of the user
    private GameSetup setGameChoice(String choice) {

        GameSetup gameSetup;

        while (true) {
            switch (choice) {
                case "SNAKE":

                    gameSetup = new SnakeSetup();
                    return gameSetup;

                case "HANGMAN":

                    gameSetup = new HangmanSetup();
                    return gameSetup;

                case "PAC-MAN":
                case "PACMAN":

                    gameSetup = new PacmanSetup();
                    return gameSetup;

                default:

                    System.out.println("Not a valid game");
                    choice = reader.nextLine().toUpperCase().trim();
                    break;
            }
        }
    }

    private void printGameOptions() {

        System.out.println("Games:");
        System.out.println();
        System.out.println("- Snake");
        System.out.println("- Hangman");
        System.out.println("- Pac-Man");
        System.out.println();
    }
}

