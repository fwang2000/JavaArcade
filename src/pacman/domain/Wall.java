package pacman.domain;

public class Wall {

    private int wallX;
    private int wallY;

    public Wall(int x, int y) {

        this.wallX = x;
        this.wallY = y;
    }

    public int getWallX() {

        return wallX;
    }

    public int getWallY() {

        return wallY;
    }

    // EFFECTS: turns hashcode to 0;
    @Override
    public int hashCode() {

        return 0;
    }

    // REQUIRES: Object parameter
    // EFFECTS: checks to see if wall and object have same x and y coordinate
    @Override
    public boolean equals(Object compared) {

        if (compared.getClass() == null || compared.getClass() != Wall.class) {

            return false;
        }

        Wall otherWall = (Wall) compared;

        return otherWall.getWallX() == getWallX() && otherWall.getWallY() == getWallY();
    }
}

