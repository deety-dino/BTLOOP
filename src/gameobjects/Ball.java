package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends GameObject {
    vector2f direction;
    private double ve_Multi;
    private boolean isActive = false;  // Trạng thái chờ bắt đầu

    public void setVe_Multi(double ve_Multi) {
        this.ve_Multi = ve_Multi;
    }

    public double getVe_Multi() {
        return ve_Multi;
    }
    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);
        shape = new Circle(x, y, radius);
        Circle circ = (Circle) shape;
        circ.setFill(Color.WHITE);
        ve_Multi = 1;
        direction = new vector2f(0, -1);  // Hướng mặc định là lên trên
        isActive = false;
    }

    @Override
    public void update() {
        if (!isActive) return;  // Không di chuyển nếu chưa active

        direction.normalize(1);
        double nextX = position.getX() + ball_velocity * ve_Multi * direction.getX();
        double nextY = position.getY() + ball_velocity * ve_Multi * direction.getY();

        // Kiểm tra va chạm với biên màn hình trước khi cập nhật vị trí
        if (nextX <= 0 || nextX >= width) {
            direction.setVector(-direction.getX(), direction.getY());
            nextX = Math.max(0, Math.min(nextX, width));
        }
        if (nextY <= 0) {
            direction.setVector(direction.getX(), -direction.getY());
            nextY = 0;
        }

        position.setPosition(nextX, nextY);
        ((Circle) shape).setCenterX(position.getX());
        ((Circle) shape).setCenterY(position.getY());
    }

    // Bắt đầu chuyển động của bóng
    public void launch() {
        isActive = true;
    }

    // Gắn bóng vào vợt
    public void attachToPaddle(Paddle paddle) {
        double x = paddle.getX() + paddle.getWidth() / 2;
        double y = paddle.getY() - getRadius() * 2;
        position.setPosition(x, y);
        ((Circle) shape).setCenterX(x);
        ((Circle) shape).setCenterY(y);
    }

    // Allow external code to set the initial direction of the ball
    public void setDirection(double x, double y) {
        if (direction == null) direction = new vector2f(x, y);
        else direction.setVector(x, y);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void bounce(GameObject obj) {
        double topSide = obj.position.getY();
        double bottomSide = obj.position.getY() + obj.size.getHeight();
        double leftSide = obj.position.getX();
        double rightSide = obj.position.getX() + obj.size.getWidth();

        if (obj instanceof Paddle) {
            double relativeIntersectX = (position.getX() - leftSide) / obj.size.getWidth();
            double normalizedRelativeIntersectionX = (relativeIntersectX - 0.5) * 2;
            double bounceAngle = normalizedRelativeIntersectionX * 60;
            double radians = Math.toRadians(-90 + bounceAngle);
            direction.setVector(Math.cos(radians), Math.sin(radians));
            return;
        }
        if (position.getY() < topSide) {
            if (position.getX() < leftSide) {
                vector2f vector = new vector2f(position.getX() - leftSide, position.getY() - topSide);
                vector.normalize(Math.sqrt(2));
                direction.add(vector);
            } else if (position.getX() > rightSide) {
                vector2f vector = new vector2f(position.getX() - rightSide, position.getY() - topSide);
                vector.normalize(Math.sqrt(2));
                direction.add(vector);
            } else {
                direction.setVector(direction.getX(), -direction.getY());
            }
        } else if (position.getY() > bottomSide) {
            if (position.getX() < leftSide) {
                vector2f vector = new vector2f(position.getX() - leftSide, position.getY() - bottomSide);
                vector.normalize(Math.sqrt(2));
                direction.add(vector);
            } else if (position.getX() > rightSide) {
                vector2f vector = new vector2f(position.getX() - rightSide, position.getY() - bottomSide);
                vector.normalize(Math.sqrt(2));
                direction.add(vector);
            } else {
                direction.setVector(direction.getX(), -direction.getY());
            }
        } else {
            direction.setVector(-direction.getX(), direction.getY());
        }
    }

    public double getRadius() {
        return size.getHeight() / 2;
    }

    public void setVelocity(double velocityX, double velocityY) {
        direction.setVector(velocityX, velocityY);
        direction.normalize(1);
    }

    public double getVelocityX() {
        return direction.getX() * ball_velocity * ve_Multi;
    }

    public double getVelocityY() {
        return direction.getY() * ball_velocity * ve_Multi;
    }
}
