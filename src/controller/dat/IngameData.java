package controller.dat;

import java.util.ArrayList;
import java.util.Iterator;

import gameobjects.ball.Ball;
import gameobjects.brick.Brick;
import gameobjects.brick.BrickFactory;
import gameobjects.brick.PowerUpBrick;
import gameobjects.brick.StrongBrick;
import gameobjects.controller.powerUpController;
import gameobjects.Laser;
import gameobjects.paddle.Paddle;
import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.animation.PauseTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mng.gameInfo;


public class IngameData  {
    private final Group root;
    private boolean isPause;
    private boolean isRunning;
    private boolean isWon = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;
    private boolean ballLaunched = false;
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Brick> bricks = new ArrayList<>();
    ArrayList<Laser> lasers = new ArrayList<>();
    powerUpController powerUps;

    Paddle paddle;

    private int score = 0;
    private int lives = 5;
    private int highScore = 0;

    private static final int NORMAL_BRICK_POINTS = 10;
    private static final int STRONG_BRICK_POINTS = 20;
    private static final int POWERUP_BRICK_POINTS = 30;

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
    // Power-ups access the paddle
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
        isWon = false;
        balls.clear();
        bricks.clear();
        lasers.clear();
        ballLaunched = false;
        score = 0;
        lives = 5;
        switch (level) {
            case 1:
                level1();
                break;
            default:
                System.out.println("Coming soon...");
        }
    }

    private void level1() {
        Ball ball = new Ball(paddle.getX() + paddle.getSize().getWidth() / 2, paddle.getY() - 10, 8);
        ball.setDirection(0, 0);
        balls.add(ball);

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
            lives--;
            if (lives <= 0) {
                isRunning = false;
                updateHighScore();
            } else {
                Ball newBall = new Ball(paddle.getX() + paddle.getSize().getWidth() / 2, paddle.getY() - 10, 8);
                newBall.setDirection(0, 0);
                balls.add(newBall);
                root.getChildren().add(newBall.getNode());
                ballLaunched = false;
            }
        } else {
            Iterator<Ball> ballIt = balls.iterator();
            while (ballIt.hasNext()) {
                Ball ball = ballIt.next();

                if (ball.getY() > gameInfo.height) {
                    root.getChildren().remove(ball.getNode());
                    ballIt.remove();
                    continue;
                }

                if (!ballLaunched && ball.getDirection().getX() == 0 && ball.getDirection().getY() == 0) {
                    double newX = paddle.getX() + paddle.getSize().getWidth() / 2;
                    double newY = paddle.getY() - 10;
                    ball.getPosition().setPosition(newX, newY);
                    ((javafx.scene.shape.Circle) ball.getNode()).setCenterX(newX);
                    ((javafx.scene.shape.Circle) ball.getNode()).setCenterY(newY);
                    continue;
                }

                Iterator<Brick> brickIt = bricks.iterator();
                while (brickIt.hasNext()) {
                    Brick brick = brickIt.next();
                    if (ball.intersects(brick)) {
                        ball.bounce(brick);
                        brick.hit();

                        if (brick.isDestroyed()) {
                            brick.onDestroyed();

                            if (brick instanceof PowerUpBrick) {
                                score += POWERUP_BRICK_POINTS;
                                PowerUp powerUp = ((PowerUpBrick) brick).getPowerUp();
                                if (powerUp != null) {
                                    powerUps.addPowerUp(powerUp, root);
                                }
                            } else if (brick instanceof StrongBrick) {
                                score += STRONG_BRICK_POINTS;
                            } else {
                                score += NORMAL_BRICK_POINTS;
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
    }

    private void updateLasers() {
        if (spacePressed && paddle.hasLaser()) {
            Laser laser = paddle.shoot();
            if (laser != null) {
                lasers.add(laser);
                root.getChildren().add(laser.getNode());
            }
        }

        Iterator<Laser> laserIt = lasers.iterator();
        while (laserIt.hasNext()) {
            Laser laser = laserIt.next();
            laser.update();

            if (laser.isOffScreen()) {
                root.getChildren().remove(laser.getNode());
                laserIt.remove();
                continue;
            }

            Iterator<Brick> brickIt = bricks.iterator();
            while (brickIt.hasNext()) {
                Brick brick = brickIt.next();
                if (laser.intersects(brick)) {
                    brick.hit();

                    if (brick.isDestroyed()) {
                        brick.onDestroyed();

                        if (brick instanceof PowerUpBrick) {
                            score += POWERUP_BRICK_POINTS;
                            PowerUp powerUp = ((PowerUpBrick) brick).getPowerUp();
                            if (powerUp != null) {
                                powerUps.addPowerUp(powerUp, root);
                            }
                        } else if (brick instanceof StrongBrick) {
                            score += STRONG_BRICK_POINTS;
                        } else {
                            score += NORMAL_BRICK_POINTS;
                        }

                        brickIt.remove();
                        root.getChildren().remove(brick.getNode());
                    }

                    root.getChildren().remove(laser.getNode());
                    laserIt.remove();
                    break;
                }
            }
        }
    }

    public void update(double time) {
        updatePaddle();
        updateBall();
        updateLasers();
        powerUps.update(root, time, paddle, balls);

        if (bricks.isEmpty() && !isWon && isRunning && ballLaunched) {
            isWon = true;
            isRunning = false;
            updateHighScore();
        }

        if (spacePressed && !ballLaunched) {
            for (Ball ball : balls) {
                if (ball.getDirection().getX() == 0 && ball.getDirection().getY() == 0) {
                    double randomAngle = -0.5 + Math.random();
                    ball.setDirection(randomAngle, -1);
                    ball.getDirection().normalize(1.0);
                    ballLaunched = true;
                }
            }
        }
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
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

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getHighScore() {
        return highScore;
    }

    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }

    public boolean isWon() {
        return isWon;
    }
}
