package controller.dat;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Brick.BrickFactory;
import gameobjects.Controller.BallController;
import gameobjects.Controller.BrickController;
import gameobjects.Controller.PaddleController;
import gameobjects.Controller.PowerUpController;
import javafx.scene.Group;
import javafx.scene.text.Text;

// Removed unused import

public class IngameData {
    private final Group root;
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

    public void loadData(int level) {
        isPause = false;
        isRunning = true;
        balls.refresh();
        bricks.refresh();
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

    public void update(double time) {
        if(balls.isEmpty()) {
            isRunning =  false;
        } else {
            bricks.update(root, time);
            paddle.update(time, leftPressed, rightPressed);
            balls.update(root, time);
            powerUps.update(root, time);
        }
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
        root.getChildren().add(paddle.getPaddle().getNode());
        for (Brick brick : bricks.getBricks()) {
            root.getChildren().add(brick.getNode());
        }
        for (Ball ball : balls.getBalls()) {
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
