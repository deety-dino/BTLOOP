package gameobjects.paddle;

import controller.resources.GFXManager;
import gameobjects.Controller.ObjectController;
import gameobjects.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;
import mng.SceneManager;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        setImage(GFXManager.getInstance().getGFX_Paddle(), Color.LIGHTBLUE);
    }

    public void moveLeft(double time) {
        if (position.getX() > 0) {
            position.setPosition(position.getX() - ObjectController.paddleVelocity * time, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    public void moveRight(double time) {
        if (position.getX() + size.getWidth() < SceneManager.width) {
            position.setPosition(position.getX() + ObjectController.paddleVelocity * time, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    @Override
    protected void setImage(Image image, Color fallbackColor) {
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setLayoutX(position.getX());
            imageView.setLayoutY(position.getY());
            imageView.setFitHeight(size.getHeight());
            imageView.setFitWidth(size.getWidth());
            imageView.setPreserveRatio(true);
            shape = imageView;
        } else {
            Rectangle rect = (Rectangle) shape;
            rect.setFill(fallbackColor);
        }
    }

    @Override
    public void update(double time) {
    }
}
