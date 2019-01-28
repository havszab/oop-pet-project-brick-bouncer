package game.run;

import game.util.Globals;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.WindowUtil;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Game game = new Game();
        primaryStage.setTitle("Ultimate havszab pong");
        primaryStage.setScene(new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT));
        primaryStage.show();
        //Globals.themeMusic.play();
        WindowUtil.mainMenuUI(game);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
