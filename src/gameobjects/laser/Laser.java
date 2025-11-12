package gameobjects.laser;

import gameobjects.GameObject;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Laser extends GameObject {
    private double velocity;

    public Laser(double x, double y) {
        super(x, y, 20, 23);
        this.velocity = -400;
        shape = new Rectangle(20, 23);
        Rectangle rect = (Rectangle) shape;

        try {
            Image bulletImage = new Image(getClass().getResourceAsStream("/gfx/inGame/bullet.png"));
            rect.setFill(new ImagePattern(bulletImage));
        } catch (Exception e) {
            rect.setFill(Color.YELLOW);
        }

        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    @Override
    public void update(double time) {
        position.setPosition(position.getX(), position.getY() + velocity * time);
        shape.setLayoutX(position.getX());
        shape.setLayoutY(position.getY());
    }

    public boolean isOffScreen() {
        return position.getY() < -20;
    }
}
