package hangman.game;

public class DoublePlayerMode extends HangmanGameMode {


    @Override
    protected String selectString() {

        System.out.println("Player 1, please type in what you would like Player 2 to guess:");
        String gameString = reader.nextLine();
        this.addSpace();

        return gameString;
    }

    private void addSpace() {

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
