package gameobjects.powerup;

import controller.resources.GFXManager;
import javafx.scene.paint.Color;

public class WidePaddle_PowerUp extends PowerUp{
    public WidePaddle_PowerUp(double x, double y) {
        super(x, y);
        setImage(GFXManager.getInstance().getGFX_WIDE_PADDLE_POWERUP(), Color.LIGHTBLUE);
    }
}
