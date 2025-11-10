package controller.control;

import controller.dat.IngameData;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import mng.gameManager;

public class Play {
    @FXML
    private Group gamePlay;
    @FXML
    private Group buttonGroup;
    @FXML
    private Group pane;
    @FXML
    private Pane root;

    private IngameData data;
    private Scene scene;
    private static int level = 1;
    private AnimationTimer gameLoop;

    public Group getGamePlayGroup() {
        return gamePlay;
    }

    @FXML
    protected void initialize() {
        data = new IngameData(gamePlay);
        data.loadData(level);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (data != null) {
                    data.update();

                    if (!data.isRunning() && !data.isPause()) {
                        data.setPause();
                        pane.setVisible(true);
                    }
                }
            }
        };
        gameLoop.start();

        root.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null && scene != newScene) {
                scene = newScene;
                setupKeyHandlers();
                root.requestFocus();
            }
        });

        root.setFocusTraversable(true);
    }

    public IngameData getData() {
        return data;
    }

    private void setupKeyHandlers() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, this::handleKeyRelease);
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case SPACE -> {
                data.handleKeyPress(KeyCode.SPACE);
                event.consume();
            }
            case LEFT -> {
                data.handleKeyPress(KeyCode.LEFT);
                event.consume();
            }
            case RIGHT -> {
                data.handleKeyPress(KeyCode.RIGHT);
                event.consume();
            }
            case ESCAPE -> {
                clickPause();
                event.consume();
            }
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case LEFT -> {
                data.handleKeyRelease(KeyCode.LEFT);
                event.consume();
            }
            case RIGHT -> {
                data.handleKeyRelease(KeyCode.RIGHT);
                event.consume();
            }
        }
    }

    @FXML
    protected void clickToHome() {
        try {
            gameManager.letShow(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void clickPause() {
        data.setPause();
        if (data.isPause()) {
            pane.setVisible(true);
        } else {
            pane.setVisible(false);
        }
    }

    @FXML
    protected void clickNext() {
        pane.setVisible(false);
        level++;
        data.loadData(level);
        data.setRunning();
        if (data.isPause()) {
            data.setPause();
        }
        root.requestFocus();
    }

    @FXML
    protected void clickRestart() {
        pane.setVisible(false);
        data.loadData(level);
        data.setRunning();
        if (data.isPause()) {
            data.setPause();
        }
        root.requestFocus();
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
