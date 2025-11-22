package mng;

import java.util.HashMap;

public interface SceneManager {

    int height = 600;
    int width = 800;


    enum SceneState {
        LOGIN_SCENE,
        LEVEL_SELECETION_SCENE,
        GAME_SCENE,
    }
     HashMap<SceneState, ?> getHashMap();
}
