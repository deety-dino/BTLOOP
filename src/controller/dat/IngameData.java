package controller.dat;

import java.util.ArrayList;
import java.util.Iterator;

import gameobjects.*;  // Updated import to include PowerUp
import javafx.scene.Group;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

// Removed unused import

public class IngameData implements dat {
    private static IngameData instance;
    private final Group root;
    private boolean isPause;
    private boolean isRunning;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Brick> bricks = new ArrayList<>();
    ArrayList<PowerUp> powerUps = new ArrayList<>();

    Paddle paddle;

    //Constructor
    public IngameData(Group root) {
        this.root = root;
        isPause = false;
        isRunning = true;
        paddle = new Paddle(350, 550, 100, 15);
        instance = this;
    }

    public static IngameData getInstance() {
        return instance;
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
        root.getChildren().add(powerUp.getNode());
    }

    // Allow power-ups to access the paddle
    public Paddle getPaddle() {
        return paddle;
    }

    // Add a ball to the game and the scene
    public void addBall(Ball ball) {
        if (ball == null) return;
        balls.add(ball);
        root.getChildren().add(ball.getNode());
    }

    // Allow power-ups to inspect/modify current balls
    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void loadData(int level) {
        isPause = false;
        isRunning = true;
        switch (level) {
            case 1:
                level1();
                break;
            default:
                System.out.println("Coming soon...");
        }
    }

    //level Pane
    private void level1() {
        balls.clear();
        bricks.clear();
        balls.add(new Ball(400, 100, 8));
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 50;
        double offsetX = 35;
        double offsetY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        offsetX + col * (brickWidth + 5),
                        offsetY + row * (brickHeight + 5),
                        brickWidth, brickHeight);
                bricks.add(brick);
            }
        }
    }

    //update
    private void updatePaddle() {
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight();
        }
    }

    private void updatePowerUps() {
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp powerUp = it.next();
            powerUp.update();

            // Check if power-up is off screen
            if (powerUp.getY() > height + 50) {  // Give some margin
                it.remove();
                root.getChildren().remove(powerUp.getNode());
                continue;
            }

            // Enhanced collision detection with paddle
            if (powerUp.intersects(paddle) && !powerUp.isActive()) {
                // Add visual/sound feedback before activation
                javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow(0.8);
                powerUp.getNode().setEffect(glow);

                // Small animation before removal
                javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(
                    Duration.millis(200), powerUp.getNode());
                st.setByX(0.3);
                st.setByY(0.3);
                st.setOnFinished(e -> {
                    // Get first ball for backwards compatibility
                    Ball firstBall = balls.isEmpty() ? null : balls.get(0);
                    powerUp.activate(paddle, firstBall);
                    it.remove();
                    root.getChildren().remove(powerUp.getNode());
                });
                st.play();
            }
        }
    }

    private void updateBall() {
        if (balls.isEmpty()) {
            isRunning = false;
        } else {
            Iterator<Ball> ballIt = balls.iterator();
            while (ballIt.hasNext()) {
                Ball ball = ballIt.next();

                if (ball.getY() > height) {
                    root.getChildren().remove(ball.getNode());
                    ballIt.remove();
                    continue;
                }

                // Brick collision with enhanced effects
                Iterator<Brick> brickIt = bricks.iterator();
                while (brickIt.hasNext()) {
                    Brick brick = brickIt.next();
                    if (ball.intersects(brick)) {
                        ball.bounce(brick);
                        brick.hit();

                        if (brick.isDestroyed()) {
                            brick.onDestroyed();
                            if (brick instanceof PowerUpBrick) {
                                PowerUp powerUp = ((PowerUpBrick) brick).getPowerUp();
                                if (powerUp != null) {
                                    powerUps.add(powerUp);
                                    root.getChildren().add(powerUp.getNode());
                                }
                            }
                            brickIt.remove();
                            root.getChildren().remove(brick.getNode());
                        }
                    }
                }

                // Paddle collision with improved bounce angles
                if (ball.intersects(paddle)) {
                    ball.bounce(paddle);
                    // Add subtle visual feedback
                    javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow(0.3);
                    paddle.getNode().setEffect(glow);
                    PauseTransition pt = new PauseTransition(Duration.millis(100));
                    pt.setOnFinished(e -> paddle.getNode().setEffect(null));
                    pt.play();
                }

                ball.update();
            }
        }
    }

    private void checkGameState() {
        if (!isRunning) return;

        // Kiểm tra thua (không còn bóng)
        if (balls.isEmpty()) {
            isRunning = false;
            showGameOver("Game Over", "Bạn đã thua!");
            return;
        }

        // Kiểm tra thắng (phá hết gạch)
        if (bricks.isEmpty()) {
            isRunning = false;
            showGameOver("Victory!", "Chúc mừng! Bạn đã chiến thắng!");
        }
    }

    private void showGameOver(String title, String content) {
        // Hiển thị thông báo trên UI thread
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.show();
        });
    }

    public void update() {
        if (!isRunning || isPause) return;

        updatePaddle();
        updateBall();
        updatePowerUps();
        checkGameState();
    }

    //Getter and setter
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void getGroup() {
        root.getChildren().clear();
        root.getChildren().add(paddle.getNode());
        for (Brick brick : bricks) {
            root.getChildren().add(brick.getNode());
        }
        for (Ball ball : balls) {
            root.getChildren().add(ball.getNode());
        }
        for (PowerUp powerUp : powerUps) {
            root.getChildren().add(powerUp.getNode());
        }
    }

    public void setPause() {
        isPause = !isPause;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning() {
        isRunning = true;
    }
}
