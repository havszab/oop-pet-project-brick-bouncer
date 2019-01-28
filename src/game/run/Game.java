package game.run;

import entities.ingame.Ball;
import entities.ingame.Board;
import entities.ingame.border.Border;
import game.util.GameLevelInitializer;
import game.util.Globals;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import userinterface.WindowUtil;

public class Game extends Pane {

    public Game() {
        //Font.getFamilies().forEach(System.out::println);
    }


    public void start(int lvl)  {
        init(lvl);
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = true; break;
                case RIGHT: Globals.rightKeyDown  = true; break;
                case SHIFT: Globals.shiftDown = true; break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = false; break;
                case RIGHT: Globals.rightKeyDown  = false; break;
                case SHIFT: Globals.shiftDown = false; break;
            }
        });

        Globals.gameLoop = new GameLoop(this);
        Globals.gameLoop.setCurrentGame(this);
        Globals.gameLoop.start();
    }

    private void init(int lvl) {
        WindowUtil.ingameUI(this);
        new Board(this, 450, 650);
        initBorders();
        GameLevelInitializer.initBarriers(this, lvl);
        new Ball(this, 400, 600, 3);
    }

    private void initBorders() {
        new Border(this, Globals.HORIZONTAL_CENTER, (int) Globals.WINDOW_HEIGHT - 25, Globals.HORIZONTAL_RADIUS, "BOTTOM");
        new Border(this, Globals.HORIZONTAL_CENTER, 0, Globals.HORIZONTAL_RADIUS, "TOP");

        new Border(this, Globals.HORIZONTAL_CENTER, 75, Globals.HORIZONTAL_RADIUS, "TOP");

        new Border(this, Globals.LEFT_BORDER+5, Globals.VERTICAL_CENTER, Globals.VERTICAL_RADIUS, "LEFT");
        new Border(this, Globals.RIGHT_BORDER-5, Globals.VERTICAL_CENTER, Globals.VERTICAL_RADIUS, "RIGHT");
    }


}
