package gameobjects.powerup;

import gameobjects.ball.Ball;
import gameobjects.controller.BallController;
import gameobjects.controller.objectInfo;
import gameobjects.GameObject;
import gameobjects.paddle.Paddle;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private double fallSpeed;
    private final Random random = new Random();

    public enum PowerUpType {
        WIDE_PADDLE,
        SPEED_BALL,
        MULTI_BALL,
        LASER_PADDLE  // New type: adds shooting capability
    }

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;
        shape = new Rectangle(20, 20);

        // Enhanced visual feedback for different types
        Color powerUpColor;
        switch (type) {
            case WIDE_PADDLE:
                powerUpColor = Color.YELLOW;
                break;
            case SPEED_BALL:
                powerUpColor = Color.RED;
                break;
            case MULTI_BALL:
                powerUpColor = Color.GREEN;
                break;
            case LASER_PADDLE:
                powerUpColor = Color.PURPLE;
                break;
            default:
                powerUpColor = Color.WHITE;
        }

        Rectangle rect = (Rectangle) shape;
        rect.setFill(powerUpColor);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setStroke(Color.WHITE);
        rect.setStrokeWidth(2);

        // Position the shape according to the GameObject position
        shape.setLayoutX(position.getX());
        shape.setLayoutY(position.getY());

        // Randomize initial fall speed slightly
        fallSpeed = objectInfo.BASE_FALL_SPEED * (0.8 + random.nextDouble() * 0.4);
    }

    @Override
    public void update(double time) {
        getPosition().setPosition(position.getX(), position.getY() + fallSpeed * time);
        shape.setLayoutX(getPosition().getX());
        shape.setLayoutY(getPosition().getY());
    }

    //Wide Paddle powerup
    public static void widePaddle_Activation(Paddle paddle) {
        Rectangle paddleShape = (Rectangle) paddle.getNode();
        paddleShape.setWidth(objectInfo.defaultPaddleSize * 1.5);
    }

    public static void widePaddle_Deactivation(Paddle paddle) {
        Rectangle paddleShape = (Rectangle) paddle.getNode();
        paddleShape.setWidth(objectInfo.defaultPaddleSize * 1.0);
    }

    //Speed Ball powerup
    public static void speedBall_Activation() {
        for (Ball ball : BallController.getInstance().getBalls()) {
            ball.setVe_Multi(1.5);
        }
    }

    public static void speedBall_Deactivation() {
        for (Ball ball : BallController.getInstance().getBalls()) {
            ball.setVe_Multi(1.0);
        }
    }

    //Multi Ball powerup
    public static void multiBall_Activation() {
        ArrayList<Ball> balls = BallController.getInstance().getBalls();
        int i = balls.size() - 1;
        while (balls.size() < 1000 && i >= 0) {
            try {
                Ball ball = balls.get(i).getCopy();
                ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
                BallController.getInstance().addBall(ball);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            i--;
        }
    }

    public static void laserPaddle_Activation(Paddle paddle) {
        paddle.setHasLaser(true);
    }

    public static void laserPaddle_Deactivation(Paddle paddle) {
        paddle.setHasLaser(false);
    }


    public PowerUpType getType() {
        return type;
    }
}
