package gameobjects.Brick;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StrongBrick extends Brick {
    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 2, Color.web("#808080"));
    }

    @Override
    protected void onHit() {
        if (hitPoints == 1) {
            ((Rectangle) shape).setFill(Color.web("#B0B0B0"));
        }
    }

    @Override
    public void onDestroyed() {

    }
}
