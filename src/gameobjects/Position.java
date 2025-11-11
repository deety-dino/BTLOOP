package gameobjects;

public class Position extends vector2f {
    public Position(double x, double y) {
        super(x, y);
    }

    public void setPosition(double x, double y) {
        super.setVector(x, y);
    }
}
