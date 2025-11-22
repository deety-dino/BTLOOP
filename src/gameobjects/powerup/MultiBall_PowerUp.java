package gameobjects.powerup;

import controller.resources.GFXManager;
import javafx.scene.paint.Color;

public class MultiBall_PowerUp extends PowerUp {
    public MultiBall_PowerUp(double x, double y) {
        super(x, y);
        setImage(GFXManager.getInstance().getGFX_MULTIBALL_POWERUP(), Color.RED);
    }
}
