package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Brick extends GameObject {

    protected int hitPoints;

    public Brick(double x, double y, double w, double h, int hitPoints, Color color) {
        super(x, y, w, h);
        this.hitPoints = hitPoints;

        shape = new Rectangle(w, h, color);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void hit() {
        hitPoints--;
        onHit();
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    public void onDestroyed() {}
    protected void onHit() {}

    @Override
    public void update() {

    }

    public boolean checkCollision(Ball ball) {
        if (intersects(ball)) {
            ball.bounce(this);
            return true;
        }
        return false;
    }

    public double getWidth() {
        return size.getWidth();
    }

    public double getHeight() {
        return size.getHeight();
    }
}
