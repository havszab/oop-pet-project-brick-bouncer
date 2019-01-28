package game.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dataaccess.KeyDAO;
import dataaccess.implementation.KeyDAOFileHandler;
import entities.Entity;
import game.run.GameLoop;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;

public class Globals {

    public static final double WINDOW_WIDTH = 1000;
    public static final double WINDOW_HEIGHT = 700;

    public static final int LEFT_BORDER = (int) (WINDOW_WIDTH / 2) * -1;//incorrect
    public static final int RIGHT_BORDER = (int) WINDOW_WIDTH / 2;//incorrect

    public static final int VERTICAL_CENTER = 350;
    public static final int HORIZONTAL_CENTER = 0;

    public static final int MENU_BUTTON_WIDTH = 400;
    public static final int MENU_BUTTON_HEIGHT = 60;

    public static final double VERTICAL_RADIUS = 90;
    public static final double HORIZONTAL_RADIUS = 0;

    public static int SECS_PER_MIN = 60;
    public static int STEP_CALLED_PER_SEC = 60;

    private static int currentLevel = 1;
    private static long currentScore = 0;


    public static Image border = new Image("borders/border_narrow.png");
    public static Image barrierBlue = new Image("barriers/barrier_olive.png");
    public static Image barrierPink = new Image("barriers/barrier_yellow.png");
    public static Image barrierRed = new Image("barriers/barrier_grey.png");

    public static Image bomb = new Image("powerups/bomb.png");
    public static Image fragment = new Image("powerups/fragment.png");
    public static Image ultraBall = new Image("balls/ball_electric.png");
    public static Image ballUltralizer = new Image("powerups/ult_orb.png");

    private static Image ingameBGImg = new Image("background/ingameBG.jpg");
    private static Image paperBGImg = new Image("background/infoBG.jpg");
    private static Image levelSelectBGImg = new Image("background/levelsBG.jpg");
    private static Image mainBGImg = new Image("background/mainBG.jpg");
    private static Image shopBGImg = new Image("background/shopBG.jpg");

    public static Background ingameBG = new Background(new BackgroundFill(new ImagePattern(ingameBGImg), CornerRadii.EMPTY, Insets.EMPTY));
    public static Background paperBG = new Background(new BackgroundFill(new ImagePattern(paperBGImg), CornerRadii.EMPTY, Insets.EMPTY));
    public static Background paperLvlSelectBG = new Background(new BackgroundFill(new ImagePattern(levelSelectBGImg), CornerRadii.EMPTY, Insets.EMPTY));
    public static Background mainBG = new Background(new BackgroundFill(new ImagePattern(mainBGImg), CornerRadii.EMPTY, Insets.EMPTY));
    public static Background shopBG = new Background(new BackgroundFill(new ImagePattern(shopBGImg), CornerRadii.EMPTY, Insets.EMPTY));


    public static AudioClip menuBtnHover = new AudioClip(new File("resources/music/menu_button_hover.wav").toURI().toString());;
    public static AudioClip themeMusic = new AudioClip(new File("resources/music/theme_music.wav").toURI().toString());
    public static AudioClip ballBounce = new AudioClip(new File("resources/music/ball_bounce.wav").toURI().toString());
    public static AudioClip win = new AudioClip(new File("resources/music/win.wav").toURI().toString());
    public static AudioClip destruction = new AudioClip(new File("resources/music/barrier_break.wav").toURI().toString());
    public static AudioClip bombExplosion = new AudioClip(new File("resources/music/bomb_explosion.wav").toURI().toString());
    public static AudioClip ultSound = new AudioClip(new File("resources/music/ball_ult.wav").toURI().toString());
    public static AudioClip ultraBrickDestroy = new AudioClip(new File("resources/music/ultra_destroys_brick.wav").toURI().toString());



    public static Image board = setStartingBoardImg();
    public static Image ball = setStartingBallImg();

    public static boolean leftKeyDown;
    public static boolean rightKeyDown;
    public static boolean shiftDown;

    public static List<Entity> gameObjects;
    public static List<Entity> newGameObjects;
    public static List<Entity> oldGameObjects;
    public static GameLoop gameLoop;

    static {
        gameObjects = new LinkedList<>();
        newGameObjects = new LinkedList<>();
        oldGameObjects = new LinkedList<>();
    }

    public static String currentBallPath;

    private static Image setStartingBoardImg () {
        KeyDAO kd = KeyDAOFileHandler.getInstance();
        int currentInUse = 0;
        try {
            currentInUse = kd.getBoardInUse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        board = new Image("boards/board_" + currentInUse + ".png");
        return board;
    }

    public static Image setStartingBallImg () {
        KeyDAO kd = KeyDAOFileHandler.getInstance();
        int currentInUse = 0;
        try {
            currentInUse = kd.getBallInUse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ball = new Image("balls/ball_" + currentInUse + ".png");
        return ball;
    }

    public static void setBoardImg(String path) {
        board = new Image(path);
    }

    public static void setBallImg(String path) {
        ball = new Image(path);
    }

    public static void addGameObject(Entity toAdd) {
        newGameObjects.add(toAdd);
    }

    public static void removeGameObject(Entity toRemove) {
        oldGameObjects.add(toRemove);
    }

    public static List<Entity> getGameObjects() {
        return Collections.unmodifiableList(gameObjects);
    }

    public static void removeAllGameObjects() {
        for (Entity ent : getGameObjects()) {
            ent.destroy();
        }
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel (int lvl) {
        currentLevel = lvl;
    }

    public static long getCurrentScore() {
        return currentScore;
    }

    public static void setCurrentScore(long currentScore) {
        Globals.currentScore = currentScore;
    }
}
