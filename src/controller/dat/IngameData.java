package controller.dat;

import java.util.ArrayList;
import java.util.Iterator;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Brick.BrickFactory;
import gameobjects.Brick.PowerUpBrick;
import gameobjects.Controller.powerUpController;
import gameobjects.paddle.Paddle;
import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.animation.PauseTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mng.gameInfo;

// Removed unused import

public class IngameData  {
    private final Group root;
    private boolean isPause;
    private boolean isRunning;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Brick> bricks = new ArrayList<>();
    powerUpController powerUps;

    Paddle paddle;

    //Constructor
    public IngameData(Group root) {
        this.root = root;
        isPause = false;
        isRunning = true;
        paddle = new Paddle(350, 550, 100, 15);
        powerUps = powerUpController.getInstance();
    }


    public void addPowerUp(PowerUp powerUp) {
        powerUps.addPowerUp(powerUp, root);
    }

    public void getText(Text laser, Text wide, Text speed) {
        powerUps.getText(laser, wide, speed);
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
        balls.clear();
        bricks.clear();
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

    private void updateBall() {
        if (balls.isEmpty()) {
            isRunning = false;
        } else {
            Iterator<Ball> ballIt = balls.iterator();
            while (ballIt.hasNext()) {
                Ball ball = ballIt.next();

                if (ball.getY() > gameInfo.height) {
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
                                    powerUps.addPowerUp(powerUp, root);
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

    public void update(double time) {
        updatePaddle();
        updateBall();
        powerUps.update(root, time, paddle, balls);
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
