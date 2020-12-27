package pacman.domain;

import game.domain.Direction;
import game.domain.Movable;
import pacman.game.PacmanGame;
import game.domain.Updatable;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Ghost extends Timer implements Movable, ActionListener {

    private String name;
    private int ghostX;
    private int ghostY;
    private Pacman pacman;
    private Random randomizer;
    private Direction direction;
    private static final List<Direction> VALUES =
            Collections.unmodifiableList(Arrays.asList(Direction.values()));
    private static final int SIZE = VALUES.size();
    private boolean blue = false;
    private boolean eaten = false;
    private Updatable updatable;
    private List<Wall> walls;
    private PacmanGame game;
    private int originalX;
    private int originalY;
    private int previousX;
    private int previousY;
    private int counter = 0;

    public Ghost(int x, int y, String name) {

        super(300, null);
        this.ghostX = x;
        this.ghostY = y;
        this.originalX = x;
        this.originalY = y;
        this.name = name;
        this.randomizer = new Random();
        setDirection();
        walls = new ArrayList<>();

        addActionListener(this);
        setBeginDelay();
    }

    // EFFECTS: sets the amount of time the ghost stays in their box at
    //          the start of the game before moving based on their name
    public void setBeginDelay() {

        if (this.name.equals("Blinky")) {

            setInitialDelay(8000);

        } else if (this.name.equals("Inky")) {

            setInitialDelay(10000);

        } else if (this.name.equals("Pinky")) {

            setInitialDelay(12000);

        } else {

            setInitialDelay(14000);

        }
    }

    public String getName() {

        return this.name;
    }

    public int getGhostX() {

        return this.ghostX;
    }

    public int getGhostY() {

        return this.ghostY;
    }

    public int getPreviousX() {

        return this.previousX;
    }

    public int getPreviousY() {

        return this.previousY;
    }

    public Direction getDirection() {

        return this.direction;
    }

    // MODIFIES: this
    // EFFECTS: calls two other methods; changes the ghost's position based on their direction and current position
    public void move() {

        setPreviousPosition();
        count();

        if (this.direction == Direction.UP) {

            ghostY--;

        } else if (this.direction == Direction.DOWN) {

            ghostY++;

        } else if (this.direction == Direction.LEFT) {

            ghostX--;

        } else {

            ghostX++;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the ghost is blue, add one to the counter;
    //          if the counter is greater than 30:
    //              - ghost resets to normal state
    //              - counter resets;
    //              - set delay between movement back to 300 milliseconds
    public void count() {

        if (blue) {

            counter++;
        }
        if (counter >= 30) {

            blue = false;
            resetCounter();
            setDelay(300);
        }
    }

    public int getCounter() {

        return counter;
    }

    // MODIFIES: this
    // EFFECTS: sets counter to 0;
    public void resetCounter() {

        counter = 0;
    }

    // MODIFIES: this
    // EFFECTS: updates the previous position of the ghost;
    //          move() is called afterwards
    public void setPreviousPosition() {

        if (null != getDirection()) {

            switch (getDirection()) {

                case LEFT:

                case RIGHT:
                    previousX = ghostX;
                    break;

                default:
                    previousY = ghostY;
            }
        }
    }

    public void setGame(PacmanGame game) {

        this.game = game;
        setWalls();
    }

    public void setWalls() {

        this.walls = game.getWalls();
    }

    // MODIFIES: this
    // EFFECTS: sets direction as a random direction so long as the new direction isn't the opposite one
    public void setDirection() {

        Direction newDirection = VALUES.get(randomizer.nextInt(SIZE));

        while (oppositeDirection(newDirection)) {

            newDirection = VALUES.get(randomizer.nextInt(SIZE));
        }

        this.direction = newDirection;
    }

    public void setDirection(Direction direction) {

        this.direction = direction;
    }

    // REQUIRES: Direction
    // EFFECTS: checks whether the new direction is directly opposite the current direction
    public boolean oppositeDirection(Direction newDirection) {

        if (newDirection == Direction.RIGHT) {

            return getDirection() == Direction.LEFT;

        } else if (newDirection == Direction.LEFT) {

            return getDirection() == Direction.RIGHT;

        } else if (newDirection == Direction.UP) {

            return getDirection() == Direction.DOWN;

        } else if (newDirection == Direction.DOWN) {

            return getDirection() == Direction.UP;

        }

        return false;
    }

    // REQUIRES: List of Directions
    // EFFECTS: if the pacman is lower than the ghost and ghost can move down, set ghost's direction to down
    //          else set the direction to up
    public void setDirectionSameX(List<Direction> possibleDirections) {

        if (pacman.getY() > getGhostY() && possibleDirections.contains(Direction.DOWN)) {

            setDirection(Direction.DOWN);

        } else if (possibleDirections.contains(Direction.UP)) {

            setDirection(Direction.UP);
        }
    }

    // REQUIRES: List of Directions
    // EFFECTS: if the pacman is to the right of the ghost and ghost can move right, set ghost's direction to right
    //          else set the direction to left
    public void setDirectionSameY(List<Direction> possibleDirections) {

        if (pacman.getX() > getGhostX() && possibleDirections.contains(Direction.RIGHT)) {

            setDirection(Direction.RIGHT);

        } else if (possibleDirections.contains(Direction.LEFT) && possibleDirections.contains(Direction.LEFT)) {

            setDirection(Direction.LEFT);
        }
    }

    public void setPacman(Pacman pacman) {

        if (this.pacman == null) {

            this.pacman = pacman;
            this.pacman.setGhosts(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the Pacman
    public void removePacman() {

        if (this.pacman != null) {

            this.pacman.removeGhost(this);
            this.pacman = null;
        }
    }

    public Pacman getPacman() {

        return this.pacman;
    }

    // MODIFIES: this
    // EFFECTS: sets ghost back to original position and previous position off the board
    //          calls two other methods to reset and restart the ghost's timer
    public void resetGhostPosition() {

        this.ghostX = originalX;
        this.ghostY = originalY;
        this.previousX = 100;
        this.previousY = 100;
        setBeginDelay();
        restart();
    }

    // MODIFIES: this
    // EFFECTS: - sets eaten to true
    //          - sets blue to false
    //          - resets the ghost's position back to the box
    //          - keeps ghost in box for 3 seconds
    //          - resets the ghost's timer, restarts it, and sets delay between actions to 300 milliseconds
    public void eaten() {

        eaten = true;
        this.blue = false;
        resetGhostPosition();
        setInitialDelay(3000);
        restart();
        setDelay(300);
    }

    public boolean getEaten() {

        return this.eaten;
    }

    // MODIFIES: this
    // EFFECTS: ghost becomes blue, and delay is 750 milliseconds between actions (ghost slows down)
    public void blue() {

        this.blue = true;
        setDelay(750);

    }

    public boolean getBlue() {

        return this.blue;
    }

    // EFFECTS: returns ghost's hashcode, which is based on the ghost's name
    @Override
    public int hashCode() {

        if (this.name == null) {

            return -1;
        }

        return this.name.hashCode();
    }

    // REQUIRES: Object parameter
    // EFFECTS: checks to make sure that two ghosts are equal;
    //          - if class, name, x, and y of ghost and object are the same, return true; else false
    @Override
    public boolean equals(Object compared) {

        if (compared == null) {

            return false;
        }

        if (compared.getClass() != Ghost.class) {

            return false;
        }

        Ghost otherGhost = (Ghost) compared;

        if (this.ghostX != ((Ghost) compared).getGhostX() || this.ghostY != ((Ghost) compared).getGhostY()) {

            return false;
        }

        return this.hashCode() == otherGhost.hashCode() && this.name.equals(otherGhost.name);
    }

    public void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
    }

    // EFFECTS: checks if there is a wall in front of the ghost based on its direction
    public boolean ghostPathBlocked() {

        if (getDirection() == Direction.UP) {

            return walls.contains(new Wall(ghostX, ghostY - 1));

        } else if (getDirection() == Direction.DOWN) {

            return walls.contains(new Wall(ghostX, ghostY + 1));

        } else if (getDirection() == Direction.LEFT) {

            return walls.contains(new Wall(ghostX - 1, ghostY));

        } else {

            return walls.contains(new Wall(ghostX + 1, ghostY));
        }
    }

    // REQUIRES: Direction
    // EFFECTS: if the path isn't blocked, add the current direction to list of possible directions;
    //          if the direction is horizontal, call the corresponding function;
    //          else call the function that corresponds to the vertical direction
    public void pathClear(Direction d) {

        List<Direction> possibleDirections = new ArrayList<>();
        if (!ghostPathBlocked()) {
            possibleDirections.add(getDirection());
        }

        if (d == Direction.LEFT || d == Direction.RIGHT) {

            pathClearDirectionIsHorizontal(possibleDirections);

        } else {

            pathClearDirectionIsVertical(possibleDirections);
        }
    }

    // REQUIRES: List of Directions
    // MODIFIES: this, possibleDirections
    // EFFECTS: if the path is clear, add the direction to the list of possible directions
    //          calls functions based on pacman's position compared to the ghost
    public void pathClearDirectionIsHorizontal(List<Direction> possibleDirections) {

        if (!walls.contains(new Wall(ghostX, ghostY - 1))) {

            possibleDirections.add(Direction.UP);
        }
        if (!walls.contains(new Wall(ghostX, ghostY + 1))) {

            possibleDirections.add(Direction.DOWN);
        }

        if (pacman.getY() == getGhostY()) {

            setDirectionSameY(possibleDirections);

        } else {

            if (pacman.getY() > getGhostY()) {

                possibleDirections = addPossiblePathsUp(possibleDirections);

            } else if (pacman.getY() < getGhostY()) {

                possibleDirections = addPossiblePathsDown(possibleDirections);
            }
            int randomNum = randomizer.nextInt(possibleDirections.size());
            setDirection(possibleDirections.get(randomNum));

        }
    }

    // REQUIRES: List of Directions
    // MODIFIES: possibleDirections
    // EFFECTS: add more down, straight, and up to possible directions list
    //          if the list contains those directions;
    //          add more down and straight direction than up
    //          return the list
    public List<Direction> addPossiblePathsUp(List<Direction> possibleDirections) {

        for (int i = 0; i < 30; i++) {

            if (possibleDirections.contains(Direction.UP) && i < 1) {

                possibleDirections.add(Direction.UP);
            }
            if (possibleDirections.contains(Direction.DOWN)) {

                possibleDirections.add(Direction.DOWN);
            }

            possibleDirections.add(getDirection());
        }

        return possibleDirections;
    }

    // REQUIRES: List of Directions
    // MODIFIES: possibleDirections
    // EFFECTS: add up, down, straight directions to possible directions list
    //          if the list contains those directions;
    //          add more up and straight direction than down
    //          return the list
    public List<Direction> addPossiblePathsDown(List<Direction> possibleDirections) {

        for (int i = 0; i < 30; i++) {

            if (possibleDirections.contains(Direction.DOWN) && i < 1) {

                possibleDirections.add(Direction.DOWN);
            }
            if (possibleDirections.contains(Direction.UP)) {

                possibleDirections.add(Direction.UP);
            }
            possibleDirections.add(getDirection());
        }

        return possibleDirections;
    }

    // REQUIRES List of Directions
    // MODIFIES: this, possibleDirections
    // EFFECTS: if the path is clear, add the direction to the list of possible directions
    //          calls functions based on pacman's position compared to the ghost
    public void pathClearDirectionIsVertical(List<Direction> possibleDirections) {

        if (!walls.contains(new Wall(ghostX + 1, ghostY))) {

            possibleDirections.add(Direction.RIGHT);
        }
        if (!walls.contains(new Wall(ghostX - 1, ghostY + 1))) {

            possibleDirections.add(Direction.LEFT);
        }

        if (pacman.getX() == getGhostX()) {

            setDirectionSameX(possibleDirections);

        } else {

            if (pacman.getX() > getGhostX()) {

                possibleDirections = setPossiblePathsLeft(possibleDirections);

            } else if (pacman.getX() < getGhostX()) {

                possibleDirections = setPossiblePathsRight(possibleDirections);
            }

            int randomNum = randomizer.nextInt(possibleDirections.size());
            setDirection(possibleDirections.get(randomNum));
        }
    }

    // REQUIRES: List of Directions
    // MODIFIES: possibleDirections
    // EFFECTS: add left, right, straight directions to possible directions list
    //          if the list contains those directions;
    //          add more right and straight direction than down
    //          return the list
    public List<Direction> setPossiblePathsLeft(List<Direction> possibleDirections) {

        for (int i = 0; i < 30; i++) {

            if (possibleDirections.contains(Direction.LEFT) && i < 1) {

                possibleDirections.add(Direction.LEFT);
            }
            if (possibleDirections.contains(Direction.RIGHT)) {

                possibleDirections.add(Direction.RIGHT);
            }

            possibleDirections.add(getDirection());
        }

        return possibleDirections;
    }

    // REQUIRES: List of directions
    // MODIFIES: possibleDirections
    // EFFECTS: add left, right, straight directions to possible directions list
    //          if the list contains those directions;
    //          add more left and straight direction than down
    //          return the list
    public List<Direction> setPossiblePathsRight(List<Direction> possibleDirections) {

        for (int i = 0; i < 30; i++) {

            if (possibleDirections.contains(Direction.RIGHT) && i < 1) {

                possibleDirections.add(Direction.RIGHT);
            }
            if (possibleDirections.contains(Direction.LEFT)) {

                possibleDirections.add(Direction.LEFT);
            }
            possibleDirections.add(getDirection());
        }

        return possibleDirections;
    }

    // MODIFIES: this
    // EFFECTS: updates the ghost on the action listener, checking for paths and moving the ghost
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ghostY == 6 && ghostX > 16 && ghostX < 22 && game.getContinues()) {

            ghostX = 19;
            ghostY = 3;
        }

        pathClear(getDirection());

        while (ghostPathBlocked()) {

            setDirection();
        }

        if (game.getContinues()) {

            move();
        }
    }
}
