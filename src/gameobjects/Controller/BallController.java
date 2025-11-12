package gameobjects.controller;

import gameobjects.ball.Ball;
import gameobjects.brick.Brick;
import gameobjects.brick.PowerUpBrick;
import gameobjects.paddle.Paddle;
import gameobjects.powerup.PowerUp;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.util.Duration;
import mng.gameInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class BallController implements objectInfo {
    private static BallController instance;
    private ArrayList<Ball> balls;

    private BallController() {
        balls = new ArrayList<>();
    }

    public static BallController getInstance() {
        if (instance == null) {
            instance = new BallController();
        }
        return instance;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }
    public void addBall(Ball ball) {
        balls.add(ball);
    }
    public boolean isEmpty() {
        return balls.isEmpty();
    }

    public void update(Group root, double time) {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (ball.getY() > gameInfo.height) {
                root.getChildren().remove(ball.getNode());
                it.remove();
                continue;
            }

            // Brick collision with enhanced effects
            Iterator<Brick> brickIt = BrickController.getInstance().getBricks().iterator();
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
                                powerUpController.getInstance().addPowerUp(powerUp, root);
                            }
                        }
                        brickIt.remove();
                        root.getChildren().remove(brick.getNode());
                    }
                break;
                }
            }

            // Paddle collision with improved bounce angles
            Paddle paddle = PaddleController.getInstance().getPaddle();
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

    public void refresh() {
        balls.clear();
    }
}
