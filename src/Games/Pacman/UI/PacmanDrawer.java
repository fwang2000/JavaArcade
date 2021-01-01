package Games.Pacman.UI;

import Games.CharacterAbstractions.Direction;
import Games.CharacterAbstractions.Piece;
import Games.GameAbstractions.Updatable;
import Games.Pacman.Domain.Dot;
import Games.Pacman.Domain.Fruit;
import Games.Pacman.Domain.Ghost;
import Games.Pacman.Domain.Wall;
import Games.Pacman.Gameplay.PacmanGame;

import javax.swing.*;
import java.awt.*;

public class PacmanDrawer extends JPanel implements Updatable {

    private final PacmanGame game;
    private final int sideLength;

    public PacmanDrawer(PacmanGame game, int sideLength) {

        super.setBackground(Color.BLACK);

        this.game = game;
        this.sideLength = sideLength;
    }

    // EFFECTS: Paint everything, if the game is over, paint it as such
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        paintWalls(g);
        paintFruit(g);
        paintDots(g);
        paintEaten(g);
        paintPacman(g, game.getPacman().getDirection());
        paintGhosts(g);
        if (!game.getContinues()) {

            paintGameEnd(g);
        }
    }

    // EFFECTS: paints all walls with colour based on their location
    public void paintWalls(Graphics g) {

        int i = 1;

        for (Wall wall : game.getWalls()) {

            int wallLength = wall.getX() * sideLength;
            int wallHeight = wall.getY() * sideLength;

            if (i <= 202) {

                g.setColor(new Color(0, 0, 153));

            } else if (i <= 269) {

                g.setColor(new Color(51, 204, 255));

            } else {

                g.setColor(new Color(255, 153, 0));
            }

            g.fill3DRect(wallLength, wallHeight, sideLength, sideLength, true);
            i++;
        }
    }

    // EFFECTS: Paint the pacman based on its direction and position
    public void paintPacman(Graphics g, Direction d) {

        int pacmanLength = game.getPacman().getX() * sideLength;
        int pacmanHeight = game.getPacman().getY() * sideLength;

        g.setColor(Color.yellow);

        if (d == Direction.UP) {

            g.fillArc(pacmanLength, pacmanHeight, sideLength - 2, sideLength - 2, 125, 290);

        } else if (d == Direction.LEFT) {

            g.fillArc(pacmanLength, pacmanHeight, sideLength - 2, sideLength - 2, 215, 290);

        } else if (d == Direction.DOWN) {

            g.fillArc(pacmanLength, pacmanHeight, sideLength - 2, sideLength - 2, 305, 290);

        } else if (d == Direction.RIGHT) {

            g.fillArc(pacmanLength, pacmanHeight, sideLength - 2, sideLength - 2, 35, 290);
        }
    }

    // EFFECTS: Paint the ghosts the right colour based on their name and paint correct location
    public void paintGhosts(Graphics g) {

        for (Ghost ghost : game.getGhosts()) {

            if (ghost.getBlue()) {

                if (ghost.getCounter() == 27 || ghost.getCounter() == 29) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(new Color(0, 0, 204));
                }

            } else if (ghost.getName().equals("Blinky")) {

                g.setColor(Color.RED);

            } else if (ghost.getName().equals("Inky")) {

                g.setColor(new Color(51, 204, 255));

            } else if (ghost.getName().equals("Pinky")) {

                g.setColor(Color.PINK);

            } else {

                g.setColor(Color.ORANGE);
            }

            g.fillOval(ghost.getGhostX() * sideLength, ghost.getGhostY() * sideLength, sideLength - 2, sideLength - 2);
        }
    }

    // EFFECTS: paint the fruit in their coordinates
    public void paintFruit(Graphics g) {

        for (Fruit fruit : game.getFruits()) {

            int fruitLength = fruit.getX() * sideLength + (sideLength / 2) - 6;
            int fruitHeight = fruit.getY() * sideLength + (sideLength / 2) - 6;

            g.setColor(new Color(255, 255, 153));
            g.drawOval(fruitLength, fruitHeight, sideLength / 2, sideLength / 2);
            g.fillOval(fruitLength, fruitHeight, sideLength / 2, sideLength / 2);
        }
    }

    // EFFECTS: paint all the dots in their locations
    public void paintDots(Graphics g) {

        for (Dot dot : game.getDots()) {

            int wallLength = dot.getX() * sideLength + (sideLength / 2) - 3;
            int wallHeight = dot.getY() * sideLength + (sideLength / 2) - 3;

            g.setColor(new Color(255, 255, 153));
            g.drawOval(wallLength, wallHeight, sideLength / 4, sideLength / 4);
            g.fillOval(wallLength, wallHeight, sideLength / 4, sideLength / 4);
        }
    }

    // EFFECTS: paint all the eaten fruits and dots over the dots and fruits in their location
    public void paintEaten(Graphics g) {

        for (Piece piece : game.getEaten()) {

            g.setColor(Color.BLACK);

            if (piece.getClass() == Dot.class) {

                int wallLength = piece.getX() * sideLength + (sideLength / 2) - 3;
                int wallHeight = piece.getY() * sideLength + (sideLength / 2) - 3;
                g.drawOval(wallLength, wallHeight, sideLength / 4, sideLength / 4);
                g.fillOval(wallLength, wallHeight, sideLength / 4, sideLength / 4);

            } else {

                int wallLength = piece.getX() * sideLength + (sideLength / 2) - 6;
                int wallHeight = piece.getY() * sideLength + (sideLength / 2) - 6;
                g.drawOval(wallLength, wallHeight, sideLength / 2, sideLength / 2);
                g.fillOval(wallLength, wallHeight, sideLength / 2, sideLength / 2);
            }
        }
    }

    // EFFECTS: if all dots are eaten, print "You Won", or print "Game Over" in the box
    public void paintGameEnd(Graphics g) {

        int height = game.getHeight() * sideLength / 2;
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.setColor(Color.white);

        if (game.getEaten().size() >= game.getDots().size()) {
            int width = game.getWidth() * sideLength / 2 - 50;
            g.drawString("You Won", width, height);

        } else {
            int width = game.getWidth() * sideLength / 2 - 61;
            g.drawString("Game Over", width, height);
        }
    }

    // EFFECTS: paint everything again
    @Override
    public void update() {

        super.repaint();
    }

}
