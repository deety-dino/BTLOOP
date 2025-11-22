package controller.dat;

import gameobjects.Controller.BallController;
import gameobjects.Controller.BrickController;
import gameobjects.Controller.PaddleController;
import gameobjects.Controller.PowerUpController;

public class ObjectData {
    private static ObjectData instance;

    private BallController balls;
    private BrickController bricks;
    private PaddleController paddle;
    private PowerUpController powerUps;

    private ObjectData(){
        balls = BallController.getInstance();
        bricks = BrickController.getInstance();
        paddle = PaddleController.getInstance();
        powerUps = PowerUpController.getInstance();
    }
    public static ObjectData getInstance() {
        if (instance == null) {
            instance = new ObjectData();
        }
        return instance;
    }

    public BallController getBalls() {
        return balls;
    }

    public BrickController getBricks() {
        return bricks;
    }

    public PaddleController getPaddle() {
        return paddle;
    }

    public PowerUpController getPowerUps() {
        return powerUps;
    }

    public void update(double delta){
        bricks.update(delta);
        paddle.update(delta);
        balls.update(delta);
        powerUps.update(delta);
    }

    public void reset(){
        bricks.refresh();
        paddle.refresh();
        balls.refresh();
        powerUps.refresh();
    }
}
