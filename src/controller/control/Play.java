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

public class Play {

    private ScheduledFuture<?> gameTicker;

    private long cur;
    private IngameData data;
    private User user;
    @FXML
    protected Pane root;
    @FXML
    protected Group buttonGroup, gamePlay, pane, statsGroup;
    @FXML
    protected Text WIDEPADDLE_POWERUP_TIME, LASER_POWERUP_TIME, SPEED_POWERUP_TIME;
    @FXML
    protected Text LIVES_TEXT, SCORE_TEXT, HIGHSCORE_TEXT;

    @FXML
    protected void clickPause() {
        data.setPause();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickToHome() throws IOException {
        gameManager.State = gameManager.ApplicationState.LEVEL_SELECTION_SCREEN;
        gameManager.letShow();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickRestart() {
        pane.setVisible(false);
        gamePlay.setVisible(true);
        data.loadData(user.getSelectedLevel());
        data.getGroup();
        cur = 0;
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void clickNext() {
        cur = 0;
        User.setSelectedLevel(user.getSelectedLevel() + 1);
        data.loadData(user.getSelectedLevel());
        data.getGroup();
        Platform.runLater(() -> root.requestFocus());
    }

    @FXML
    protected void initialize() {
        data = new IngameData(gamePlay);

        Platform.runLater(() -> root.requestFocus());
        root.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(false);
            if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(false);
            if (e.getCode() == KeyCode.SPACE) data.setSpacePressed(false);
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(true);
            if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(true);
            if (e.getCode() == KeyCode.SPACE) {
                if (!data.isRunning() || data.isPause()) {
                    data.resume();
                } else {
                    data.setSpacePressed(true);
                }
            }
            if (e.getCode() == KeyCode.P) data.setPause();
        });

        ThreadPoolManager tpm = ThreadPoolManager.getInstance();
        gameTicker = tpm.scheduleAtFixedRate(() -> {
            long now = System.nanoTime();
            try {
                if (user == null) {
                    user = User.getInstance();
                } else if (user.isSelected()) {
                    // Load level data in background; then sync scene on FX thread
                    int levelToLoad = user.getSelectedLevel();
                    tpm.submit(() -> {
                        cur = System.nanoTime();
                        Platform.runLater(() -> {
                            data.loadData(levelToLoad);
                            data.getGroup();
                            pane.setVisible(false);
                            gamePlay.setVisible(true);
                        });
                    });
                    User.setSelected(false);
                } else {
                    double deltaSeconds;
                    if (cur == 0) {
                        deltaSeconds = 16.0 / 1000.0;
                    } else {
                        deltaSeconds = (now - cur) / 1000000000.0;
                    }
                    cur = now;
                    if (!data.isRunning()) {
                        // show pause/end UI on FX thread
                        Platform.runLater(() -> {
                            pane.setVisible(true);
                            gamePlay.setVisible(false);
                        });
                    } else if (!data.isPause()) {
                        pane.setVisible(false);
                        gamePlay.setVisible(true);
                        // Perform update (model work) off FX thread, passing a delta time
                        Platform.runLater(() -> data.update(deltaSeconds));
                        Platform.runLater(() -> {
                            data.getGroup();
                            data.getText(LASER_POWERUP_TIME, WIDEPADDLE_POWERUP_TIME, SPEED_POWERUP_TIME);
                            LIVES_TEXT.setText(String.valueOf(data.getLives()));
                            SCORE_TEXT.setText(String.valueOf(data.getScore()));
                            SCORE_TEXT.setFill(javafx.scene.paint.Color.RED);
                            HIGHSCORE_TEXT.setText(String.valueOf(data.getHighScore()));
                            HIGHSCORE_TEXT.setFill(javafx.scene.paint.Color.RED);
                        });
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, 0, 16, TimeUnit.MILLISECONDS);
    }
}