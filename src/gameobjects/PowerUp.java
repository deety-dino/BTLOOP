package gameobjects;

import controller.dat.IngameData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private boolean active = false;
    private static final double FALL_SPEED = 3.0;

    public enum PowerUpType {
        WIDE_PADDLE,
        SPEED_BALL,
        MULTI_BALL
    }

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;
        shape = new Rectangle(20, 20);

        Color powerUpColor = switch (type) {
            case WIDE_PADDLE -> Color.YELLOW;
            case SPEED_BALL -> Color.RED;
            case MULTI_BALL -> Color.GREEN;
        };

        Rectangle rect = (Rectangle) shape;
        rect.setFill(powerUpColor);
        rect.setArcWidth(5);
        rect.setArcHeight(5);
        rect.setStroke(Color.WHITE);
        rect.setStrokeWidth(1);

        // Set initial position
        rect.setLayoutX(x);
        rect.setLayoutY(y);
    }

    @Override
    public void update() {
        if (!active) {
            position.setPosition(position.getX(), position.getY() + FALL_SPEED);
            shape.setLayoutY(position.getY());
        }
    }

    public void activate(Paddle paddle, Ball ball) {
        if (active) return;
        active = true;

        switch (type) {
            case WIDE_PADDLE -> {
                double currentWidth = paddle.getWidth();
                paddle.setWidth(currentWidth * 1.3);
            }
            case SPEED_BALL -> {
                if (ball != null) {
                    ball.setVe_Multi(ball.getVe_Multi() * 1.5);
                }
            }
            case MULTI_BALL -> {
                if (ball != null) {
                    Ball newBall1 = new Ball(ball.getX(), ball.getY(), ball.getRadius());
                    Ball newBall2 = new Ball(ball.getX(), ball.getY(), ball.getRadius());

                    newBall1.setDirection(-0.7, -0.7);
                    newBall1.launch();

                    newBall2.setDirection(0.7, -0.7);
                    newBall2.launch();

                    IngameData gameData = IngameData.getInstance();
                    if (gameData != null) {
                        gameData.addBall(newBall1);
                        gameData.addBall(newBall2);
                    }
                }
            }
        }
    }

    public boolean isActive() {
        return active;
    }
}
