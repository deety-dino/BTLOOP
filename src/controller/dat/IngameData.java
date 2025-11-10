package controller.dat;

import java.util.ArrayList;
import java.util.Iterator;
import gameobjects.*;
import javafx.scene.Group;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class IngameData implements dat {
    private static IngameData instance;
    private final Group root;
    private boolean isPause;
    private boolean isRunning;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean gameStarted = false;  // Trạng thái game đã bắt đầu chưa
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Brick> bricks = new ArrayList<>();
    ArrayList<PowerUp> powerUps = new ArrayList<>();

    Paddle paddle;

    public IngameData(Group root) {
        this.root = root;
        isPause = false;
        isRunning = true;
        gameStarted = false;
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

    public Paddle getPaddle() {
        return paddle;
    }

    public void addBall(Ball ball) {
        if (ball == null) return;
        balls.add(ball);
        root.getChildren().add(ball.getNode());
    }

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

    private void level1() {
        balls.clear();
        bricks.clear();
        powerUps.clear();
        root.getChildren().clear();
        gameStarted = false;

        // Khởi tạo paddle ở giữa màn hình
        double paddleX = (width - 100) / 2;
        paddle = new Paddle(paddleX, 550, 100, 15);
        root.getChildren().add(paddle.getNode());

        // Khởi tạo bóng ở vị trí chính xác trên paddle ngay từ đầu
        double ballX = paddleX + 50;  // Giữa paddle (paddle width = 100, nên 100/2 = 50)
        double ballY = 550 - 16;  // Trên paddle (paddle Y = 550, ball radius = 8, nên 8*2 = 16)
        Ball initialBall = new Ball(ballX, ballY, 8);
        initialBall.attachToPaddle(paddle);
        balls.add(initialBall);
        root.getChildren().add(initialBall.getNode());

        // Khởi tạo các brick
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
                root.getChildren().add(brick.getNode());
            }
        }
    }

    public void handleKeyPress(KeyCode code) {
        switch (code) {
            case SPACE:
                if (!gameStarted) {
                    gameStarted = true;
                    for (Ball ball : balls) {
                        // Đặt hướng ban đầu với góc chéo ngẫu nhiên
                        double randomAngle = -60 - Math.random() * 60; // Góc từ -60 đến -120 độ
                        double radians = Math.toRadians(randomAngle);
                        ball.setDirection(Math.cos(radians), Math.sin(radians));
                        ball.launch();
                    }
                }
                break;
            case LEFT:
                leftPressed = true;
                break;
            case RIGHT:
                rightPressed = true;
                break;
        }
    }

    public void handleKeyRelease(KeyCode code) {
        switch (code) {
            case LEFT:
                leftPressed = false;
                break;
            case RIGHT:
                rightPressed = false;
                break;
        }
    }

    private void updatePaddle() {
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight();
        }
    }

    private void updatePowerUps() {
        ArrayList<PowerUp> snapshot = new ArrayList<>(powerUps);
        ArrayList<PowerUp> toRemove = new ArrayList<>();

        for (PowerUp powerUp : snapshot) {
            powerUp.update();

            if (powerUp.getY() > height + 50) {
                toRemove.add(powerUp);
                continue;
            }

            if (powerUp.intersects(paddle) && !powerUp.isActive()) {
                javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow(0.8);
                powerUp.getNode().setEffect(glow);

                javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(
                    Duration.millis(200), powerUp.getNode());
                st.setByX(0.3);
                st.setByY(0.3);
                st.setOnFinished(e -> {
                    Ball firstBall = balls.isEmpty() ? null : balls.get(0);
                    powerUp.activate(paddle, firstBall);
                    powerUps.remove(powerUp);
                    root.getChildren().remove(powerUp.getNode());
                });
                st.play();
            }
        }

        if (!toRemove.isEmpty()) {
            for (PowerUp p : toRemove) {
                powerUps.remove(p);
                root.getChildren().remove(p.getNode());
            }
        }
    }

    private void updateBall() {
        if (balls.isEmpty()) {
            isRunning = false;
            return;
        }

        if (!gameStarted) {
            for (Ball ball : balls) {
                ball.attachToPaddle(paddle);
            }
            return;
        }

        Iterator<Ball> ballIt = balls.iterator();
        while (ballIt.hasNext()) {
            Ball ball = ballIt.next();

            if (ball.getY() > height) {
                root.getChildren().remove(ball.getNode());
                ballIt.remove();
                if (balls.isEmpty()) {
                    isRunning = false;
                    showGameOver("Game Over", "Bạn đã thua!");
                }
                continue;
            }

            Iterator<Brick> brickIt = bricks.iterator();
            while (brickIt.hasNext()) {
                Brick brick = brickIt.next();
                if (ball.intersects(brick)) {
                    ball.bounce(brick);
                    brick.hit();

                    if (brick.isDestroyed()) {
                        // Nếu là PowerUpBrick, tạo PowerUp trước khi xóa brick
                        if (brick instanceof PowerUpBrick) {
                            PowerUpBrick powerUpBrick = (PowerUpBrick) brick;
                            // Gọi onDestroyed để tạo PowerUp
                            powerUpBrick.onDestroyed();
                            PowerUp powerUp = powerUpBrick.getPowerUp();
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

            if (ball.intersects(paddle)) {
                ball.bounce(paddle);
                javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow(0.3);
                paddle.getNode().setEffect(glow);
                PauseTransition pt = new PauseTransition(Duration.millis(100));
                pt.setOnFinished(e -> paddle.getNode().setEffect(null));
                pt.play();
            }

            ball.update();
        }
    }

    private void checkGameState() {
        if (!isRunning) return;

        if (balls.isEmpty()) {
            isRunning = false;
            showGameOver("Game Over", "Bạn đã thua!");
            return;
        }

        if (bricks.isEmpty()) {
            isRunning = false;
            showGameOver("Victory!", "Chúc mừng! Bạn đã chiến thắng!");
        }
    }

    private void showGameOver(String title, String content) {
        javafx.application.Platform.runLater(() -> {
            isPause = true;
        });
    }

    public void update() {
        if (!isRunning || isPause) return;

        updatePaddle();
        updateBall();
        updatePowerUps();
        checkGameState();
    }

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
