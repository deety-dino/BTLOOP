package controller.control;

import controller.dat.IngameData;
import controller.dat.dat;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class controller implements dat {
    private int status;
    private AnimationTimer gameLoop;

    IngameData ingame;

    private IngameData ingameData;
    @FXML
    protected Pane root;

    @FXML
    protected Button hello_playButton,
            level_playButton,
            level_homeButton,
            ingame_playButton,
            ingame_pauseButton;

    @FXML
    protected Group helloView, levelSelectionView, inGameView;

    @FXML
    protected void initialize() {
        ingameData = new IngameData(inGameView);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        gameLoop.start();
    }

    @FXML
    protected void toHome() {
        status = homeStatus;
    }

    @FXML
    protected void toLevelSelection() {
        status = levelStatus;
    }

    @FXML
    protected void toGame() {

        ingameData.loadData(1);
        ingameData.getGroup();
        status = ingameStatus;
    }

    @FXML
    protected void pauseGame() {
        ingameData.setPause();
    }

    public void update() {
        switch (status) {
            case homeStatus -> {
                helloView.setVisible(true);
                levelSelectionView.setVisible(false);
                inGameView.setVisible(false);
            }
            case levelStatus -> {
                helloView.setVisible(false);
                levelSelectionView.setVisible(true);
                inGameView.setVisible(false);
            }
            case ingameStatus -> {
                if (!ingameData.isRunning()) {
                    this.status = levelStatus;
                } else if (!ingameData.isPause()) {
                    root.setOnKeyReleased(e -> {
                        if (e.getCode() == KeyCode.LEFT) {
                            ingameData.setLeftPressed(false);
                        }
                        if (e.getCode() == KeyCode.RIGHT) ingameData.setRightPressed(false);
                    });
                    root.setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.LEFT) ingameData.setLeftPressed(true);
                        if (e.getCode() == KeyCode.RIGHT) ingameData.setRightPressed(true);
                        if (e.getCode() == KeyCode.K) {
                            ingameData.setPause();
                        }
                    });
                    root.setFocusTraversable(true);
                    Platform.runLater(() -> root.requestFocus());
                    ingameData.update();
                }
                helloView.setVisible(false);
                levelSelectionView.setVisible(false);
                inGameView.setVisible(true);
            }
            default -> {
                gameLoop.stop();
            }
        }
    }


}
