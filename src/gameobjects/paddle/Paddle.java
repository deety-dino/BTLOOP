package gameobjects.paddle;

import gameobjects.Controller.objectInfo;
import gameobjects.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;
import mng.gameInfo;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(w, h);
        Rectangle rect = (Rectangle) shape;
        try {
            Image paddleTexture = new Image(getClass().getResourceAsStream("/gfx/pad/Pad.png"));
            rect.setFill(new javafx.scene.paint.ImagePattern(paddleTexture));
        } catch (Exception e) {
            // Fallback to solid color
            rect.setFill(Color.LIGHTBLUE);
        }
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void moveLeft(double time) {
        if (position.getX() > 0) {
            position.setPosition(position.getX() - objectInfo.paddleVelocity * time, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    public void moveRight(double time) {
        if (position.getX() + size.getWidth() < gameInfo.width) {
            position.setPosition(position.getX() + objectInfo.paddleVelocity * time, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    @Override
    public void update(double time) {

    }
}
