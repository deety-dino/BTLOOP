package controller.resources;

import javafx.fxml.FXMLLoader;
import main.Arkanoid;
import mng.SceneManager;

import java.io.IOException;
import java.util.HashMap;

public class FXMLManager implements SceneManager {
    private static FXMLManager instance;
    private final HashMap<SceneManager.SceneState, FXMLLoader> fxml;

    private FXMLManager() {
        fxml = new HashMap<>();
        try {
            fxml.putIfAbsent(SceneState.LOGIN_SCENE, loadResources("/login.fxml"));
            fxml.putIfAbsent(SceneState.LEVEL_SELECETION_SCENE, loadResources("/levelSelection.fxml"));
            fxml.putIfAbsent(SceneState.GAME_SCENE, loadResources("/play.fxml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static FXMLManager getInstance() {
        if (instance == null) {
            instance = new FXMLManager();
        }
        return instance;
    }

    private FXMLLoader loadResources(String filePath) throws  IOException {
        return new FXMLLoader(Arkanoid.class.getResource(filePath));
    }

    @Override
    public HashMap<SceneState, FXMLLoader> getHashMap() {
        return fxml;
    }
}
