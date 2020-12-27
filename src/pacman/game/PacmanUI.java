package pacman.game;

import game.domain.UI;
import game.domain.Updatable;

import javax.swing.*;
import java.awt.*;

public class PacmanUI extends UI {

    private PacmanGame game;
    private JFrame frame;
    private int pieceLength;
    private PacmanDrawer board;

    public PacmanUI(PacmanGame game, int pieceLength) {

        this.game = game;
        this.pieceLength = pieceLength;
    }

    // MODIFIES: this
    // EFFECTS: Create the JFrame for the Application
    @Override
    public void run() {

        frame = new JFrame("Pacman Game");
        int width = (game.getWidth() + 1) * pieceLength + 20;
        int height = (game.getHeight() + 3) * pieceLength + 15;

        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds drawing board and keyboard listener
    @Override
    public void createComponents(Container container) {

        this.board = new PacmanDrawer(game, this.pieceLength);
        container.add(board);

        KeyboardListener kl = new KeyboardListener(game.getPacman());
        this.frame.addKeyListener(kl);
    }



    @Override
    protected Updatable getUpdatable() {
        return this.board;
    }

    @Override
    protected JFrame getFrame() {
        return this.frame;
    }
}
