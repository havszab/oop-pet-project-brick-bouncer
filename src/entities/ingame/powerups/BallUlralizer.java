package entities.ingame.powerups;

import entities.Animatable;
import entities.Entity;
import game.util.Globals;
import javafx.scene.layout.Pane;

public class BallUlralizer extends Entity implements Animatable {

    public BallUlralizer() {
    }

    public BallUlralizer(Pane pane, double x, double y) {
        super(pane);
        this.pane = pane;
        pane.getChildren().add(this);
        this.setX(x);
        this.setY(y);
        this.setImage(Globals.ballUltralizer);
    }

    @Override
    public void step() {
        this.setRotate(this.getRotate()+10);
    }
}
