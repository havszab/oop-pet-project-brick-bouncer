package entities.ingame.border;

import entities.Entity;
import game.util.Globals;
import javafx.scene.layout.Pane;

public class Border extends Entity {

    private String type; // "BOTTOM", "LEFT", "RIGHT", "TOP"

    public Border(Pane pane, int xc, int yc, double rotation) {
        super(pane);
        this.pane = pane;
        setX(xc);
        setY(yc);
        setRotate(rotation);
        setImage(Globals.border);
        pane.getChildren().add(this);
    }

    public Border(Pane pane, int xc, int yc, double rotation, String type) {
        super(pane);
        this.pane = pane;
        setX(xc);
        setY(yc);
        setRotate(rotation);
        setImage(Globals.border);
        pane.getChildren().add(this);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTopBorder() {
        return this.getType().equals("TOP");
    }

    public boolean isBottomBorder() {
        return this.getType().equals("BOTTOM");
    }

    public boolean isRightBorder() {
        return this.getType().equals("RIGHT");
    }

    public boolean isLeftBorder() {
        return this.getType().equals("LEFT");
    }
}
