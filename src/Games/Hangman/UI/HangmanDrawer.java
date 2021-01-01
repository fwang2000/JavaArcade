package Games.Hangman.UI;

import Games.Hangman.Gameplay.HangmanGame;
import Games.GameAbstractions.Updatable;

import javax.swing.*;
import java.awt.*;

public class HangmanDrawer extends JPanel implements Updatable {

    private HangmanGame game;

    public HangmanDrawer(HangmanGame game) {

        this.game = game;
        this.setBackground(Color.WHITE);
    }

    // EFFECTS: sets colour to black, paints everything;
    //          if game over, paint result, else paint game with string
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (game.checkGameEnd()) {

            this.paintResult(g);

        } else {

            this.paintString(g);

        }
        this.paintSide(g);
        this.paintMan(g, game.getMoves());
    }

    // EFFECTS: if player wins, draw "YOU WON" centered, else draw "YOU LOST" centered, in correct colour
    //          draw out the game string
    public void paintResult(Graphics g) {

        Dimension d = this.getSize();
        if (game.getResult().equals("WON")) {

            g.setColor(Color.GREEN);

        } else {

            g.setColor(Color.RED);
        }

        this.drawCenteredString("YOU " + game.getResult(), d.width, 500, g);

        g.setColor(Color.BLACK);
        this.drawCenteredString("The string was: " + game.getGameString(), d.width, 550, g);

    }

    // EFFECTS: Draws the displayed string (underscores and letters), in font based on the display strings length
    public void paintString(Graphics g) {

        Font font;
        int length = game.getDisplayString().length();

        if (length > 70) {

            font = new Font("TimesRoman", Font.BOLD, 17);

        } else if (length > 60) {

            font = new Font("TimesRoman", Font.BOLD, 20);

        } else if (game.getDisplayString().length() > 40) {

            font = new Font("TimesRoman", Font.BOLD, 25);

        } else {

            font = new Font("TimesRoman", Font.BOLD, 28);
        }
        this.setFont(font);

        Dimension d = this.getSize();
        this.drawCenteredString(game.getDisplayString(), d.width, 475, g);
        this.drawCenteredString("Attempts Left: " + game.getMoves(), d.width, 550, g);
    }

    // EFFECTS: centers the string to be drawn
    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        g.drawString(s, x, h);
    }

    // EFFECTS: Paints the hangman hanger
    public void paintSide(Graphics graphics) {

        Graphics2D g2d = (Graphics2D) graphics;

        graphics.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        graphics.drawLine(175, 50, 175, 400);
        graphics.drawLine(100, 400, 250, 400);
        graphics.drawLine(175, 50, 350, 50);
        g2d.setStroke(new BasicStroke(3));
        graphics.drawLine(175, 100, 225, 50);
        graphics.drawLine(350, 50, 350, 75);
    }

    // EFFECTS: Paints the hangman based on the amount of moves player has left
    public void paintMan(Graphics g, int moves) {

        if (moves < 6) {

            g.drawOval(313, 76, 76, 76);

        }
        if (moves < 5) {

            g.drawLine(350, 151, 350, 295);

        }
        if (moves < 4) {

            g.drawLine(350, 200, 275, 150);
        }
        if (moves < 3) {

            g.drawLine(350, 200, 425, 150);
        }
        if (moves < 2) {

            g.drawLine(350, 295, 275, 345);
        }
        if (moves < 1) {

            g.drawLine(350, 295, 425, 345);
        }
    }

    // EFFECTS: repaint the drawing board
    @Override
    public void update() {

        super.repaint();
    }
}
