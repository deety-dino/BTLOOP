package gameobjects.Brick;

import controller.resources.GFXManager;
import javafx.scene.paint.Color;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double w, double h) {
        super(x, y, w, h, 1);
        setImage(GFXManager.getInstance().getGFX_DEFAULT_BRICK(), Color.web("#D3D3D3"));
    }


}
