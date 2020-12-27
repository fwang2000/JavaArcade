package pacman.game;

import game.domain.Direction;
import game.domain.Game;
import game.domain.Piece;
import game.domain.UI;
import pacman.domain.*;
import game.domain.Updatable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PacmanGame extends Game /*Timer*/ implements ActionListener {

    private Pacman pacman;
    private List<Dot> dots;
    private final List<Piece> eaten = new ArrayList<>();
    private List<Fruit> fruits;
    private final List<Ghost> ghosts;
    private final List<Wall> walls;
    private boolean continues;
    private int counter = 0;
    private int lives = 1;
    private final int width;
    private final int height;
    private Updatable updatable;
    private Scanner scanner;
    private final Timer timer;

    // EFFECTS: creates pacman for game, and lists for dots, fruits, and ghosts; sets up in game ghosts.
    public PacmanGame(int width, int height) {

        this.timer = new Timer(300, null);
        this.pacman = new Pacman(19, 11);
        this.dots = new ArrayList<Dot>();
        this.fruits = new ArrayList<Fruit>();
        this.ghosts = new ArrayList<Ghost>();
        this.walls = new ArrayList<Wall>();
        this.continues = true;
        this.width = width;
        this.height = height;

        this.setAll();
        for (Ghost ghost : this.ghosts) {

            pacman.setGhosts(ghost);
        }

        this.timer.addActionListener(this);
        this.timer.setInitialDelay(5000);
        this.timer.start();
    }

    public void setAll() {

        try {

            this.setPacman(this.pacman);
            this.setWalls();
            this.setDots();
            this.setFruits();
            this.setGhosts();
            this.pacman.setWalls();

        } catch (IOException e) {

            System.out.println("Exception Caught");
        }
    }

    public void setPacman(Pacman pacman) {

        if (this.pacman == null) {

            this.pacman = pacman;

        }
        this.pacman.setGame(this);
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

        for (Ghost ghost : ghosts) {

            ghost.setUpdatable(this.updatable);
            ghost.setGame(this);
            ghost.start();
        }
    }

    // EFFECTS: returns list of ghosts
    public List<Ghost> getGhosts() {

        return this.ghosts;
    }

    public void setWalls() throws IOException {

        String str = "";
        List<String> wallStrings = Files.readAllLines(Paths.get("data/walls.txt"));
        for (String s : wallStrings) {

            str += s + "\n";
        }
        scanner = new Scanner(str).useDelimiter("\\(|\\, |\\)\n?");
        while (scanner.hasNext()) {

            walls.add(new Wall(scanner.nextInt(), scanner.nextInt()));
            scanner.nextLine();
        }
    }

    public void setDots() throws IOException {

        this.dots = new ArrayList<>();
        String str = "";
        List<String> wallStrings = Files.readAllLines(Paths.get("data/dots.txt"));
        for (String s : wallStrings) {

            str += s + "\n";
        }
        scanner = new Scanner(str).useDelimiter("Dot\\(|\\, |\\)\n?");
        while (scanner.hasNext()) {

            dots.add(new Dot(scanner.nextInt(), scanner.nextInt()));
            scanner.nextLine();
        }
    }

    public void setFruits() throws IOException {

        this.fruits = new ArrayList<>();
        String str = "";
        List<String> wallStrings = Files.readAllLines(Paths.get("data/fruits.txt"));
        for (String s : wallStrings) {

            str += s + "\n";
        }
        scanner = new Scanner(str).useDelimiter("Fruit\\(|\\, |\\)\n?");
        while (scanner.hasNext()) {

            fruits.add(new Fruit(scanner.nextInt(), scanner.nextInt()));
            scanner.nextLine();
        }
    }

    public void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
    }

    @Override
    protected UI getUI() {
        return null;
    }

    public Pacman getPacman() {

        return this.pacman;
    }

    public int getWidth() {

        return this.width;
    }

    public int getHeight() {

        return this.height;
    }

    public List<Wall> getWalls() {

        return this.walls;
    }

    public List<Dot> getDots() {

        return this.dots;
    }

    public List<Piece> getEaten() {

        return this.eaten;
    }

    public List<Fruit> getFruits() {

        return this.fruits;
    }

    public boolean getContinues() {

        return this.continues;
    }

    // MODIFIES: this
    // EFFECTS: if there are no more lives left, set continues to false, or else lose a life
    public void continues() {

        if (lives <= 0) {

            continues = false;
        }

        lives--;
    }

    // EFFECTS: calls all functions that update on timer, updates the frame
    @Override
    public void actionPerformed(ActionEvent ae) {

        doPacmanMove();
        eatDots();
        eatFruit();
        runIntoGhost();
        checkGameEnd();

        this.updatable.update();
    }

    // MODIFIES: this
    // EFFECTS: if the pacman runs into a ghost
    //          if the ghost is blue, set it to eaten
    //          else remove a life, reset the pacman and the ghosts, and restart the game
    public void runIntoGhost() {

        for (Ghost ghost : ghosts) {

            if (pacman.hitsGhost(ghost) || checkGhostPassed(ghost)) {

                if (ghost.getBlue()) {

                    ghost.eaten();

                } else {

                    continues();
                    pacman.reset();
                    for (Ghost ghost1 : ghosts) {

                        ghost1.resetGhostPosition();
                    }
                    this.timer.setInitialDelay(3000);
                    this.timer.restart();
                }
            }
        }
    }

    // REQUIRES: Ghost parameter
    // EFFECTS: Checks to see if that ghost and pacman pass each other after being one space apart
    public boolean checkGhostPassed(Ghost ghost) {

        if (ghost.getDirection() == Direction.LEFT && pacman.getDirection() == Direction.RIGHT) {

            return (ghost.getPreviousX() == pacman.getPreviousX() + 1 && checkYEqual(ghost));

        } else if (ghost.getDirection() == Direction.RIGHT && pacman.getDirection() == Direction.LEFT) {

            return (ghost.getPreviousX() == pacman.getPreviousX() - 1 && checkYEqual(ghost));

        } else if (ghost.getDirection() == Direction.DOWN && pacman.getDirection() == Direction.UP) {

            return (ghost.getPreviousY() == pacman.getPreviousY() + 1 && checkXEqual(ghost));

        } else if (ghost.getDirection() == Direction.UP && pacman.getDirection() == Direction.DOWN) {

            return (ghost.getPreviousY() == pacman.getPreviousY() - 1 && checkXEqual(ghost));
        }

        return false;
    }

    // EFFECTS: checks to see the ghost's y and pacman's y positions are equal
    public boolean checkYEqual(Ghost ghost) {

        return ghost.getGhostY() == pacman.getY();
    }

    // EFFECTS: checks to see the ghost's x and pacman's x positions are equal
    public boolean checkXEqual(Ghost ghost) {

        return ghost.getGhostX() == pacman.getX();
    }

    // EFFECTS: if the game continues, and pacman isn't blocked by a wall, move the pacman
    public void doPacmanMove() {

        if (continues) {

            if (!pacman.pathBlockedByWall()) {

                pacman.move();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if the pacman runs into a dot, add one of the counter,
    //          and add the dot to the eaten list if it doesn't already contain it
    public void eatDots() {

        for (Dot dot : dots) {

            if (pacman.runsInto(dot) && !eaten.contains(dot)) {

                counter++;
                eaten.add(dot);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if pacman runs into the fruit and it hasn't been eaten
    //          turn ghost to blue, if it was already blue, reset the blue counter
    //          add the fruit to list of eaten if it hasn't been eaten
    public void eatFruit() {

        for (Fruit fruit : fruits) {

            if (pacman.runsInto(fruit) && !eaten.contains(fruit)) {

                for (Ghost ghost : ghosts) {

                    ghost.blue();
                    if (ghost.getCounter() > 0) {

                        ghost.resetCounter();
                    }
                }
                eaten.add(fruit);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if the counter is equal to the amount of dots
    //          if so, set continues to false
    public void checkGameEnd() {

        if (counter == dots.size()) {

            continues = false;
        }
    }
}
