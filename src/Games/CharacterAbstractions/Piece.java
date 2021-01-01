package Games.CharacterAbstractions;

public class Piece {

    protected int x;
    protected int y;

    // EFFECTS: instantiates the x and y coordinates of the Piece object
    public Piece(int x, int y) {

        this.x = x;
        this.y= y;
    }

    // EFFECTS: returns the x coordinate of the Piece
    public int getX() {

        return this.x;
    }

    // EFFECTS: returns the y coordinate of the Piece
    public int getY() {

        return this.y;
    }

    // EFFECTS: returns whether or not the Piece runs into another piece
    public boolean runsInto(Piece piece) {

        return this.x == piece.getX() && this.y == piece.getY();
    }
}
