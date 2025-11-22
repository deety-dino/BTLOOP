package gameobjects.Brick;

import controller.resources.GFXManager;
import gameobjects.powerup.MultiBall_PowerUp;
import gameobjects.powerup.PowerUp;
import gameobjects.powerup.SpeedBall_PowerUp;
import gameobjects.powerup.WidePaddle_PowerUp;
import javafx.scene.paint.Color;

public class PowerUpBrick extends Brick {
    private PowerUp powerUp;
    private static final Color BRICK_COLOR = Color.web("#D4AF37");

    public PowerUpBrick(double x, double y, double w, double h) {
        super(x, y, w, h, 1);
        setImage(GFXManager.getInstance().getGFX_POWERUP_BRICK(), BRICK_COLOR);
        setPowerUp();
    }


    private void setPowerUp() {
        // Enhanced power-up type selection with weighted probabilities
        double r = Math.random();

        double offsetX = (Math.random() - 0.5) * 10;
        if (r < 0.35) {
            powerUp = new WidePaddle_PowerUp((getPosition().getX() + offsetX), getPosition().getY());
        } else if (r < 0.65) {
            powerUp = new SpeedBall_PowerUp((getPosition().getX() + offsetX), getPosition().getY());
        } else {
            powerUp = new MultiBall_PowerUp((getPosition().getX() + offsetX), getPosition().getY());
        }
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
