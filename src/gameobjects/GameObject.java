package gameobjects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected double w;
    protected double h;
    protected Shape shape;

    public GameObject(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Node getNode() {
        return shape;
    }

    public abstract void update();

    public boolean intersects(GameObject other) {
        return shape.getBoundsInParent().intersects(other.shape.getBoundsInParent());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }
}
