package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends GameObject {
    private double dx = 4;
    private double dy = -4;

    public Ball(double x, double y, double radius) {
        super(x, y, radius*2, radius*2);
        shape = new Circle(x, y, radius);
        ((Circle) shape).setFill(Color.WHITE);
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
        ((Circle) shape).setCenterX(x);
        ((Circle) shape).setCenterY(y);
    }

    public void bounceX() {
        dx = -dx;
    }
    public void bounceY() {
        dy = -dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
