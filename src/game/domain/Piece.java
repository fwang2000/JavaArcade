package game.domain;

public class Piece implements RunIntoAble {

    private int pieceX;
    private int pieceY;

    // EFFECTS: instantiates the x and y coordinates of the Piece object
    public Piece(int x, int y) {

        this.pieceX = x;
        this.pieceY = y;
    }

    // EFFECTS: returns the x coordinate of the Piece
    public int getX() {

        return this.pieceX;
    }

    // EFFECTS: returns the y coordinate of the Piece
    public int getY() {

        return this.pieceY;
    }

    // EFFECTS: returns whether or not the Piece runs into another piece
    public boolean runsInto(Piece piece) {

        return this.pieceX == piece.getX() && this.pieceY == piece.getY();
    }
}
