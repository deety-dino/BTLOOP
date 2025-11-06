package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends GameObject {
    vector2f direction;
    private double ballVelocity = 5.0; // Default ball speed

    public void setBallVelocity(double velocity) {
        this.ballVelocity = velocity;
    }

    public double getBallVelocity() {
        return this.ballVelocity;
    }

    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);
        shape = new Circle(x, y, radius);
        Circle circ = (Circle) shape;
        circ.setFill(Color.WHITE);
        direction = new vector2f(1, 1);
    }

    @Override
    public void update() {
        direction.normalize(1);
        position.setPosition(position.getX() + ballVelocity * direction.getX(), position.getY() + ballVelocity * direction.getY());
        ((Circle) shape).setCenterX(position.getX());
        ((Circle) shape).setCenterY(position.getY());
        if (position.getX() <= 0 || position.getX() >= width) {
            direction.setVector(-direction.getX(), direction.getY());
        }
        if (position.getY() <= 0) {
            direction.setVector(direction.getX(), -direction.getY());
        }
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
}
