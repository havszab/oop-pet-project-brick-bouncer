package entities.ingame.powerups;

import entities.Animatable;
import entities.Entity;
import entities.ingame.Ball;
import entities.ingame.Barrier;
import game.util.Globals;
import game.util.Util;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Fragment extends Entity implements Animatable {

    private static float speed = 3;

    public Fragment() {
    }

    public Fragment(Pane pane, double X, double Y, double R) {
        super(pane);
        this.pane = pane;
        pane.getChildren().add(this);
        this.setX(X);
        this.setY(Y);
        this.setRotate(R);
        this.setImage(Globals.fragment);
    }

    @Override
    public void step() {
        Point2D heading = Util.directionToVector(this.getRotate(), speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());

        for (Entity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Barrier) {
                    destroy();
                    ((Barrier) entity).handleIncomingDamage();
                } else if (!(entity instanceof Fragment || entity instanceof Ball)) {
                    destroy();
                }
            }
        }
    }
}
