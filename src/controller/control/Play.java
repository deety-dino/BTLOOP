package controller.control;

import controller.dat.IngameData;
import controller.dat.dat;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import mng.gameManager;
import user.User;

import java.io.IOException;

public class Play {
    private AnimationTimer gameLoop;
    private IngameData data;
    private User user;
    @FXML
    protected Pane root;
    @FXML
    protected Group buttonGroup, gamePlay, pane;

    @FXML
    protected void clickPause() {
        data.setPause();
    }

    @FXML
    protected void clickToHome() throws IOException {
        gameManager.letShow(dat.levelStatus);
    }
    @FXML
    protected void clickRestart() {
        pane.setVisible(false);
        gamePlay.setVisible(true);
        data.loadData(user.getSelectedLevel());
        data.getGroup();
    }
    @FXML
    protected void clickNext() {
        User.setSelectedLevel(user.getSelectedLevel() + 1);
        data.loadData(user.getSelectedLevel());
        data.getGroup();
    }
    @FXML
    protected void initialize() {
        data = new IngameData(gamePlay);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(user == null) {
                    user = User.getInstance();
                } else if (user.isSelected()) {
                    data.loadData(user.getSelectedLevel());
                    data.getGroup();
                    pane.setVisible(false);
                    gamePlay.setVisible(true);
                    User.setSelected(false);
                } else {
                    update();
                }
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (!data.isRunning()) {
            pane.setVisible(true);
            gamePlay.setVisible(false);
        } else if (!data.isPause()) {
            root.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.LEFT) {
                    data.setLeftPressed(false);
                }
                if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(false);
            });
            root.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(true);
                if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(true);
                if (e.getCode() == KeyCode.K) {
                    data.setPause();
                }
            });
            root.setFocusTraversable(true);
            Platform.runLater(() -> root.requestFocus());
            data.update();
        }
    }
}
