package entities.ingame;

import entities.Animatable;
import entities.Entity;
import game.util.Globals;
import game.util.Util;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Board extends Entity implements Animatable {

    private static float speed = (float) 7.5;
    private static float rightSpeed;
    private static float leftSpeed;
    private static final float speedGrowth = (float) 0.15;

    public Board(Pane pane, int xc, int yc) {
        super(pane);
        this.pane = pane;
        setX(xc);
        setY(yc);
        setImage(Globals.board);
        pane.getChildren().add(this);
    }

    public void step() {

        double dirLeft = 270;
        double dirRight = 90;

        if (Globals.leftKeyDown && this.getX() > -30) {
            rightSpeed = 0;
            Point2D heading = Util.directionToVector(dirLeft, speed + leftSpeed);
            setX(getX() + heading.getX());
            leftSpeed += speedGrowth;

        }
        if (Globals.rightKeyDown && this.getX() < 930) {
            leftSpeed = 0;
            Point2D heading = Util.directionToVector(dirRight, speed + rightSpeed);
            setX(getX() + heading.getX());
            rightSpeed += speedGrowth;
        }
    }
}
