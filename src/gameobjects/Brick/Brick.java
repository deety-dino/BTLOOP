package gameobjects.Brick;

import gameobjects.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;

public abstract class Brick extends GameObject {

    protected int hitPoints;

    public Brick(double x, double y, double w, double h, int hitPoints) {
        super(x, y, w, h);
        this.hitPoints = hitPoints;
    }

    @Override
    protected void setImage(Image image, Color fallbackColor) {
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size.getWidth());
            imageView.setFitHeight(size.getHeight());
            imageView.setLayoutX(position.getX());
            imageView.setLayoutY(position.getY());
            imageView.setPreserveRatio(false);
            shape = imageView;
        } else {
            Rectangle rectangle =  new Rectangle(size.getWidth(), size.getHeight(), fallbackColor);
            rectangle.setX(position.getX());
            rectangle.setY(position.getY());
            shape = rectangle;
        }
    }

    public void hit() {
        hitPoints--;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    @Override
    public void update(double time) {

    }

}
