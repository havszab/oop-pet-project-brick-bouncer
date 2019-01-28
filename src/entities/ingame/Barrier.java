package entities.ingame;

import entities.Entity;
import game.util.Globals;
import game.util.Util;
import javafx.scene.layout.Pane;

public class Barrier extends Entity {

    private int livesLeft;

    public Barrier(Pane pane, int xc, int yc, int livesLeft) {
        super(pane);
        this.pane = pane;
        setX(xc);
        setY(yc);
        setLivesLeft(livesLeft);
        setImageBasedOnLivesLeft();
        pane.getChildren().add(this);
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void handleIncomingDamage() {
        this.livesLeft--;
        if (this.getLivesLeft() > 0)
            setImageBasedOnLivesLeft();
        else {
            Globals.destruction.play();
            destroy();
            Util.checkAndHandleGameWin();
        }
    }

    public void handleUltraDamage() {
        Globals.destruction.play();
        destroy();
    }

    private void setImageBasedOnLivesLeft() {
        if (this.getLivesLeft() == 3)
            this.setImage(Globals.barrierBlue);
        else if (this.getLivesLeft() == 2)
            this.setImage(Globals.barrierPink);
        else if (this.getLivesLeft() == 1)
            this.setImage(Globals.barrierRed);

    }
}
