package Games.Pacman.Domain;

import Games.CharacterAbstractions.Piece;

public class Wall extends Piece {

    public Wall(int x, int y) {

        super(x, y);
    }

    // REQUIRES: Object parameter
    // EFFECTS: checks to see if wall and object have same x and y coordinate
    @Override
    public boolean equals(Object compared) {

        if (compared.getClass() == null || compared.getClass() != Wall.class) {

            return false;
        }

        Wall otherWall = (Wall) compared;

        return otherWall.getX() == getX() && otherWall.getY() == getY();
    }
}

