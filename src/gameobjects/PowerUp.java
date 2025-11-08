package gameobjects;

import controller.dat.IngameData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private boolean active = false;
    private static final double FALL_SPEED = 2.0;

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

        // Màu sắc khác nhau cho mỗi loại power-up
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

        shape.setLayoutX(position.getX());
        shape.setLayoutY(position.getY());
    }

    @Override
    public void update() {
        if (!active) {
            double nextY = position.getY() + FALL_SPEED;
            position.setPosition(position.getX(), nextY);
            // Cập nhật vị trí của shape để phù hợp với position mới
            shape.setLayoutX(position.getX());
            shape.setLayoutY(position.getY());
        }
    }

    public void activate(Paddle paddle, Ball ball) {
        if (active) return;
        active = true;

        switch (type) {
            case WIDE_PADDLE:
                double currentWidth = paddle.getWidth();
                Rectangle rect = (Rectangle) paddle.getShape();
                rect.setWidth(currentWidth * 1.5);
                paddle.setWidth(currentWidth * 1.5);
                break;
            case SPEED_BALL:
                ball.setVelocity(ball.getVelocityX() * 1.5, ball.getVelocityY() * 1.5);
                break;
            case MULTI_BALL:
                // Tạo thêm 2 quả bóng mới
                Ball newBall1 = new Ball(ball.getX(), ball.getY(), ball.getRadius());
                Ball newBall2 = new Ball(ball.getX(), ball.getY(), ball.getRadius());

                // Set hướng khác nhau cho các bóng mới
                newBall1.setDirection(-1, -1);
                newBall2.setDirection(1, -1);

                // Thêm vào game (sẽ được xử lý trong IngameData)
                if (ball.getNode().getParent() != null) {
                    IngameData gameData = IngameData.getInstance();
                    gameData.addBall(newBall1);
                    gameData.addBall(newBall2);
                }
                break;
            case LASER_PADDLE:
                // TODO: Implement laser paddle power-up
                break;
        }
    }

    public boolean isActive() {
        return active;
    }

    public PowerUpType getType() {
        return type;
    }
}
