package userinterface;

import dataaccess.KeyDAO;
import dataaccess.ScoreDAO;
import dataaccess.implementation.KeyDAOFileHandler;
import dataaccess.implementation.ScoreDAOFileHandler;
import entities.ingame.Ball;
import game.run.Game;
import game.util.Globals;
import game.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.List;

import static userinterface.ButtonUtil.*;
import static userinterface.TextDisplayUtil.*;

public class WindowUtil {

    public static Game currentGame;
    private static Button backToMain;
    private static Button pauseResume;
    private static Button muteUnmute;


    private static int xInc = 190;
    private static int yInc = 110;
    private static int xBase = 160;

    static KeyDAO kd = KeyDAOFileHandler.getInstance();

    public static void mainMenuUI(Game game) {
        currentGame = game;
        game.setBackground(Globals.mainBG);
        removeTexts(game);


        Button levelSelect = new Button("Level select");
        menuButtonStyle(levelSelect, 300, 100, Globals.MENU_BUTTON_WIDTH, Globals.MENU_BUTTON_HEIGHT, 30);
        ButtonUtil.menuButtonList.add(levelSelect);

        Button store = new Button("Store");
        menuButtonStyle(store, 300, 180, Globals.MENU_BUTTON_WIDTH, Globals.MENU_BUTTON_HEIGHT, 30);
        ButtonUtil.menuButtonList.add(store);

        Button highScores = new Button("High scores");
        menuButtonStyle(highScores, 300, 260, Globals.MENU_BUTTON_WIDTH, Globals.MENU_BUTTON_HEIGHT, 30);
        ButtonUtil.menuButtonList.add(highScores);

        Button info = new Button("Info");
        menuButtonStyle(info, 300, 340, Globals.MENU_BUTTON_WIDTH, Globals.MENU_BUTTON_HEIGHT, 30);
        ButtonUtil.menuButtonList.add(info);

        Button quit = new Button("Quit");
        menuButtonStyle(quit, 300, 420, Globals.MENU_BUTTON_WIDTH, Globals.MENU_BUTTON_HEIGHT, 30);
        ButtonUtil.menuButtonList.add(quit);

        addButtons(game, menuButtonList);

        levelSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removeButtons(game, menuButtonList);
                levelSelector(game);
            }
        });

        store.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removeButtons(game, menuButtonList);
                try {
                    storePageUI(game);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        highScores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removeButtons(game, menuButtonList);
                try {
                    highScorePageUI(game);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });


        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removeButtons(game, menuButtonList);
                infoPageUI(game);

            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
    }

    private static void levelSelector(Game game) {

        Button back = new Button("Back");
        menuButtonStyle(back, 150, 90, 700, 40, 20);
        game.getChildren().add(back);

        initLevelButtons(game);

        game.setBackground(Globals.paperLvlSelectBG);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removeButtons(game, selectButtonList);
                game.getChildren().remove(back);
                mainMenuUI(game);
            }
        });


        for (Button lvlBtn : ButtonUtil.selectButtonList) {
            lvlBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.setCurrentLevel(ButtonUtil.selectButtonList.indexOf(lvlBtn) + 1);
                    currentGame.start(Globals.getCurrentLevel());
                    removeButtons(game, selectButtonList);
                    game.getChildren().remove(back);
                }
            });
        }
    }


    private static void storePageUI(Game game) throws FileNotFoundException {
        game.setBackground(Globals.shopBG);

        try {
            displayKeys();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Button back = new Button("Back");
        menuButtonStyle(back, xBase, 90, 450, 40, 20);
        game.getChildren().add(back);


        for (int row = 0; row < 2; row++) {
            for (int board = 0 + row * 4; board < 4 + row * 4; board++) {

                boardButtonList.add(new Button());
                Button thisBoard = boardButtonList.get(board);

                ButtonUtil.shopButtonStyle(thisBoard, row == 0 ? xBase + board * xInc : xBase + (board - 4) * xInc, 210 + row * yInc, 110, 40, 15);
                setStoreButtonText(thisBoard, boardButtonList, kd.getUnlockedBoards(), kd.getBoardInUse());
                changeBtnColorBasedOnText(thisBoard);
            }
        }
        addButtons(game, boardButtonList);

        for (int row = 0; row < 2; row++) {
            for (int ball = 0 + row * 4; ball < 4 + row * 4; ball++) {
                ballButtonList.add(new Button());
                Button thisBall = ballButtonList.get(ball);

                ButtonUtil.shopButtonStyle(thisBall, row == 0 ? xBase + ball * xInc : xBase + (ball - 4) * xInc, 440 + row * yInc, 110, 40, 15);
                setStoreButtonText(thisBall, ballButtonList, kd.getUnlockedBalls(), kd.getBallInUse());
            }
        }
        addButtons(game, ballButtonList);


        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.getChildren().remove(back);
                removeButtons(game, boardButtonList);
                removeButtons(game, ballButtonList);
                mainMenuUI(game);
            }
        });

        for (Button btn : ButtonUtil.boardButtonList) {
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                if (isUnlocked(btn, boardButtonList, kd.getUnlockedBoards())) {
                                    setStoreButtonStatus(btn);
                                    Globals.setBoardImg("boards/board_" + boardButtonList.indexOf(btn) + ".png");
                                } else if (!isInUse(btn, boardButtonList, kd.getBoardInUse())) {
                                    try {
                                        if (canPurchase(btn, boardButtonList)) {
                                            canBuyPopup(btn, boardButtonList, kd.getUnlockedBoards(), kd.getBoardInUse());
                                        } else
                                            notAbleToBuyPopup(btn, boardButtonList);
                                    } catch (FileNotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }

        for (Button btn : ballButtonList) {
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                if (isUnlocked(btn, ballButtonList, kd.getUnlockedBalls())) {
                                    setStoreButtonStatus(btn); //
                                    Globals.setBallImg("balls/ball_" + ballButtonList.indexOf(btn) + ".png");
                                } else if (!isInUse(btn, ballButtonList, kd.getBallInUse())) {
                                    if (canPurchase(btn, ballButtonList)) {
                                        canBuyPopup(btn, ballButtonList, kd.getUnlockedBalls(), kd.getBallInUse());
                                    } else
                                        notAbleToBuyPopup(btn, ballButtonList);

                                }
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }

    public static void canBuyPopup(Button btn, List<Button> buttonList, List<Integer> indicesList, int ballOrBoardInUse) {

        Platform.runLater(() -> {
            String msg = "";
            try {
                msg = "Do you really want to purchase this item? \n" +
                        "Price: " + getPrice(btn, buttonList) + " You have: " + kd.getCount();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.NONE, msg, ButtonType.YES, ButtonType.NO);

            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    kd.decrease(getPrice(btn, buttonList));
                    displayKeys();
                    if (!indicesList.contains(buttonList.indexOf(btn)) && (isTypeOfBoardButton(btn))) {
                        kd.addUnlockedBoard(buttonList.indexOf(btn));
                        Globals.setBoardImg("boards/board_" + boardButtonList.indexOf(btn) + ".png");
                    } else if (!indicesList.contains(buttonList.indexOf(btn)) && (isTypeOfBallButton(btn))) {
                        kd.addUnlockedBall(buttonList.indexOf(btn));
                        Globals.setBallImg("balls/ball_" + ballButtonList.indexOf(btn) + ".png");
                    }

                    setStoreButtonStatus(btn);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (alert.getResult() == ButtonType.NO) {
                alert.hide();
            }
        });
    }

    public static void notAbleToBuyPopup(Button btn, List<Button> buttonList) { // REUSE REFACTOR
        Platform.runLater(() -> {
            String msg = "";
            try {
                msg = "Unfortunately you can't buy that item yet.\n" +
                        "Complete levels for earning more keys.\n" +
                        "Price: " + getPrice(btn, buttonList) + " You have: " + kd.getCount();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ButtonType agree = new ButtonType("So sad");
            Alert alert = new Alert(Alert.AlertType.NONE, msg, agree);

            alert.showAndWait();

            if (alert.getResult() == agree)
                alert.hide();
        });
    }

    private static boolean isTypeOfBoardButton(Button btn) {
        return boardButtonList.contains(btn);
    }

    private static boolean isTypeOfBallButton(Button btn) {
        return ballButtonList.contains(btn);
    }

    private static void setStoreButtonText(Button btn, List<Button> buttonList, List<Integer> indicesList, int ballOrBoardInUse) {
        if (isUnlocked(btn, buttonList, indicesList)) {
            btn.setText(unlocked);
            if (isInUse(btn, buttonList, ballOrBoardInUse))
                btn.setText(inUse);
        } else
            btn.setText(locked);
        changeBtnColorBasedOnText(btn);
    }

    private static void changeBtnColorBasedOnText(Button btn) {
        if (btn.getText().equals(inUse))
            setButtonColor(btn, inUseColor, shopButtonFont);
        else if (btn.getText().equals(unlocked))
            setButtonColor(btn, unlockedColor, shopButtonFont);
        else if (btn.getText().equals(locked))
            setButtonColor(btn, lockedColor, shopButtonFont);
    }

    private static boolean isUnlocked(Button btn, List<Button> buttonList, List<Integer> indicesList) {
        return indicesList.contains(buttonList.indexOf(btn));
    }

    private static boolean isInUse(Button btn, List<Button> buttonList, int ballOrBoardinUse) {
        return buttonList.indexOf(btn) == ballOrBoardinUse;
    }

    private static void setStoreButtonStatus(Button btn) throws FileNotFoundException {
        if (isTypeOfBoardButton(btn)) {
            boardButtonList.get(kd.getBoardInUse()).setText(unlocked);
            changeBtnColorBasedOnText(boardButtonList.get(kd.getBoardInUse()));
            kd.setBoardInUse(boardButtonList.indexOf(btn));
            btn.setText(inUse);
        }
        if (isTypeOfBallButton(btn)) {
            ballButtonList.get(kd.getBallInUse()).setText(unlocked);
            changeBtnColorBasedOnText(ballButtonList.get(kd.getBallInUse()));
            kd.setBallInUse(ballButtonList.indexOf(btn));
            btn.setText(inUse);

        }
        changeBtnColorBasedOnText(btn);

    }

    private static int getPrice(Button btn, List<Button> buttonList) {
        int result = buttonList.indexOf(btn) < 4 ? 50 : 100;
        return result;
    }

    private static boolean canPurchase(Button btn, List<Button> buttonList) throws FileNotFoundException {
        return kd.getCount() - getPrice(btn, buttonList) >= 0;
    }


    private static void highScorePageUI(Game game) throws FileNotFoundException {
        game.setBackground(Globals.paperLvlSelectBG);
        ScoreDAO scoreDAO = ScoreDAOFileHandler.getInstance();

        Button back = new Button("Back");

        menuButtonStyle(back, 120, 75, 100, 50, 25);
        game.getChildren().add(back);

        long[] hScores = scoreDAO.getFirst10();

        String scoresString = "";
        int i = 1;
        for (Long score : hScores) {
            scoresString += i + ":  " + score + "\n ";
            i++;
        }
        displayHighScores();
        scores.setText(scoresString);


        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.getChildren().remove(back);
                game.getChildren().remove(scores);
                mainMenuUI(game);
            }
        });
    }

    public static void infoPageUI(Game game) {
        game.setBackground(Globals.paperBG);

        Button back = new Button("Back");
        menuButtonStyle(back, 120, 75, 100, 50, 25);
        game.getChildren().add(back);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.getChildren().remove(back);
                mainMenuUI(game);
            }
        });
    }

    public static void ingameUI(Game game) {
        game.setBackground(Globals.ingameBG);

        backToMain = new Button("Back to menu");
        menuButtonStyle(backToMain, 800, 25, 120, 20, 15);
        game.getChildren().add(backToMain);

        pauseResume = new Button("Pause");
        menuButtonStyle(pauseResume, 650, 25, 30, 20, 15);
        game.getChildren().add(pauseResume);

        muteUnmute = new Button("Mute");
        menuButtonStyle(muteUnmute, 500, 25, 60, 20, 15);
        game.getChildren().add(muteUnmute);


        displayLives.setText("Lives left: ");
        displayLives();
        displayScore();

        backToMain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Globals.removeAllGameObjects();
                game.getChildren().remove(backToMain);
                game.getChildren().remove(pauseResume);
                game.getChildren().remove(muteUnmute);
                removeTexts(game);
                Ball.resetScore();
                Globals.gameLoop.stop();
                mainMenuUI(game);
            }
        });

        pauseResume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (pauseResume.getText().equals("Pause")) {
                    pauseResume.setText("Resume");
                    Globals.gameLoop.stop();
                } else {
                    pauseResume.setText("Pause");
                    Globals.gameLoop.start();
                }
            }
        });

        muteUnmute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (Globals.themeMusic.isPlaying()) {
                    Globals.themeMusic.stop();
                    muteUnmute.setText("Unmute");
                } else {
                    Globals.themeMusic.play();
                    String imgUrl = "ingame/mute.png";
                    muteUnmute.setText("Mute");
                }

            }
        });

    }

    public static void winPopup() {
        Globals.win.play();
        int numOfKeysEarned = Util.getRandomIntInRange(1, Math.max(Globals.getCurrentLevel(), 2));


        Platform.runLater(() -> {
            afterGameClosings();

            ButtonType backToMain = new ButtonType("Back to main");
            String message = null;
            try {
                kd.increase(numOfKeysEarned);
                message = "Level completed! \n Would you like to go back to menu, or go to next level?\n" +
                        "You earned " + numOfKeysEarned + " key(s)." +
                        "You have " + kd.getCount() + " keys. Visit the store for items! ";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.NONE, message, backToMain, ButtonType.NEXT);
            alert.setTitle("Level completed");
            alert.showAndWait();

            if (alert.getResult() == backToMain) {
                mainMenuUI(currentGame);

            } else if (alert.getResult() == ButtonType.NEXT) {
                Globals.setCurrentLevel(Globals.getCurrentLevel() + 1);
                currentGame.start(Globals.getCurrentLevel());
            }

            Globals.removeAllGameObjects();
        });
    }

    public static void losePopup() {
        ScoreDAO score = ScoreDAOFileHandler.getInstance();


        Platform.runLater(() -> {
            afterGameClosings();
            Ball.resetScore();

            String currentScoreText = "";
            try {
                currentScoreText = getScoreString(score);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            score.add(Globals.getCurrentScore());

            ButtonType backToMain = new ButtonType("Back to main");
            ButtonType tryAgain = new ButtonType("Try again");
            String message = "Level failed! \n" + currentScoreText + "\n" +
                    "Would you like to go back to menu, or try again?";
            Alert alert = new Alert(Alert.AlertType.NONE, message, backToMain, tryAgain);
            alert.setTitle("Game over");
            alert.showAndWait();
            Globals.removeAllGameObjects();
            if (alert.getResult() == backToMain) {
                mainMenuUI(currentGame);
                try {
                    score.getFirst10();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (alert.getResult() == tryAgain) {
                currentGame.start(Globals.getCurrentLevel());
            }
        });
    }

    private static void afterGameClosings() {
        currentGame.getChildren().remove(backToMain);
        currentGame.getChildren().remove(pauseResume);
        currentGame.getChildren().remove(muteUnmute);
        removeTexts(currentGame);
        Globals.gameLoop.stop();
        Globals.rightKeyDown = false;
        Globals.leftKeyDown = false;
    }

    private static String getScoreString(ScoreDAO score) throws FileNotFoundException {
        return score.getHighest() <= Globals.getCurrentScore() ? "NEW HIGH SCORE: " + Globals.getCurrentScore() : "YOUR SCORE: " + Globals.getCurrentScore();
    }

    private static void initLevelButtons(Game game) {

        int startingX = 150;
        int startingY = 150;
        int horizontalDist = 150;
        int verticalDist = 80;

        for (int row = 0; row < 6; row++) {
            for (int lvlBtn = 0 + row * 5; lvlBtn < 5 + row * 5; lvlBtn++) {
                selectButtonList.add(new Button());
                int imgNumber = lvlBtn + 1;
                imgButtonStyle(selectButtonList.get(lvlBtn), startingX + (lvlBtn - row * 5) * horizontalDist, startingY + row * verticalDist, 100, 60, "levels/lvl_" + imgNumber + ".png");
                selectButtonList.get(lvlBtn).setText("Level " + imgNumber);
            }
        }
        addButtons(game, selectButtonList);

    }


}
