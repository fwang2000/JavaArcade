package hangman.game;

import game.domain.UI;
import game.domain.Updatable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HangmanUI extends UI {

    private JFrame frame;
    private HangmanDrawer board;
    private JPanel letterPanel;
    private JPanel numberPanel;
    private List<JButton> buttons;
    private HangmanGame game;

    public HangmanUI(HangmanGame game) {

        this.game = game;
        this.buttons = new ArrayList<>();
        game.setUI(this);
    }

    // MODIFIES: this
    // EFFECTS: Create the JFrame for the Application
    @Override
    public void run() {

        frame = new JFrame("Hangman");
        int width = 700;
        int height = 800;

        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds panels for the buttons of numbers, letters, and the drawing board
    @Override
    public void createComponents(Container container) {

        this.board = new HangmanDrawer(game);
        container.add(board);

        this.letterPanel = new JPanel(new GridLayout(2, 13));
        this.numberPanel = new JPanel(new GridLayout(1, 10));

        setButtons();

        JPanel subPanel = new JPanel(new GridLayout(3, 1));
        subPanel.add(letterPanel);
        subPanel.add(numberPanel);
        container.add(subPanel, BorderLayout.SOUTH);
    }

    public void setButtons() {

        List<String> letters = game.getPossibleGuesses();

        for (int i = 0; i < letters.size(); i++) {

            JButton button = new JButton(letters.get(i));
            buttons.add(button);
            button.addActionListener(new ButtonListener(button, game));
            if (i < 26) {

                letterPanel.add(button);

            } else {

                numberPanel.add(button);
            }
        }
    }

    public List<JButton> getButtons() {

        return this.buttons;
    }

    @Override
    public JFrame getFrame() {

        return this.frame;
    }

    @Override
    public Updatable getUpdatable() {

        return this.board;
    }
}
