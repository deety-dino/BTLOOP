package gameobjects.Brick;

import gameobjects.powerup.PowerUp;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;

public class PowerUpBrick extends Brick {
    private PowerUp powerUp;
    private static final Color BRICK_COLOR = Color.web("#D4AF37");
    private static final Color GLOW_COLOR = Color.YELLOW;
    private static final Color FALLBACK_COLOR = Color.web("#D4AF37");

    public PowerUpBrick(double x, double y, double w, double h) {
        super(x, y, w, h, 1, FALLBACK_COLOR);

        // Load the power-up brick image
        try {
            Image brickImage = new Image(getClass().getResourceAsStream("/gfx/brick/Brick3.png"));
            ImageView imageView = new ImageView(brickImage);
            imageView.setFitWidth(w);
            imageView.setFitHeight(h);
            imageView.setPreserveRatio(false);
            shape = imageView;
            shape.setLayoutX(x);
            shape.setLayoutY(y);

            // Optional: Add glow effect to power-up brick
            javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow();
            glow.setLevel(0.3);
            shape.setEffect(glow);

        } catch (Exception e) {
            System.out.println("Power-up brick image not found, using fallback with gradient");
            e.printStackTrace();
            // Keep the gradient fallback from original code
        }
    }

    private static LinearGradient createGradient() {
        return new LinearGradient(
            0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, BRICK_COLOR),
            new Stop(0.5, BRICK_COLOR.brighter()),
            new Stop(1, BRICK_COLOR)
        );
    }

    @Override
    public void onDestroyed() {
        // Enhanced power-up type selection with weighted probabilities
        double r = Math.random();
        PowerUp.PowerUpType type;

        if (r < 0.35) {
            type = PowerUp.PowerUpType.WIDE_PADDLE;      // 35% chance
        } else if (r < 0.65) {
            type = PowerUp.PowerUpType.SPEED_BALL;       // 30% chance
        } else if (r < 0.85) {
            type = PowerUp.PowerUpType.MULTI_BALL;       // 20% chance
        } else {
            type = PowerUp.PowerUpType.LASER_PADDLE;     // 15% chance
        }

        // Create power-up at brick's position with slight random offset
        double offsetX = (Math.random() - 0.5) * 10;  // ±5 pixels
        powerUp = new PowerUp(
            getPosition().getX() + offsetX,
            getPosition().getY(),
            type
        );
    }

    @Override
    public void hit() {
        super.hit();
        // Add visual feedback when hit
        shape.setOpacity(0.8);
    }

    public PowerUp getPowerUp() {
        PowerUp temp = powerUp;
        powerUp = null; // Clear reference after getting it
        return temp;
    }
}
