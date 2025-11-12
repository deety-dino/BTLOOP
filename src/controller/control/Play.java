package controller.control;

import controller.dat.IngameData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mng.ThreadPoolManager;
import mng.gameManager;
import user.User;

import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import mng.gameInfo.State;

public class Play {

    private ScheduledFuture<?> gameTicker;

    private long cur;
    private IngameData data;
    private User user;
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
        data.state = State.PAUSE;
        data.setPause();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickToHome() throws IOException {
        data.state = State.LOADING;
        gameManager.State = gameManager.ApplicationState.LEVEL_SELECTION_SCREEN;
        gameManager.letShow();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickRestart() {
        data.state = State.IN_GAME;
        data.loadData(user.getSelectedLevel());
        data.getGroup();
        cur = 0;
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickNext() {
        User.setSelectedLevel(user.getSelectedLevel() + 1);
        if (user.getSelectedLevel() <= 3) {
            data.state = State.IN_GAME;
            data.loadData(user.getSelectedLevel());
            data.getGroup();
            Platform.runLater(() -> root.requestFocus());
        }
    }

    @FXML
    protected void clickContinue() {
        data.state = State.IN_GAME;
    }
    @FXML
    protected void initialize() {
        data = new IngameData(gamePlay);
        data.state = State.LOADING;
        Platform.runLater(() -> root.requestFocus());
        root.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(false);
            if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(false);
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(true);
            if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(true);
            if (e.getCode() == KeyCode.K) data.setPause();
        });

        ThreadPoolManager tpm = ThreadPoolManager.getInstance();
        gameTicker = tpm.scheduleAtFixedRate(() -> {
            long now = System.nanoTime();
            //System.out.println(data.state);
            try {
                switch (data.state) {
                    case LOADING: {
                        GAME_STAT.setVisible(true);
                        gamePlay.setVisible(true);
                        PAUSE.setVisible(false);
                        VICTORY.setVisible(false);
                        GAME_OVER.setVisible(false);
                        if (user == null) {
                            user = User.getInstance();
                        } else if (user.isSelected()) {
                            // Load level data in background; then sync scene on FX thread
                            int levelToLoad = user.getSelectedLevel();
                            cur = System.nanoTime();
                            Platform.runLater(() -> {
                                System.out.println("Loading");
                                data.loadData(levelToLoad);
                                data.getGroup();
                            });
                            User.setSelected(false);
                            data.state = State.IN_GAME;
                        }
                        break;
                    }
                    case PAUSE: {
                        GAME_STAT.setVisible(false);
                        gamePlay.setVisible(false);
                        PAUSE.setVisible(true);
                        VICTORY.setVisible(false);
                        GAME_OVER.setVisible(false);
                        break;
                    }
                    case IN_GAME: {
                        GAME_STAT.setVisible(true);
                        gamePlay.setVisible(true);
                        PAUSE.setVisible(false);
                        VICTORY.setVisible(false);
                        GAME_OVER.setVisible(false);
                        double deltaSeconds;
                        if (cur == 0) {
                            deltaSeconds = 16.0 / 1000.0;
                        } else {
                            deltaSeconds = (now - cur) / 1000000000.0;
                        }
                        cur = now;
                        Platform.runLater(() -> data.update(deltaSeconds));
                        System.out.println(data.state);
                        Platform.runLater(() -> {
                            data.getGroup();
                            data.getText(LASER_POWERUP_TIME, WIDEPADDLE_POWERUP_TIME, SPEED_POWERUP_TIME);
                        });
                        break;
                    }
                    case VICTORY: {
                        Platform.runLater(() -> {
                            GAME_STAT.setVisible(false);
                            gamePlay.setVisible(false);
                            PAUSE.setVisible(false);
                            VICTORY.setVisible(true);
                            GAME_OVER.setVisible(false);
                        });
                        break;
                    }
                    case GAMEOVER: {
                        Platform.runLater(() -> {
                            GAME_STAT.setVisible(false);
                            gamePlay.setVisible(false);
                            PAUSE.setVisible(false);
                            VICTORY.setVisible(false);
                            GAME_OVER.setVisible(true);
                        });
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, 0, 16, TimeUnit.MILLISECONDS);
    }
}