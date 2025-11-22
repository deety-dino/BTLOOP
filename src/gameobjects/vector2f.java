package gameobjects;

public class vector2f {
    private double x, y;

    public vector2f(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

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
}
