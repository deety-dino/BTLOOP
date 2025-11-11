package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Laser extends GameObject {
    private static final double LASER_SPEED = 8.0;
    private static final double LASER_WIDTH = 4.0;
    private static final double LASER_HEIGHT = 15.0;

    public Laser(double x, double y) {
        super(x, y, LASER_WIDTH, LASER_HEIGHT);
        shape = new Rectangle(LASER_WIDTH, LASER_HEIGHT);
        Rectangle rect = (Rectangle) shape;
        rect.setFill(Color.CYAN);
        rect.setStroke(Color.WHITE);
        rect.setStrokeWidth(1);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    @Override
    public void update() {
        position.setPosition(position.getX(), position.getY() - LASER_SPEED);
        shape.setLayoutY(position.getY());
    }

    public boolean isOffScreen() {
        return position.getY() < -LASER_HEIGHT;
    }
}
