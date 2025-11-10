package mng;

import controller.dat.IngameData;
import controller.dat.dat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Arkanoid;
import user.User;

import java.io.IOException;
import java.net.URL;

public class gameManager implements dat {
    private static gameManager gameManager;
    private static FXMLLoader[] fxmlLoader = new FXMLLoader[3];
    private static Scene[] scene = new Scene[3];
    private static Scene primaryScene;
    private static Stage stage;
    private static User user;
    private static IngameData currentGameData;

    @FXML
    protected void initialize() {
        // Game loop đã được chuyển sang Play controller
        // Không cần loop ở đây nữa
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

    public void loadResource() throws Exception {
        String[] paths = {
            "/controller/fxml/login.fxml",
            "/controller/fxml/levelSelection.fxml",
            "/controller/fxml/play.fxml"
        };

        for (int i = 0; i < 3; i++) {
            fxmlLoader[i] = new FXMLLoader(Arkanoid.class.getResource(paths[i]));
            scene[i] = new Scene(fxmlLoader[i].load(), 800, 600);
        }
    }

    public static void letShow(int status) throws Exception {
        primaryScene = scene[status];
        stage.setScene(primaryScene);
        stage.show();
    }

    public IngameData getCurrentGameData() {
        return currentGameData;
    }
}