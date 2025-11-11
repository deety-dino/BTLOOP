package gameobjects;

import javafx.scene.Node;
import javafx.scene.shape.Shape;


public abstract class GameObject {

    protected Shape shape;
    protected Position position;
    protected Size size;
    public GameObject(double x, double y, double width, double height) {
        position = new Position(x, y);
        size = new Size(width, height);
    }
    public Position getPosition() {return position;}
    public Size getSize() {return size;}
    public Node getNode() {
        return shape;
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
}
