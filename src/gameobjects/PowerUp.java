package gameobjects;

import controller.dat.IngameData;
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
        if (!active) {
            // Oscillating horizontal movement
            oscillatePhase += OSCILLATION_SPEED;
            double offsetX = Math.sin(oscillatePhase) * OSCILLATION_AMPLITUDE;

            // Update position with both vertical fall and horizontal oscillation
            position.setPosition(
                getX() + offsetX * 0.1,  // Scale horizontal movement
                getY() + fallSpeed
            );

            // Accelerate falling slightly over time
            fallSpeed = Math.min(fallSpeed * 1.001, BASE_FALL_SPEED * 2);

            // Update visual position
            shape.setLayoutX(position.getX());
            shape.setLayoutY(position.getY());

            // Make power-up glow/fade for visibility
            Rectangle rect = (Rectangle) shape;
            rect.setOpacity(0.7 + Math.sin(oscillatePhase * 2) * 0.3);
        }
    }

    public void activate(IngameData data) {
        if (active) return;
        active = true;

        switch (type) {
            case WIDE_PADDLE: {
                Paddle paddle = data.getPaddle();
                Rectangle paddleShape = (Rectangle) paddle.getNode();
                double originalWidth = paddleShape.getWidth();
                double newWidth = originalWidth * 1.8;  // Increased effect

                Platform.runLater(() -> {
                    paddleShape.setWidth(newWidth);
                    // Add visual feedback
                    paddleShape.setStroke(Color.YELLOW);
                    paddleShape.setStrokeWidth(2);
                });

                PauseTransition pt = new PauseTransition(Duration.seconds(12));  // Longer duration
                pt.setOnFinished(evt -> Platform.runLater(() -> {
                    paddleShape.setWidth(originalWidth);
                    paddleShape.setStroke(null);
                }));
                pt.play();
                break;
            }

            case SPEED_BALL: {
                ArrayList<Ball> balls = data.getBalls();
                if (balls == null || balls.isEmpty()) break;

                double[] originalVelocities = new double[balls.size()];
                for (int i = 0; i < balls.size(); i++) {
                    Ball b = balls.get(i);
                    if (b == null) continue;
                    b.setVe_Multi(1.7);
                }

                PauseTransition pt = new PauseTransition(Duration.seconds(10));
                pt.setOnFinished(evt -> {
                    for (int i = 0; i < balls.size() && i < originalVelocities.length; i++) {
                        Ball b = balls.get(i);
                        b.setVe_Multi(1);
                    }
                });
                pt.play();
                break;
            }

            case MULTI_BALL: {
                Paddle p = data.getPaddle();
                double px = p.getX() + (p.getSize().getWidth() / 2);
                double py = p.getY() - 10;

                // Create 3 balls with different angles
                for (int i = 0; i < 3; i++) {
                    Ball newBall = new Ball(px + (i-1) * 15, py, 6);
                    // Spread the angles more widely
                    double angle = -Math.PI/3 + (Math.PI/3 * i);
                    newBall.setDirection(Math.sin(angle), -Math.cos(angle));
                    data.addBall(newBall);
                }
                break;
            }

            case LASER_PADDLE: {
                Paddle paddle = data.getPaddle();
                Rectangle paddleShape = (Rectangle) paddle.getNode();

                // Visual feedback for laser mode
                Platform.runLater(() -> {
                    paddleShape.setFill(Color.PURPLE.brighter());
                    paddleShape.setEffect(new javafx.scene.effect.Glow(0.5));
                });

                // Reset after duration
                PauseTransition pt = new PauseTransition(Duration.seconds(15));
                pt.setOnFinished(evt -> Platform.runLater(() -> {
                    paddleShape.setFill(Color.LIGHTBLUE);
                    paddleShape.setEffect(null);
                }));
                pt.play();
                break;
            }
        }
    }

    // Backwards compatibility
    public void activate(Paddle paddle, Ball ball) {
        IngameData data = IngameData.getInstance();
        if (data != null) {
            activate(data);
            return;
        }

        active = true;
        switch (type) {
            case WIDE_PADDLE: {
                Rectangle paddleShape = (Rectangle) paddle.getNode();
                double originalWidth = paddleShape.getWidth();
                double newWidth = originalWidth * 1.8;
                Platform.runLater(() -> paddleShape.setWidth(newWidth));
                PauseTransition pt = new PauseTransition(Duration.seconds(12));
                pt.setOnFinished(evt -> Platform.runLater(() -> paddleShape.setWidth(originalWidth)));
                pt.play();
                break;
            }
            case SPEED_BALL: {
                if (ball != null) {
                    ball.setVe_Multi(1.7);
                    PauseTransition pt = new PauseTransition(Duration.seconds(10));
                    pt.setOnFinished(evt -> ball.setVe_Multi(1));
                    pt.play();
                }
                break;
            }
            // Other types need IngameData, so they do nothing in compatibility mode
        }
    }

    public boolean isActive() {
        return active;
    }

    public PowerUpType getType() {
        return type;
    }
}
