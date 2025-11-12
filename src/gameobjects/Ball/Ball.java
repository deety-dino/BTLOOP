package gameobjects.Ball;

import gameobjects.Controller.objectInfo;
import gameobjects.GameObject;
import gameobjects.vector2f;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mng.gameInfo;

public class Ball extends GameObject {
    private vector2f direction;
    private double ve_Multi;

    public void setVe_Multi(double ve_Multi) {
        this.ve_Multi = ve_Multi;
    }

    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);
        shape = new Circle(x, y, radius);
        Circle circ = (Circle) shape;
        circ.setFill(Color.WHITE);
        ve_Multi = 1;
        direction = new vector2f(1, 1);
    }
    public Ball getCopy() {
        return new Ball(getX(), getY(), getRadius());
    }

    @Override
    public void update(double time) {
        direction.normalize(1);
        position.setPosition(position.getX() + objectInfo.ballVelocity * ve_Multi * direction.getX() * time,
                position.getY() + objectInfo.ballVelocity * ve_Multi * direction.getY() * time);
        ((Circle) shape).setCenterX(position.getX());
        ((Circle) shape).setCenterY(position.getY());
        if (position.getX() <= 0 || position.getX() >= gameInfo.width) {
            direction.setVector(-direction.getX(), direction.getY());
        }
        if (position.getY() <= 0) {
            direction.setVector(direction.getX(), -direction.getY());
        }
    }

    // Allow external code to set the initial direction of the ball
    public vector2f getDirection() {
        return direction;
    }

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
        double topSide = obj.getPosition().getY();
        double bottomSide = obj.getPosition().getY() + obj.getSize().getHeight();
        double leftSide = obj.getPosition().getX();
        double rightSide = obj.getPosition().getX() + obj.getSize().getWidth();
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
