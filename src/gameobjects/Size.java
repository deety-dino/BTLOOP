package gameobjects;

public class Size extends vector2f {
    public Size(double x, double y) {
        super(x, y);
    }
    public void setSize(double x, double y) {
        super.setVector(x, y);
    }
    public double getWidth() {
        return super.getX();
    }
    public double getHeight() {
        return super.getY();
    }
}