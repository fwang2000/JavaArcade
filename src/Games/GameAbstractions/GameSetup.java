package Games.GameAbstractions;

import javax.swing.*;
import java.lang.Thread.State;

public abstract class GameSetup {

    protected Game game;
    protected UI ui;

    public abstract void setGame();

    protected abstract UI getUI();

    public void playUI() {

        this.setGame();

        UI ui = this.getUI();
        SwingUtilities.invokeLater(ui);
        while (ui.getUpdatable() == null) {

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                System.out.println("The drawing board hasn't been created yet");
            }
        }

        this.game.setUpdatable(ui.getUpdatable());
        System.out.println("Open the Java Application");
    }
}
