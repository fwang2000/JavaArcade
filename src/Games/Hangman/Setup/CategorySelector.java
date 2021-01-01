package Games.Hangman.Setup;

import Exceptions.InvalidNumberException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CategorySelector {

    // EFFECTS: sets the category from pre-made categories based on the integer user selects
    //          if the integer is not between 1 and 4, throw an invalid number exception
    public List<String> setCategorySelection(int categorySelection) throws IOException, InvalidNumberException {

        List<String> category;

        if (categorySelection == 1) {

            category = this.getCountries();

        } else if (categorySelection == 2) {

            category = this.getAnimals();

        } else if (categorySelection == 3) {

            category = this.getMovies();

        } else if (categorySelection == 4) {

            category = this.getCommonPhrases();

        } else {

            throw new InvalidNumberException();
        }

        return category;
    }

    private List<String> getCountries() throws IOException {

        List<String> countries = Files.readAllLines(Paths.get("data/countryfile.txt"));
        return countries;
    }

    // EFFECTS: returns array of animals.txt
    private List<String> getAnimals() throws IOException {

        List<String> animals = Files.readAllLines(Paths.get("data/animalfile.txt"));
        return animals;
    }

    // EFFECTS: returns array of movies.txt
    private List<String> getMovies() throws IOException {

        List<String> movies = Files.readAllLines(Paths.get("data/moviefile.txt"));
        return movies;
    }

    // EFFECTS: returns array of common phrases
    private List<String> getCommonPhrases() throws IOException {

        List<String> commonPhrases = Files.readAllLines(Paths.get("data/commonphrasefile.txt"));
        return commonPhrases;
    }
}
