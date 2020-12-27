package game.domain;

import javax.swing.*;

public abstract class Game {

    protected Updatable updatable;

    public void play() {

        UI ui = this.getUI();

        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                System.out.println("The drawing board hasn't been created yet");
            }
        }

        this.setUpdatable(ui.getUpdatable());
    }

    protected void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
    }

    protected abstract UI getUI();
}
