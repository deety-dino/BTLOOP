package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends GameObject {

    public Brick(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(x, y, w, h);
        ((Rectangle) shape).setFill(Color.ORANGE);
        ((Rectangle) shape).setStroke(Color.BLACK);
    }

    @Override
    public void update() {

    }
}
