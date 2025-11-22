package gameobjects;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class GameObject {

    protected Node shape;
    protected Position position;
    protected Size size;

    public GameObject(double x, double y, double width, double height) {
        position = new Position(x, y);
        size = new Size(width, height);
    }

    public abstract void update(double time);

    public boolean intersects(GameObject other) {
        return shape.getBoundsInParent().intersects(other.shape.getBoundsInParent());
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }


    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    public Node getNode() {
        return shape;
    }

    protected void setImage(Image image, Color fallbackColor) {
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(size.getHeight());
            imageView.setFitWidth(size.getWidth());
            imageView.setLayoutX(position.getX());
            imageView.setLayoutY(position.getY());
            imageView.setPreserveRatio(true);
            shape = imageView;
        } else {
            Rectangle rectangle = new Rectangle(position.getX(), position.getY(), size.getWidth(), size.getHeight());
            rectangle.setFill(fallbackColor);
            rectangle.setLayoutX(position.getX());
            rectangle.setLayoutY(position.getY());
            shape = rectangle;
        }
    };
}
