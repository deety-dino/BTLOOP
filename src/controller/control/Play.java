package controller.control;

import controller.dat.IngameData;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mng.gameManager;
import user.User;

import java.io.IOException;

public class Play {
    private AnimationTimer gameLoop;
    private long cur;
    private IngameData data;
    private User user;
    @FXML
    protected Pane root;
    @FXML
    protected Group buttonGroup, gamePlay, pane, winPane;
    @FXML
    protected Text WIDEPADDLE_POWERUP_TIME, LASER_POWERUP_TIME, SPEED_POWERUP_TIME;
    @FXML
    protected Text scoreText, livesText, highScoreText;
    @FXML
    protected Text winScoreText, winHighScoreText, winComparisonText;

    @FXML
    protected void clickPause() {
        data.setPause();
    }

    @FXML
    protected void clickToHome() throws IOException {
        gameManager.State = gameManager.ApplicationState.LEVEL_SELECTION_SCREEN;
        gameManager.letShow();
    }

    @FXML
    protected void clickRestart() {
        pane.setVisible(false);
        winPane.setVisible(false);
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
                if (user == null) {
                    user = User.getInstance();
                } else if (user.isSelected()) {
                    data.loadData(user.getSelectedLevel());
                    data.getGroup();
                    pane.setVisible(false);
                    gamePlay.setVisible(true);
                    User.setSelected(false);
                } else {
                    double time = (l - cur) / 1000000000.0;
                    update(time);
                }
                cur = l;
            }
        };
        gameLoop.start();
    }

    private void update(double time) {
        if (!data.isRunning()) {
            if (data.isWon()) {
                winPane.setVisible(true);
                pane.setVisible(false);
                updateWinScreen();
            } else {
                pane.setVisible(true);
                winPane.setVisible(false);
            }
            gamePlay.setVisible(false);
        } else if (!data.isPause()) {
            root.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.LEFT) {
                    data.setLeftPressed(false);
                }
                if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(false);
                if (e.getCode() == KeyCode.SPACE) data.setSpacePressed(false);
            });
            root.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.LEFT) data.setLeftPressed(true);
                if (e.getCode() == KeyCode.RIGHT) data.setRightPressed(true);
                if (e.getCode() == KeyCode.SPACE) data.setSpacePressed(true);
                if (e.getCode() == KeyCode.K) {
                    data.setPause();
                }
            });
            root.setFocusTraversable(true);
            Platform.runLater(() -> root.requestFocus());
            data.update(time);
            data.getText(LASER_POWERUP_TIME, WIDEPADDLE_POWERUP_TIME, SPEED_POWERUP_TIME);

            // Cập nhật hiển thị điểm và mạng
            updateScoreDisplay();
        }
    }

    private void updateScoreDisplay() {
        if (scoreText != null) {
            scoreText.setText("Score: " + data.getScore());
        }
        if (livesText != null) {
            livesText.setText("Lives: " + data.getLives());
        }
        if (highScoreText != null) {
            highScoreText.setText("High Score: " + data.getHighScore());
        }
    }

    private void updateWinScreen() {
        int score = data.getScore();
        int highScore = data.getHighScore();

        if (winScoreText != null) {
            winScoreText.setText("Your Score: " + score);
        }
        if (winHighScoreText != null) {
            winHighScoreText.setText("High Score: " + highScore);
        }
        if (winComparisonText != null) {
            if (score >= highScore) {
                winComparisonText.setText("🏆 NEW HIGH SCORE! 🏆");
                winComparisonText.setFill(javafx.scene.paint.Color.GOLD);
            } else {
                int difference = highScore - score;
                winComparisonText.setText("+" + difference + " points to beat high score!");
                winComparisonText.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            }
        }
    }
}
