package game.run;

import entities.Animatable;
import entities.Entity;
import game.util.GameLevelInitializer;
import game.util.Globals;
import game.util.Util;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class GameLoop extends AnimationTimer {

    private Pane game;
    private static Game currentGame;

    public GameLoop(Pane pane) {
        super();
        game = pane;
    }

    @Override
    public void handle(long now) {
        for (Entity gameObject : Globals.gameObjects) {
            if (gameObject instanceof Animatable) {
                Animatable animObject = (Animatable) gameObject;
                animObject.step();
            }
        }
        Globals.gameObjects.addAll(Globals.newGameObjects);
        Globals.newGameObjects.clear();

        Globals.gameObjects.removeAll(Globals.oldGameObjects);
        Globals.oldGameObjects.clear();
    }

    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

}
