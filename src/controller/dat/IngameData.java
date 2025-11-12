package controller.dat;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Controller.BallController;
import gameobjects.Controller.BrickController;
import gameobjects.Controller.PaddleController;
import gameobjects.Controller.PowerUpController;
import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.scene.text.Text;
import mng.gameInfo;


// Removed unused import

public class IngameData {
    public gameInfo.State state;
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
        state = gameInfo.State.LOADING;
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
            case 1: {
                LevelData.level1();
                break;
            } case 2: {
                LevelData.level2();
                break;
            } case 3: {
                LevelData.level3();
            }
            default:
                System.out.println("Coming soon...");
        }
        getGroup();
        state = gameInfo.State.IN_GAME;
    }

    private void refesh() {
        state = gameInfo.State.LOADING;
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
            state = gameInfo.State.GAMEOVER;
        } else if(bricks.isEmpty()){
            state = gameInfo.State.VICTORY;
        }else {
            bricks.update(time);
            paddle.update(time, leftPressed, rightPressed);
            balls.update(time);
            powerUps.update(time);
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
