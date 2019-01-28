package entities;

import game.util.Globals;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Entity extends ImageView {

    public Pane pane;

    protected Entity() {

    }

    protected Entity(Pane pane) {
        this.pane = pane;
        Globals.addGameObject(this);
    }

    public void destroy() {
        if (getParent() != null) pane.getChildren().remove(this);
        Globals.removeGameObject(this);
    }
}
