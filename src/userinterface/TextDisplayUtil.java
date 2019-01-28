package userinterface;

import entities.ingame.Ball;
import game.run.Game;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

import static userinterface.WindowUtil.currentGame;

public class TextDisplayUtil {

    public static Text displayScore = new Text();
    public static Text displayLives = new Text();
    public static Text displayKeys = new Text();
    static Text scores = new Text();
    static Text bonusText = new Text();

    static void displayScore() {
        displayText(currentGame, 250, 55, displayScore, "Score: 0");
    }

    static void displayLives() {
        Ball.resetLives();
        displayText(currentGame, 50, 55, displayLives, "Lives left: 3");
    }

    static void displayHighScores() {
        displayText(currentGame, 450, 110, scores, "High scores");
    }

    static void displayText(Game game, int setX, int setY, Text text, String itemName) {
        text.setText(itemName);
        text.setX(setX);
        text.setY(setY);
        text.setFont(Font.font("papyrus", FontWeight.BOLD, FontPosture.REGULAR, 35));
        text.setFill(Color.BROWN);
        if (!game.getChildren().contains(text))
            game.getChildren().add(text);
    }

    static void displayKeys() throws FileNotFoundException {
        displayKeysText(currentGame, 740, 130, WindowUtil.kd.getCount(), displayKeys, "");
    }

    static void displayKeysText(Game game, int setX, int setY, int getter, Text text, String itemName) {
        text.setText(itemName + getter);
        text.setX(setX);
        text.setY(setY);
        text.setFont(Font.font("Phosphate", FontWeight.BOLD, FontPosture.REGULAR, 50));
        Color brown = Color.web("#654b00");
        text.setFill(brown);
        if (!game.getChildren().contains(text))
            game.getChildren().add(text);
    }

    public static void displayBonus(double x, double y, int bonus) {

        bonusText.setText(bonus + "");
        System.out.println(bonus);
        bonusText.setX(x);
        bonusText.setX(y);
        bonusText.setFont(Font.font("Phosphate", FontWeight.BOLD, FontPosture.REGULAR, 20));
        if (!currentGame.getChildren().contains(bonusText))
            currentGame.getChildren().add(bonusText);

    }

    public static void removeTexts(Game game) {
        game.getChildren().remove(displayScore);
        game.getChildren().remove(displayLives);
        game.getChildren().remove(displayKeys);
        game.getChildren().remove(bonusText);
    }
}
