package Games.Pacman.Domain;

import Games.CharacterAbstractions.Direction;
import Games.CharacterAbstractions.Piece;
import Games.CharacterAbstractions.Character;
import Games.Pacman.Gameplay.PacmanGame;

import java.util.ArrayList;
import java.util.List;

public class Pacman extends Character {

    private int pacmanX;
    private int pacmanY;
    private int previousX;
    private int previousY;
    private Direction dir;
    private List<Ghost> ghosts;
    private List<Wall> walls;
    private PacmanGame game;

    public Pacman(int x, int y) {

        this.pacmanX = x;
        this.pacmanY = y;
        this.dir = Direction.LEFT;
        this.ghosts = new ArrayList<Ghost>();

    }

    // MODIFIES: this
    // EFFECTS: resets pacman back to its original position, and previous positions off the board
    public void reset() {

        this.pacmanX = 19;
        this.pacmanY = 11;
        this.previousX = -1;
        this.previousY = -1;
    }

    public void setGame(PacmanGame game) {

        if (this.game == null) {

            this.game = game;
            game.setPacman(this);
        }
    }

    public void setGhosts(Ghost ghost) {

        if (!this.ghosts.contains(ghost)) {

            this.ghosts.add(ghost);
            ghost.setPacman(this);
        }
    }

    // REQUIRES: Parameter must be a ghost
    // MODIFIES: this, ghost
    // EFFECTS: removes the pacman from the ghost and vice-versa
    public void removeGhost(Ghost ghost) {

        if (this.ghosts.contains(ghost)) {

            this.ghosts.remove(ghost);
            ghost.removePacman();
        }
    }

    public List<Ghost> getGhosts() {

        return this.ghosts;
    }

    public int getX() {

        return this.pacmanX;
    }

    public int getY() {

        return this.pacmanY;
    }

    // EFFECTS: returns the direction pacman travels in
    public Direction getDirection() {

        return this.dir;
    }

    // REQUIRES: Parameter must be a direction
    // MODIFIES: this
    // EFFECTS: sets dir as parameter Direction
    public void setDirection(Direction dir) {

        this.dir = dir;
    }

    public void setWalls() {

        this.walls = new ArrayList<>();

        walls.addAll(game.getWalls());
    }

    // MODIFIES: this
    // EFFECTS: updates previous position
    //          moves pacman's coordinates by 1 based on this.dir
    public void move() {

        setPreviousPosition();

        if (null != getDirection()) {

            switch (getDirection()) {

                case DOWN:
                    pacmanY++;
                    break;

                case LEFT:
                    pacmanX--;
                    break;

                case RIGHT:
                    pacmanX++;
                    break;

                default:
                    pacmanY--;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: keeps track of the previous position of the pacman
    public void setPreviousPosition() {

        if (null != getDirection()) {

            switch (getDirection()) {

                case LEFT:

                case RIGHT:
                    previousX = pacmanX;
                    break;

                default:
                    previousY = pacmanY;
            }
        }
    }

    public int getPreviousX() {

        return this.previousX;
    }

    public int getPreviousY() {

        return this.previousY;
    }

    // EFFECTS: checks if the pacman is blocked by a wall
    public boolean pathBlockedByWall() {

        if (getDirection() == Direction.UP) {

            return walls.contains(new Wall(pacmanX, pacmanY - 1));

        } else if (getDirection() == Direction.DOWN) {

            return walls.contains(new Wall(pacmanX, pacmanY + 1));

        } else if (getDirection() == Direction.LEFT) {

            return walls.contains(new Wall(pacmanX - 1, pacmanY));

        } else {

            return walls.contains(new Wall(pacmanX + 1, pacmanY));
        }
    }

    // REQUIRES: Parameter must be a piece
    // EFFECTS: returns true if pacman runs into another piece
    //          otherwise returns false
    public boolean runsInto(Piece piece) {

        return this.pacmanX == piece.getX() && this.pacmanY == piece.getY();
    }

    // REQUIRES: Parameter must be a ghost
    // EFFECTS: checks to see if a pacman runs into a ghost
    public boolean hitsGhost(Ghost ghost) {

        return this.pacmanX == ghost.getGhostX() && this.pacmanY == ghost.getGhostY();
    }
}
