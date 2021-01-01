package Games.Hangman.Setup;

import java.util.Random;
import java.util.Scanner;

public abstract class HangmanGameMode {

    protected Random randomizer;
    protected Scanner reader;
    protected CategorySelector selector;

    public HangmanGameMode() {

        this.randomizer = new Random();
        this.reader = new Scanner(System.in);
        this.selector = new CategorySelector();
    }

    protected abstract String selectString();
}
