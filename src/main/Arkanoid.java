package main;

import controller.dat.dat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Arkanoid extends Application implements dat {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Arkanoid.class.getResource("/controller/fxml/Ingame-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Simple Arkanoid Game");
        stage.setScene(scene);
        stage.show();
    }
}
