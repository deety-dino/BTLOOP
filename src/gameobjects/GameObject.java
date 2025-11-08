package gameobjects;

import controller.dat.dat;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

import javax.swing.text.Position;

class vector2f {
    private double x,y;
    public vector2f(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public void setVector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void normalize(double l) {
        double length = Math.sqrt(x * x + y * y);
        x = x * l / length;
        y = y * l / length;
    }
    public void add(vector2f vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }
    public void sub(vector2f vector) {
        this.x -= vector.getX();
        this.y -= vector.getY();
    }
}
class position extends vector2f {
    public position(double x, double y) {
        super(x, y);
    }
    public void setPosition(double x, double y) {
        super.setVector(x, y);
    }
}
class size extends vector2f {
    public size(double x, double y) {
        super(x, y);
    }
    public void setSize(double x, double y) {
        super.setVector(x, y);
    }
    public void setWidth(double width) {
        super.setVector(width, getHeight());
    }
    public void setHeight(double height) {
        super.setVector(getWidth(), height);
    }
    public double getWidth() {
        return super.getX();
    }
    public double getHeight() {
        return super.getY();
    }
}
public abstract class GameObject implements dat {

    protected Shape shape;
    position position;
    size size;
    public GameObject(double x, double y, double width, double height) {
        position = new position(x, y);
        size = new size(width, height);
    }
    public position getPosition() {return position;}
    public size getSize() {return size;}
    public Node getNode() {
        return shape;
    }
    public abstract void update();

    public boolean intersects(GameObject other) {
        return shape.getBoundsInParent().intersects(other.shape.getBoundsInParent());
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public Shape getShape() {
        return shape;
    }
}
