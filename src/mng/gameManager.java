package mng;

import controller.control.Play;
import controller.dat.dat;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Arkanoid;
import user.User;

import java.io.IOException;

public class gameManager implements dat {
    private AnimationTimer loop;
    private static gameManager gameManager;
    private static FXMLLoader[] fxmlLoader = new FXMLLoader[3];
    private static Scene[] scene = new Scene[3];
    private static Scene primaryScene;
    private static Stage stage;
    private static User user;

    @FXML
    protected void initialize() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };
        loop.start();
    }

    public static gameManager getInstance(Stage stage) {
        if (gameManager == null) {
            gameManager = new gameManager(stage);
        }
        return gameManager;
    }

    private gameManager(Stage stage) {
        gameManager.stage = stage;
    }

    public static void loadData(int level) {

    }

    public void loadResource() throws IOException {
        try {
            fxmlLoader[0] = new FXMLLoader(Arkanoid.class.getResource("/login.fxml"));
            scene[0] = new Scene(fxmlLoader[0].load(), 800, 600);
            System.out.println("Login scene loaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load login scene: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw if you want the calling method to handle it
        }

        try {
            fxmlLoader[1] = new FXMLLoader(Arkanoid.class.getResource("/levelSelection.fxml"));
            scene[1] = new Scene(fxmlLoader[1].load(), 800, 600);
            System.out.println("Level scene loaded successfully!");
        } catch (IOException e)  {
            System.err.println("Failed to load level selection scene: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        try {
            fxmlLoader[2] = new FXMLLoader(Arkanoid.class.getResource("/play.fxml"));
            scene[2] = new Scene(fxmlLoader[2].load(), 800, 600);
            System.out.println("Play scene loaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load play scene: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void letShow(int status) throws IOException {
        primaryScene = scene[status];
        stage.setScene(primaryScene);
        stage.show();
    }
}