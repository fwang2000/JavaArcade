package Games.Hangman.Setup;

import Exceptions.InvalidNumberException;

import java.io.IOException;
import java.util.List;

public class SinglePlayerMode extends HangmanGameMode {


    @Override
    protected String selectString() {

        this.printCategories();
        List<String> category = this.categorySelect();

        return this.singlePlayerStringChoice(category);
    }

    // EFFECTS: prints out category choices and prompts user category selection
    private void printCategories() {

        System.out.println("Pick a category:");
        System.out.println();
        System.out.println("1. Countries");
        System.out.println("2. Animals");
        System.out.println("3. Movies");
        System.out.println("4. Common Phrases");
        System.out.println();

        System.out.println("Type the number of the category you want:");
    }

    // MODIFIES: this, reader
    // EFFECTS: returns the category selected by user
    private List<String> categorySelect() {

        List<String> category;

        while (true) {

            try {

                int categoryChoice = reader.nextInt();
                category = selector.setCategorySelection(categoryChoice);
                reader.nextLine();
                break;

            } catch (IOException e) {

                System.out.println("IO Error");

            } catch (InvalidNumberException e) {

                System.out.println("Not A Valid Number");
                reader.nextLine();

            }
        }
        return category;
    }

    // REQUIRES: parameter must be a List<String> object
    // EFFECTS: randomly selects a string from chosen category and initiates gameplay
    private String singlePlayerStringChoice(List<String> category) {

        return category.get(randomizer.nextInt(category.size()));
    }
}
