package gameobjects.Controller;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Brick.PowerUpBrick;
import gameobjects.paddle.Paddle;
import gameobjects.powerup.PowerUp;
import javafx.animation.PauseTransition;
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
    public void addBall( Ball ball) {
        balls.add(ball);
    }
    public boolean isEmpty() {
        return balls.isEmpty();
    }

    @Override
    public void update(double time) {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (ball.getY() > gameInfo.height) {
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
                                PowerUpController.getInstance().addPowerUp(powerUp);
                            }
                        }
                        brickIt.remove();
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
            ball.update(time);
        }
    }

    @Override
    public void refresh() {
        balls.clear();
    }
}
