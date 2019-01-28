package userinterface;

import game.run.Game;
import game.util.Globals;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.*;

class ButtonUtil {

    static List<Button> selectButtonList = new ArrayList<>();
    static List<Button> boardButtonList = new ArrayList<>();
    static List<Button> menuButtonList = new LinkedList<>();
    static List<Button> ballButtonList = new ArrayList<>();

    static int shopButtonFont = 15;

    static String inUse = "In use";
    static String unlocked = "Use";
    static String locked = "Buy";

    static String inUseColor = "AEDB9F";
    static String unlockedColor = "#B99C6B";
    static String lockedColor = "#CD5C5C";
    static String btnHoverColor = "AEDB9F";

    static String shopButtonFontColor = "#430303";

    static void menuButtonStyle(Button button, int x, int y, int width, int height, int fontSize) {
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle(
                "    -fx-background-color: #654b00;" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0,1,2,3,0;\n" +
                        "    -fx-text-fill: #e68400;\n" +
                        "    -fx-font-weight: bold;\n" +
                        "    -fx-font-size: " + fontSize + "px;\n" +
                        "    -fx-font-family: Phosphate;\n" +
                        "    -fx-padding: 10 20 10 20;");
        button.setMinSize(width, height);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                button.setStyle(
                        "    -fx-background-color: #654b00;" +
                                "    -fx-background-radius: 30;\n" +
                                "    -fx-background-insets: 0,1,2,3,0;\n" +
                                "    -fx-text-fill: " + btnHoverColor +";\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-font-size: " + fontSize + "px;\n" +
                                "    -fx-font-family: Phosphate;\n" +
                                "    -fx-padding: 10 20 10 20;");
                Globals.menuBtnHover.play();
            }
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                button.setStyle(
                        "    -fx-background-color: #654b00;" +
                                "    -fx-background-radius: 30;\n" +
                                "    -fx-background-insets: 0,1,2,3,0;\n" +
                                "    -fx-text-fill: #e68400;\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-font-size: " + fontSize + "px;\n" +
                                "    -fx-font-family: Phosphate;\n" +
                                "    -fx-padding: 10 20 10 20;");
            }
        });
    }


    static void imgButtonStyle(Button button, int x, int y, int width, int height, String imgUrl) {
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setFont(Font.font("phosphate", FontWeight.BOLD, FontPosture.REGULAR, 20));
        button.setStyle("-fx-background-image: url(" + imgUrl + ");" +
                "    -fx-text-fill: #654b00;\n");
    }

    static void shopButtonStyle(Button button, int x, int y, int width, int height, int fontSize) {
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setId("shiny-orange");
        button.setStyle(
                "    -fx-background-color: #654b00;" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0,1,2,3,0;\n" +
                        "    -fx-text-fill: " + shopButtonFontColor + ";\n" +
                        "    -fx-font-weight: bold;\n" +
                        "    -fx-font-size: " + fontSize + "px;\n" +
                        "    -fx-font-family: Phosphate;\n" +
                        "    -fx-padding: 10 20 10 20;");
        button.setMinSize(width, height);
        button.setMaxSize(width, height);

        changeColorOnClick(button, fontSize);

    }

    private static void changeColorOnClick(Button btn, int size) {
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (btn.getText().equals(inUse)) {
                            setButtonColor(btn, inUseColor, size);
                        } else if (btn.getText().equals(locked)) {
                            setButtonColor(btn, lockedColor, size);
                        } else if (btn.getText().equals(unlocked)) {
                            setButtonColor(btn, unlockedColor, size);
                        }
                    }
                });
    }

    static void setButtonColor(Button btn, String color, int size) {
        btn.setStyle(
                "    -fx-background-color:" + color + ";" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0,1,2,3,0;\n" +
                        "    -fx-text-fill: " + shopButtonFontColor + ";\n" +
                        "    -fx-font-weight: bold;\n" +
                        "    -fx-font-size: " + size + "px;\n" +
                        "    -fx-font-family: Phosphate;\n" +
                        "    -fx-padding: 10 20 10 20;");

    }

    static void addButtons(Game game, List<Button> btnList) {
        for (Button btn : btnList) {
            game.getChildren().add(btn);
        }
    }

    static void removeButtons(Game game, List<Button> btnList) {
        for (Button btn : btnList) {
            game.getChildren().remove(btn);
        }
        btnList.clear();
    }
}
