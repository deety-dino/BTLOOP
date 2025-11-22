package mng;

import controller.resources.GFXManager;
import controller.resources.SFXManager;
import javafx.stage.Stage;
import user.UserManager;


public class GameEngine {
    private static final Object lock = new Object();
    public static SceneManager.SceneState sceneState;
    private static volatile GameEngine GameEngine;
    private static Stage stage;



    public static GameEngine getInstance(Stage stage) {
        if (GameEngine == null) {
            synchronized (lock) {
                if (GameEngine == null) {
                    GameEngine = new GameEngine(stage);
                }
            }
        }
        return GameEngine;
    }

    private GameEngine(Stage stage) {
        sceneState = SceneManager.SceneState.LOGIN_SCENE;
        mng.GameEngine.stage = stage;
        loadResource();
    }

    private void loadResource() {
        UserManager.getInstance();
        SceneController.getInstance();
        SoundManager.getInstance();
        GFXManager.getInstance();
        System.out.println("Resources all be done!");
    }

    public static void letShow() {
        stage.setScene(SceneController.getInstance().getHashMap().get(sceneState));
        SoundManager.getInstance().playTheme();
        stage.show();
    }

}
