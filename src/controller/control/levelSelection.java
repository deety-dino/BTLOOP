package controller.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import mng.SceneManager;
import mng.GameEngine;
import user.User;
import user.UserManager;

public class levelSelection {
    @FXML
    protected Pane root;

    @FXML
    protected void logout() {
        UserManager.getInstance().logout();
        GameEngine.sceneState = SceneManager.SceneState.LOGIN_SCENE;
        GameEngine.letShow();
    }

    @FXML
    protected void loadLevel1() {
        UserManager.getInstance().getUser().setSelectedLevel(1);
        UserManager.getInstance().getUser().setSelected(true);
        GameEngine.sceneState = SceneManager.SceneState.GAME_SCENE;
        GameEngine.letShow();
    }

    @FXML
    protected void loadLevel2() {
        UserManager.getInstance().getUser().setSelectedLevel(2);
        UserManager.getInstance().getUser().setSelected(true);
        GameEngine.sceneState = SceneManager.SceneState.GAME_SCENE;
        GameEngine.letShow();
    }

    @FXML
    protected void loadLevel3() {
        UserManager.getInstance().getUser().setSelectedLevel(3);
        UserManager.getInstance().getUser().setSelected(true);
        GameEngine.sceneState = SceneManager.SceneState.GAME_SCENE;
        GameEngine.letShow();
    }
}
