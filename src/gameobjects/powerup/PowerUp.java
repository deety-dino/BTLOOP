package gameobjects.powerup;

import controller.dat.IngameData;
import gameobjects.ball.Ball;
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
        LASER_PADDLE
    }

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;
        shape = new Rectangle(20, 20);


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

        fallSpeed = BASE_FALL_SPEED * (0.8 + random.nextDouble() * 0.4);
    }

    @Override
    public void update() {
        getPosition().setPosition(position.getX(), position.getY() + fallSpeed);
        shape.setLayoutX(getPosition().getX());
        shape.setLayoutY(getPosition().getY());
    }

    public static void widePaddle_Activation(Paddle paddle) {
        Rectangle paddleShape = (Rectangle) paddle.getNode();
        paddleShape.setWidth(75);
    }
    public static void widePaddle_Deactivation(Paddle paddle) {
        Rectangle paddleShape = (Rectangle) paddle.getNode();
        paddleShape.setWidth(50);
    }

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

    public static void multiBall_Activation(ArrayList<Ball> balls, javafx.scene.Group root) {
        int originalSize = balls.size();
        for (int i = 0; i < originalSize; i++) {
            Ball originalBall = balls.get(i);

            Ball newBall1 = new Ball(originalBall.getX(), originalBall.getY(), originalBall.getRadius());
            Ball newBall2 = new Ball(originalBall.getX(), originalBall.getY(), originalBall.getRadius());

            double origX = originalBall.getDirection().getX();
            double origY = originalBall.getDirection().getY();

            double angle1 = Math.toRadians(30);
            double angle2 = Math.toRadians(-30);

            double newX1 = origX * Math.cos(angle1) - origY * Math.sin(angle1);
            double newY1 = origX * Math.sin(angle1) + origY * Math.cos(angle1);

            double newX2 = origX * Math.cos(angle2) - origY * Math.sin(angle2);
            double newY2 = origX * Math.sin(angle2) + origY * Math.cos(angle2);

            newBall1.setDirection(newX1, newY1);
            newBall1.getDirection().normalize(1.0);

            newBall2.setDirection(newX2, newY2);
            newBall2.getDirection().normalize(1.0);

            balls.add(newBall1);
            balls.add(newBall2);
            root.getChildren().add(newBall1.getNode());
            root.getChildren().add(newBall2.getNode());
        }
    }

    public static void laserPaddle_Activation(Paddle paddle) {
        paddle.setLaser(true);
    }

    public static void laserPaddle_Deactivation(Paddle paddle) {
        paddle.setLaser(false);
    }


    public PowerUpType getType() {
        return type;
    }
}
