package mng;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Arkanoid;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class gameManager {
    private static final Object lock = new Object();

    private AnimationTimer loop;
    private static gameManager gameManager;
    private static FXMLLoader[] fxmlLoader = new FXMLLoader[3];
    private static Scene[] scene = new Scene[3];
    private static Scene primaryScene;
    private static Stage stage;
    private static User user;

    public enum ApplicationState {
        LOGIN_SCREEN,
        LEVEL_SELECTION_SCREEN,
        IN_GAME_SCREEN
    };
    public static ApplicationState State;

    public static gameManager getInstance(Stage stage) {
        if (gameManager == null) {
            synchronized (lock) {
                if (gameManager == null) {
                    gameManager = new gameManager(stage);
                }
            }
        }
        return gameManager;
    }

    private gameManager(Stage stage) {
        this.State = ApplicationState.LOGIN_SCREEN;
        gameManager.stage = stage;
    }

    public void loadResource() throws IOException {

        User.initialize();
        // Helper to load a single FXML by path
        try {
            scene[0] = new Scene(gameInfo.loginFXML.load(), 800, 600);
            System.out.println("Login scene loaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load login scene: " + e.getMessage());
            throw e;
        }

        try {
            scene[1] = new Scene(gameInfo.levelFXML.load(), 800, 600);
            System.out.println("Level selection scene loaded successfully!");
        } catch (IOException e)  {
            System.err.println("Failed to load level selection scene: " + e.getMessage());
            throw e;
        }

        try {
            scene[2] = new Scene(gameInfo.playFXML.load(), 800, 600);
            System.out.println("Play scene loaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load play scene: " + e.getMessage());
            throw e;
        }
    }

    public static void letShow() throws IOException {
        switch(State) {
            case LOGIN_SCREEN ->  {
                stage.setScene(scene[0]);
                stage.show();
            } case LEVEL_SELECTION_SCREEN ->  {
                stage.setScene(scene[1]);
                stage.show();
            } case IN_GAME_SCREEN ->  {
                stage.setScene(scene[2]);
                stage.show();
            }
        }
    }
}