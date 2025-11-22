package main;

import javafx.application.Application;
import javafx.stage.Stage;
import mng.GameEngine;

public class Arkanoid extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameEngine.getInstance(primaryStage);
        GameEngine.letShow();
    }
}
