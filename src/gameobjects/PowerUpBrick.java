package gameobjects;

import javafx.scene.paint.Color;

public class PowerUpBrick extends Brick {
    public PowerUpBrick(double x, double y, double w, double h) {
        super(x, y, w, h, 1, Color.web("#D4AF37"));
    }

    @Override
    public void onDestroyed() {
        //Drop power-up =)))
        System.out.println("Power-up spawned at (" +
                getPosition().getX() + ", " + getPosition().getY() + ")");
    }


}
