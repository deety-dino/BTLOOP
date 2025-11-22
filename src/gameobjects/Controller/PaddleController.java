package gameobjects.Controller;

import controller.dat.GameStatusData;
import gameobjects.Ball.Ball;
import gameobjects.paddle.Paddle;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class PaddleController implements ObjectController {
    private static PaddleController instance;
    private final Paddle paddle;

    private PaddleController() {
        paddle = new Paddle(350, 550, defaultPaddleWidth, defaultPaddleHeight);
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

    public static PaddleController getInstance() {
        if (instance == null) {
            instance = new PaddleController();
        }
        return instance;
    }

    @Override
    public void update(double time) {
        paddle.update(time);
        if (GameStatusData.getInstance().isLeftPressed()) {paddle.moveLeft(time);}
        if (GameStatusData.getInstance().isRightPressed()) {paddle.moveRight(time);}
    }

    private void bounceEffect() {
        javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow(0.3);
        paddle.getNode().setEffect(glow);
        PauseTransition pt = new PauseTransition(Duration.millis(100));
        pt.setOnFinished(_ -> paddle.getNode().setEffect(null));
        pt.play();
    }
    public boolean checkPaddleCollision(Ball ball) {
        if (ball.intersects(paddle)) {
            bounceEffect();
            return true;
        }
        return false;
    }
}
