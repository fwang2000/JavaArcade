package Games.Snake.UI;

import Games.Snake.Gameplay.SnakeGame;
import Games.GameAbstractions.UI;
import Games.GameAbstractions.Updatable;

import javax.swing.*;
import java.awt.*;

public class SnakeUI extends UI {

    private JFrame frame;
    private SnakeGame game;
    private int sideLength;
    private SnakeDrawer board;

    public SnakeUI(SnakeGame game, int sideLength) {

        this.game = game;
        this.sideLength = sideLength;
    }

    // MODIFIES: this
    // EFFECTS: Create the JFrame for the Application
    @Override
    public void run() {

        frame = new JFrame("Snake Game");
        int width = (game.getWidth() + 1) * sideLength + 20;
        int height = (game.getHeight() + 2) * sideLength + 35;

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

        this.board = new SnakeDrawer(game, this.sideLength);
        container.add(board);

        KeyboardListener kl = new KeyboardListener(game.getSnake());
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
