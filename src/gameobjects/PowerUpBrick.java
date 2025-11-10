package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;

public class PowerUpBrick extends Brick {
    private PowerUp powerUp;
    private static final Color BRICK_COLOR = Color.web("#D4AF37");
    private static final Color GLOW_COLOR = Color.YELLOW;

    public PowerUpBrick(double x, double y, double w, double h) {
        super(x, y, w, h, 1, BRICK_COLOR);
        Rectangle rect = (Rectangle) shape;
        rect.setFill(createGradient());
        rect.setStroke(GLOW_COLOR);
        rect.setStrokeWidth(1.5);
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
        double r = Math.random();
        PowerUp.PowerUpType type;

        if (r < 0.40) {
            type = PowerUp.PowerUpType.WIDE_PADDLE;
        } else if (r < 0.70) {
            type = PowerUp.PowerUpType.SPEED_BALL;
        } else {
            type = PowerUp.PowerUpType.MULTI_BALL;
        }

        double powerUpX = position.getX() + (size.getWidth() - 20) / 2;
        double powerUpY = position.getY();
        powerUp = new PowerUp(powerUpX, powerUpY, type);
    }

    @Override
    public void hit() {
        super.hit();
        shape.setOpacity(0.8);
    }

    public PowerUp getPowerUp() {
        PowerUp temp = powerUp;
        powerUp = null;
        return temp;
    }
}
