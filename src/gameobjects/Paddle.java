package gameobjects;

import controller.dat.dat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(w, h);
        Rectangle rect = (Rectangle) shape;
        rect.setFill(Color.LIGHTBLUE);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void moveLeft() {
        if (position.getX() > 0) {
            position.setPosition(position.getX() - dat.paddle_velocity, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    public void moveRight() {
        if (position.getX() + size.getWidth() < dat.width) {
            position.setPosition(position.getX() + dat.paddle_velocity, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    @Override
    public void update() {

    }
}
