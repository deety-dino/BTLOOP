package controller.control;

import controller.dat.IngameData;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;


import javafx.application.Platform;

public class IngameController {

    @FXML
    private AnchorPane root;
    private IngameData data ;
    private AnimationTimer gameLoop;
    private boolean pause = false;
    /**
     * Initialize game data.
     */
    @FXML
    public void initialize() {
        data = new IngameData(root);
        data.loadData(1);
        data.getRoot();
        root.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                data.setLeftPressed(false);
            }
            if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(false);
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(true);
            if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(true);
            if (e.getCode() == KeyCode.K) {pause = !pause;}
        });

        root.setFocusTraversable(true);
        Platform.runLater(() -> root.requestFocus());

        //Game Loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        // Handle paddle movement
        if(!pause)data.update();

        //Lose game
        if (!data.isContinue()) {
            gameLoop.stop();
            System.out.println("Game Over");
        }


    }


}
