package entities.ingame.powerups;

import entities.Entity;
import game.util.Globals;
import javafx.scene.layout.Pane;

public class Bomb extends Entity {

    public Bomb() {
    }

    public Bomb(Pane pane, double X, double Y) {
        super(pane);
        this.pane = pane;
        pane.getChildren().add(this);
        this.setX(X);
        this.setY(Y);
        this.setImage(Globals.bomb);
    }

    public void explode() {
        Globals.bombExplosion.play();
        for (float rotate = 0; rotate < 360 ; rotate += 45) {
            new Fragment(this.pane, this.getX(), this.getY(), rotate);
        }
        destroy();
    }
}
