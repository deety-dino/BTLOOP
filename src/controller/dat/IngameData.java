package controller.dat;

import gameobjects.ball.Ball;
import gameobjects.brick.Brick;
import gameobjects.brick.BrickFactory;
import gameobjects.controller.BallController;
import gameobjects.controller.BrickController;
import gameobjects.controller.LaserController;
import gameobjects.controller.PaddleController;
import gameobjects.controller.PowerUpController;
import gameobjects.laser.Laser;
import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class IngameData {
    private final Object lock = new Object();
    private Group root;
    private boolean isPause;
    private boolean isRunning;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    private int lives = 3;
    private int score = 0;
    private int highScore = 0;

    BallController balls;
    BrickController bricks;
    PaddleController paddle;
    PowerUpController powerUps;
    LaserController lasers;

    public IngameData(Group root) {
        this.root = root;
        isPause = false;
        isRunning = true;
        balls = BallController.getInstance();
        bricks = BrickController.getInstance();
        paddle = PaddleController.getInstance();
        powerUps = PowerUpController.getInstance();
        lasers = LaserController.getInstance();
        balls.setGameData(this);
        lasers.setGameData(this);
    }


    public void getText(Text laser, Text wide, Text speed) {
        powerUps.getText(laser, wide, speed);
    }

    public synchronized void loadData(int level) {
        refesh();
        switch (level) {
            case 1:
                level1();
                break;
            default:
                System.out.println("Coming soon...");
        }
        getGroup();
    }

    //level Pane
    private void level1() {
        balls.addBall(new Ball(400, 100, 8));
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
                bricks.addBrick(brick);
            }
        }
    }

    private void refesh() {
        isRunning = true;
        isPause = false;
        lives = 3;
        score = 0;

        root.getChildren().removeAll(root.getChildren());
        balls.refresh();
        bricks.refresh();
        powerUps.refresh();
        paddle.refresh();
        lasers.refresh();
    }

    public void update(double time) {
        if (balls.isEmpty()) {
            lives--;
            if (lives <= 0) {
                isRunning = false;
            } else {
                balls.addBall(new Ball(400, 300, 8));
            }
        } else {
            bricks.update(time);
            paddle.update(time, leftPressed, rightPressed, spacePressed);
            balls.update(time);
            powerUps.update(time);
            lasers.update(time);
        }
    }

    public void getGroup() {
        root.getChildren().removeAll(root.getChildren());
        for (Ball ball : balls.getBalls()) {
            root.getChildren().add(ball.getNode());
        }
        for(Brick brick : bricks.getBricks()) {
            root.getChildren().add(brick.getNode());
        }
        for(PowerUp powerUp : powerUps.getPowerUps()) {
            root.getChildren().add(powerUp.getNode());
        }
        for(Laser laser : lasers.getLasers()) {
            root.getChildren().add(laser.getNode());
        }
        root.getChildren().add(paddle.getPaddle().getNode());
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

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void addScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
        }
    }

    public void setPause() {
        isPause = !isPause;
    }

    public void resume() {
        isRunning = true;
        isPause = false;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
