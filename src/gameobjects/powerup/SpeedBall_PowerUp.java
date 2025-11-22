package gameobjects.powerup;

import controller.resources.GFXManager;
import javafx.scene.paint.Color;

public class SpeedBall_PowerUp extends PowerUp {
    public SpeedBall_PowerUp(double x, double y) {
        super(x, y);
        setImage(GFXManager.getInstance().getGFX_SPEEDBALL_POWERUP(), Color.GREEN);
    }
}
