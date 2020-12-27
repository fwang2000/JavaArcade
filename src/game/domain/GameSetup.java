package game.domain;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public abstract class GameSetup {

    protected Game game;
    protected UI ui;

    public abstract void makeGame();

    public void playUI() {

        this.makeGame();
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

    protected abstract UI getUI();
}
