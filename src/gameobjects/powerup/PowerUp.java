package gameobjects.powerup;

import controller.dat.IngameData;
import gameobjects.Ball.Ball;
import gameobjects.GameObject;
import gameobjects.paddle.Paddle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private boolean active = false;
    private static final double BASE_FALL_SPEED = 2.0;
    private double fallSpeed = BASE_FALL_SPEED;
    private double oscillatePhase = 0;
    private final Random random = new Random();
    private static final double OSCILLATION_AMPLITUDE = 30.0;
    private static final double OSCILLATION_SPEED = 0.05;

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
        fallSpeed = BASE_FALL_SPEED * (0.8 + random.nextDouble() * 0.4);
    }

    @Override
    public void update() {
        getPosition().setPosition(position.getX(), position.getY() + fallSpeed);
        shape.setLayoutX(getPosition().getX());
        shape.setLayoutY(getPosition().getY());
    }

    //Wide Paddle powerup
    public static void widePaddle_Activation(Paddle paddle) {
        Rectangle paddleShape = (Rectangle) paddle.getNode();
        paddleShape.setWidth(75);
    }
    public static void widePaddle_Deactivation(Paddle paddle) {
        Rectangle paddleShape = (Rectangle) paddle.getNode();
        paddleShape.setWidth(50);
    }

    //Speed Ball powerup
    public static void speedBall_Activation(ArrayList<Ball> balls) {
        for(Ball ball : balls) {
            ball.setVe_Multi(1.5);
        }
    }
    public static void speedBall_Deactivation(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            ball.setVe_Multi(1.0);
        }
    }

    //Multi Ball powerup
    public static void multiBall_Activation(ArrayList<Ball> balls) {
        int i = balls.size();
        while (i > 0) {
            Ball ball = balls.get(i);
            ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
            balls.add(ball);
        }
    }

    //Laser Paddle powerup
    public static void laserPaddle_Activation(Paddle paddle) {

    }


    public PowerUpType getType() {
        return type;
    }
}
