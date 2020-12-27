package hangman.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    private JButton button;
    private HangmanGame game;

    public ButtonListener(JButton button, HangmanGame game) {

        this.button = button;
        this.game = game;
    }

    // EFFECTS: guesses a letter, sets corresponding button to false, and updates the board
    @Override
    public void actionPerformed(ActionEvent ae) {

        game.guess(button.getText());
        button.setEnabled(false);
    }
}
