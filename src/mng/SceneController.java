package mng;

import controller.resources.FXMLManager;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class SceneController implements SceneManager{
    private static SceneController sceneController;
    private final HashMap<SceneState, Scene> scene;

    private SceneController() {
        scene =  new HashMap<>();
        FXMLManager.getInstance();
        for (SceneState sceneState : SceneState.values()) {
            try {
                scene.putIfAbsent(sceneState, new Scene(FXMLManager.getInstance().getHashMap().get(sceneState).load()));
            } catch (IOException e)  {
                e.printStackTrace();
            }
        }
    }

    public static SceneController getInstance() {
        if (sceneController == null) {
            sceneController = new SceneController();
            System.out.println("SceneController created!");
        }
        return sceneController;
    }

    @Override
    public HashMap<SceneState, Scene> getHashMap() {
        return scene;
    }
}
