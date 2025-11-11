package main;

import javafx.application.Application;
import javafx.stage.Stage;
import mng.gameManager;

import java.io.IOException;

public class Arkanoid extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        gameManager manager = gameManager.getInstance(primaryStage);
        manager.loadResource();
        manager.letShow();
    }
}
