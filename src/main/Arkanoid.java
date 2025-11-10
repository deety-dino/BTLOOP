package main;

import controller.dat.dat;
import javafx.application.Application;
import javafx.stage.Stage;
import mng.gameManager;

public class Arkanoid extends Application implements dat {

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameManager manager = gameManager.getInstance(primaryStage);
        manager.loadResource();
        manager.letShow(loginStatus);
    }
}
