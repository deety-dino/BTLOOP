package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(w, h);
        ((Rectangle) shape).setFill(Color.LIGHTBLUE);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void moveLeft() {
        if (position.getX() > 0) {
            position.setPosition(position.getX() - paddle_velocity, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    public void moveRight() {
        if (position.getX() + size.getWidth() < width) {
            position.setPosition(position.getX() + 2 * paddle_velocity, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    @Override
    public void update() {

    }
}
