package Games.GameAbstractions;

public abstract class Game {

    protected Updatable updatable;

    protected void setUpdatable(Updatable updatable) {

        this.updatable = updatable;
    }
}
