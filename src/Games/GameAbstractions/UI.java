package Games.GameAbstractions;

import javax.swing.*;
import java.awt.*;

public abstract class UI implements Runnable {

    @Override
    public abstract void run();

    protected abstract void createComponents(Container container);

    protected abstract Updatable getUpdatable();

    protected abstract JFrame getFrame();
}
