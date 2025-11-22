package controller.control;

import controller.dat.GameData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mng.SceneManager;
import mng.ThreadPoolManager;
import mng.GameEngine;
import user.User;
import user.UserManager;

import java.util.concurrent.TimeUnit;

public class Play {
    private GameData gameData;
    private long cur;
    @FXML
    protected Pane root;
    @FXML
    protected Group GAME_STAT, gamePlay, GAME_OVER, PAUSE, VICTORY;
    @FXML
    protected Text WIDEPADDLE_POWERUP_TIME, LASER_POWERUP_TIME, SPEED_POWERUP_TIME;
    @FXML
    protected Text SCORE_TEXT, HIGHSCORE_TEXT, HEART_TEXT;

    @FXML
    protected void clickPause() {
        if (gameData.getStatus().isState_PAUSE())  {
            gameData.getStatus().setState_PLAYING();
        } else {
            gameData.getStatus().setState_PAUSE();
        }
        gameData.getStatus().setPause();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickToHome() {
        gameData.refesh();
        GameEngine.sceneState = SceneManager.SceneState.LEVEL_SELECETION_SCENE;
        GameEngine.letShow();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickRestart() {
        gameData.getStatus().setState_PLAYING();
        gameData.loadData(UserManager.getInstance().getUser().getSelectedLevel());
        cur = 0;
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickNext() {
        UserManager.getInstance().getUser().setSelectedLevel(UserManager.getInstance().getUser().getSelectedLevel() + 1);
        if (UserManager.getInstance().getUser().getSelectedLevel() <= 3) {
            gameData.getStatus().setState_PLAYING();
            gameData.loadData(UserManager.getInstance().getUser().getSelectedLevel());
            Platform.runLater(() -> root.requestFocus());
        }
    }

    @FXML
    protected void clickContinue() {
        gameData.getStatus().setState_PLAYING();
        gameData.getStatus().setPause();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void initialize() {
        gameData = GameData.getInstance(gamePlay);
        Platform.runLater(() -> root.requestFocus());
        root.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) gameData.getStatus().setLeftPressed(false);
            if (e.getCode() == KeyCode.RIGHT) gameData.getStatus().setRightPressed(false);
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) gameData.getStatus().setLeftPressed(true);
            if (e.getCode() == KeyCode.RIGHT) gameData.getStatus().setRightPressed(true);
        });

        ThreadPoolManager tpm = ThreadPoolManager.getInstance();
        // Load level data in background; then sync scene on FX thread
        tpm.scheduleAtFixedRate(() -> {
            long now = System.nanoTime();
            if(GameEngine.sceneState == SceneManager.SceneState.GAME_SCENE){
                gamePlay.setVisible(gameData.getStatus().isState_PLAYING());
                GAME_STAT.setVisible(gameData.getStatus().isState_PLAYING());
                GAME_OVER.setVisible(gameData.getStatus().isState_GAME_OVER());
                PAUSE.setVisible(gameData.getStatus().isState_PAUSE());
                VICTORY.setVisible(gameData.getStatus().isState_VICTORY());
                if (UserManager.getInstance().getUser() != null) {
                    if (UserManager.getInstance().getUser().isSelected()) {
                        // Load level data in background; then sync scene on FX thread
                        Platform.runLater(() -> {
                            gameData.loadData(UserManager.getInstance().getUser().getSelectedLevel());
                            gameData.getStatus().setState_PLAYING();
                        });
                        UserManager.getInstance().getUser().setSelected(false);
                    } else {
                        double deltaSeconds;
                        if (cur == 0) {
                            deltaSeconds = 16.0 / 1000.0;
                        } else {
                            deltaSeconds = (now - cur) / 1000000000.0;
                        }
                        synchronized (this){
                            Platform.runLater(() -> gameData.update(deltaSeconds));
                        }
                        synchronized (this) {
                            Platform.runLater(() -> {
                                gameData.getGroup();
                                gameData.getPowerUpText(LASER_POWERUP_TIME, WIDEPADDLE_POWERUP_TIME, SPEED_POWERUP_TIME);
                                gameData.getStatText(SCORE_TEXT, HIGHSCORE_TEXT, HEART_TEXT);
                            });
                        }
                    }
                }
            }
            cur = now;
        }, 0, 16, TimeUnit.MILLISECONDS);
    }
}