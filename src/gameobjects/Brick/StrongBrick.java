package gameobjects.Brick;

import controller.resources.GFXManager;
import javafx.scene.paint.Color;
public class StrongBrick extends Brick {

    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 2);
        setImage(GFXManager.getInstance().getGFX_STRONG_BRICK(), Color.GREEN);
    }
}
