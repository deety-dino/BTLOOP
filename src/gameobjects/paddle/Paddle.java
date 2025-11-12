package gameobjects.paddle;

import gameobjects.controller.objectInfo;
import gameobjects.GameObject;
import gameobjects.Laser;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mng.gameInfo;

public class Paddle extends GameObject {
    private boolean hasLaser = false;
    private long lastShotTime = 0;
    private static final long SHOT_COOLDOWN = 300;

    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(w, h);
        Rectangle rect = (Rectangle) shape;
        rect.setFill(Color.LIGHTBLUE);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void moveLeft() {
        if (position.getX() > 0) {
            position.setPosition(position.getX() - objectInfo.paddleVelocity, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    public void moveRight() {
        if (position.getX() + size.getWidth() < gameInfo.width) {
            position.setPosition(position.getX() + objectInfo.paddleVelocity, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    @Override
    public void update() {

    }

    public void setLaser(boolean hasLaser) {
        this.hasLaser = hasLaser;
        Rectangle rect = (Rectangle) shape;
        if (hasLaser) {
            rect.setFill(Color.PURPLE);
        } else {
            rect.setFill(Color.LIGHTBLUE);
        }
    }

    public boolean hasLaser() {
        return hasLaser;
    }

    public Laser shoot() {
        long currentTime = System.currentTimeMillis();
        if (hasLaser && (currentTime - lastShotTime) >= SHOT_COOLDOWN) {
            lastShotTime = currentTime;
            double laserX = position.getX() + size.getWidth() / 2 - 2;
            double laserY = position.getY() - 15;
            return new Laser(laserX, laserY);
        }
        return null;
    }
}
