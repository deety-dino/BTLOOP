package controller.dat;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Brick.BrickFactory;
import gameobjects.Controller.BallController;
import gameobjects.Controller.BrickController;
import gameobjects.Controller.PaddleController;
import gameobjects.Controller.PowerUpController;
import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.scene.text.Text;

// Removed unused import

public class IngameData {
    private final Object lock = new Object();
    private Group root;
    private boolean isPause;
    private boolean isRunning;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    BallController balls;
    BrickController bricks;
    PaddleController paddle;
    PowerUpController powerUps;

    //Constructor
    public IngameData(Group root) {
        this.root = root;
        isPause = false;
        isRunning = true;
        balls = BallController.getInstance();
        bricks = BrickController.getInstance();
        paddle = PaddleController.getInstance();
        powerUps = PowerUpController.getInstance();
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

        root.getChildren().removeAll(root.getChildren());
        balls.refresh();
        bricks.refresh();
        powerUps.refresh();
        paddle.refresh();
    }

    public void update(double time) {
        if (balls.isEmpty()) {
            isRunning = false;
        } else {
            bricks.update(time);
            paddle.update(time, leftPressed, rightPressed);
            balls.update(time);
            powerUps.update( time);
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
        root.getChildren().add(paddle.getPaddle().getNode());
    }

    //Getter and setter
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
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
}
