import Games.GameAbstractions.GameSetup;
import Games.Hangman.Setup.HangmanSetup;
import Games.Pacman.Setup.PacmanSetup;
import Games.Snake.Setup.SnakeSetup;

import java.util.Scanner;

public class Arcade {

    private Scanner scanner;

    public Arcade() {

        this.scanner = new Scanner(System.in);
    }

    public void start() {

        this.printGameOptions();
        GameSetup gameSetup = this.chooseGame();
        gameSetup.playUI();
    }

    private GameSetup chooseGame() {

        System.out.println("Please type which game you'd like to play:");
        String gameChoice = scanner.nextLine().toUpperCase().trim();
        return this.setGameChoice(gameChoice);
    }
    // REQUIRES: String parameter
    // EFFECTS: chooses the game/website based on the input of the user
    private GameSetup setGameChoice(String choice) {

        while (true) {
            switch (choice) {
                case "SNAKE":

                    return new SnakeSetup();

                case "HANGMAN":

                    return new HangmanSetup();

                case "PAC-MAN":
                case "PACMAN":

                    return new PacmanSetup();

                default:

                    System.out.println("Not a valid game");
                    choice = scanner.nextLine().toUpperCase().trim();
                    break;
            }
        }
    }

    private void printGameOptions() {

        System.out.println("Games:");
        System.out.println();
        System.out.println("- Snake");
        System.out.println("- Hangman");
        System.out.println("- Pacman");
        System.out.println();
    }
}
