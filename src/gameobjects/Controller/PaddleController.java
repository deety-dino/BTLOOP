package gameobjects.Controller;

import gameobjects.paddle.Paddle;
import javafx.scene.Group;

public class PaddleController implements objectInfo {
    private static PaddleController instance;
    private Paddle paddle;

    private PaddleController() {
        paddle = new Paddle(350, 550, 100, 15);
    }

    @Override
    public void refresh() {
        paddle.getPosition().setPosition(350, 550);
        paddle.getNode().setLayoutX(350);
        paddle.getNode().setLayoutY(550);
    }
    public Paddle getPaddle() {
        return paddle;
    }
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public static PaddleController getInstance() {
        if (instance == null) {
            instance = new PaddleController();
        }
        return instance;
    }

    @Override
    public void update(double time) {
    }

    public void update(double time, boolean leftPressed, boolean rightPressed) {
        if(leftPressed) {
            paddle.moveLeft(time);
        }
        if(rightPressed) {
            paddle.moveRight(time);
        }
    }
}
