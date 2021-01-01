package Games.GameAbstractions;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class TimerGame extends Game implements ActionListener {

    protected Timer timer;

    protected void start(int tickDelay, int initialDelay) {

        this.timer = new Timer(tickDelay, null);
        this.timer.addActionListener(this);
        this.timer.setInitialDelay(initialDelay);
        this.timer.start();
    }
}
