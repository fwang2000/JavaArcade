package snake.domain;

import exceptions.IllegalDirectionChangeException;
import game.domain.Direction;
import game.domain.Movable;
import game.domain.Piece;
import game.domain.RunIntoAble;

import java.util.ArrayList;
import java.util.List;

public class Snake implements RunIntoAble, Movable {

    private List<Piece> pieces;
    public Direction dir;
    private int originalX;
    private int originalY;
    private boolean grow;

    // REQUIRES: positive integer between 0 and width,
    //           positive integer between 0 and height,
    //           Direction
    // MODIFIES: pieces
    // EFFECTS: instantiates 5 fields, adds snake head to pieces
    public Snake(int originalX, int originalY, Direction originalDir) {

        this.originalX = originalX;
        this.originalY = originalY;
        this.pieces = new ArrayList<Piece>();
        this.dir = originalDir;
        this.grow = true;

        pieces.add(new Piece(originalX, originalY));
    }

    // EFFECTS: returns direction;
    public Direction getDirection() {

        return this.dir;
    }

    // REQUIRES: Direction parameter
    // MODIFIES: this
    // EFFECTS: sets dir to parameter direction
    public void setDirection(Direction direction) throws IllegalDirectionChangeException {

        if (checkOppositeDirection(direction)) {

            throw new IllegalDirectionChangeException();

        } else {

            this.dir = direction;
        }
    }

    // REQUIRES: Direction parameter
    // EFFECTS: checks to see if inputted direction is the opposite of the current direction
    private boolean checkOppositeDirection(Direction direction) {

        if (this.dir.equals(Direction.DOWN) && direction.equals(Direction.UP)) {

            return true;

        } else if (this.dir.equals(Direction.UP) && direction.equals(Direction.DOWN)) {

            return true;

        } else if (this.dir.equals(Direction.LEFT) && direction.equals(Direction.RIGHT)) {

            return true;

        } else if (this.dir.equals(Direction.RIGHT) && direction.equals(Direction.LEFT)) {

            return true;
        }

        return false;
    }

    // EFFECTS: returns length of this.pieces;
    public int getLength() {

        return this.pieces.size();
    }

    // EFFECTS: return list of pieces
    public List<Piece> getPieces() {

        return this.pieces;
    }

    // REQUIRES: pieces.size() >= 1
    // MODIFIES: this
    // EFFECTS: instructions for the movement of snake given this.direction
    public void move() {

        Piece snakeHead = this.pieces.get(pieces.size() - 1);
        Piece newHead;

        if (getDirection() == Direction.UP) {

            newHead = new Piece(snakeHead.getX(), snakeHead.getY() - 1);
            pieces.add(newHead);

        } else if (getDirection() == Direction.DOWN) {

            newHead = new Piece(snakeHead.getX(), snakeHead.getY() + 1);
            pieces.add(newHead);

        } else if (getDirection() == Direction.LEFT) {

            newHead = new Piece(snakeHead.getX() - 1, snakeHead.getY());
            pieces.add(newHead);

        } else if (getDirection() == Direction.RIGHT) {

            newHead = new Piece(snakeHead.getX() + 1, snakeHead.getY());
            pieces.add(newHead);
        }

        checkSnakeGrowsAfterMove();
    }

    // REQUIRES: this.pieces.size() >= 1
    // MODIFIES: this
    // EFFECTS: if snake no longer grows, remove the tail piece;
    //          if the snake's size is >= 3, stop growing
    public void checkSnakeGrowsAfterMove() {

        if (!this.grow) {

            this.pieces.remove(0);
        }
        if (this.pieces.size() >= 3) {

            this.grow = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: set grow to true
    public void grow() {

        this.grow = true;
    }

    // REQUIRES: parameter must be piece object
    // EFFECTS: returns true if a piece of the worm runs into another piece;
    //          else return false
    public boolean runsInto(Piece piece) {

        for (Piece wormPiece : this.pieces) {

            if (wormPiece.getX() == piece.getX() && wormPiece.getY() == piece.getY()) {

                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if a piece of the snake runs into another piece of snake;
    //          else return false
    public boolean runsIntoItself() {

        for (Piece pieceA : getPieces()) {

            for (Piece pieceB : getPieces()) {

                if (pieceA == pieceB) {

                    continue;
                }
                if (pieceA.getX() == pieceB.getX() && pieceA.getY() == pieceB.getY()) {

                    return true;
                }
            }
        }
        return false;
    }
}
