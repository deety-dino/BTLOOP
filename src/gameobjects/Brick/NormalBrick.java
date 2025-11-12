package gameobjects.Brick;

import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double w, double h) {
        super(x, y, w, h, 1, Color.web("#D3D3D3"));
        loadBrickImage("/gfx/brick/Brick1.png", Color.web("#D3D3D3"));
    }

    @Override
    public void onDestroyed() {

    }

}
