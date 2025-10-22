package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(w,h);
        ((Rectangle) shape).setFill(Color.LIGHTBLUE);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void moveLeft() {
        if (x > 0) {
            x -= 15;
            shape.setLayoutX(x);
        }
    }

    public void moveRight(double sceneWidth) {
        if (x + w < sceneWidth) {
            x += 15;
            shape.setLayoutX(x);
        }
    }

    @Override
    public void update() {

    }
}
