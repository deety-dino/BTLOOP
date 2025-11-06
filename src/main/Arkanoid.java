package main;

import controller.dat.dat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mng.gameManager;

import java.io.IOException;

public class Arkanoid extends Application implements dat {
    @Override
    public void start(Stage primaryStage) throws IOException {
        gameManager manager = gameManager.getInstance(primaryStage);
        manager.loadResource();
        manager.letShow(dat.loginStatus);
    }
}