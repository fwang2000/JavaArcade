package Games.Pacman.Setup;

import Games.GameAbstractions.Game;
import Games.GameAbstractions.GameFactory;
import Games.Pacman.Domain.*;
import Games.Pacman.Gameplay.PacmanGame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PacmanGameFactory implements GameFactory {

    private Pacman pacman;
    private List<Ghost> ghosts;

    public PacmanGameFactory() {

        this.pacman = new Pacman(19, 11);
        this.setGhosts();
    }

    public void setGhosts() {

        Ghost blinky = new Ghost(17, 6, "Blinky");
        Ghost inky = new Ghost(18, 6, "Inky");
        Ghost pinky = new Ghost(20, 6, "Pinky");
        Ghost clyde = new Ghost(21, 6, "Clyde");

        this.ghosts.add(blinky);
        this.ghosts.add(inky);
        this.ghosts.add(pinky);
        this.ghosts.add(clyde);
    }

    @Override
    public Game makeGame() {

        return new PacmanGame(pacman, ghosts);
    }
}
