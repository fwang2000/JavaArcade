package Games.Snake.UI;

import Games.CharacterAbstractions.Piece;
import Games.Snake.Gameplay.SnakeGame;
import Games.GameAbstractions.Updatable;

import javax.swing.*;
import java.awt.*;

public class SnakeDrawer extends JPanel implements Updatable {
//
    private SnakeGame game;
    private int pieceLength;

    public SnakeDrawer(SnakeGame game, int pieceLength) {

        super.setBackground(Color.LIGHT_GRAY);

        this.game = game;
        this.pieceLength = pieceLength;
    }

    // EFFECTS: Paints snake and apple; if game doesn't continue, draw game over
    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        paintSnake(graphics);
        paintApple(graphics);
        if (!game.continues()) {

            this.paintGameOver(graphics);
        }
    }

    // EFFECTS: repaint everything
    @Override
    public void update() {

        super.repaint();
    }

    // EFFECTS: paint all of the snakes pieces in their locations in orange
    private void paintSnake(Graphics graphics) {

        for (Piece piece : game.getSnake().getPieces()) {

            int pieceXLength = piece.getX() * this.pieceLength;
            int pieceYLength = piece.getY() * this.pieceLength;

            graphics.setColor(Color.orange);
            graphics.fill3DRect(pieceXLength, pieceYLength, this.pieceLength, this.pieceLength, true);
        }
    }

    private void paintApple(Graphics graphics) {

        int appleXLength = game.getApple().getX() * pieceLength;
        int appleYLength = game.getApple().getY() * pieceLength;

        graphics.setColor(Color.red);
        graphics.fillOval(appleXLength, appleYLength, pieceLength, pieceLength);
    }

    private void paintGameOver(Graphics graphics) {

        int width = game.getWidth() * pieceLength / 2 - 50;
        int height = game.getHeight() * pieceLength / 2;
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
        graphics.drawString("Game Over", width, height);
    }
}
