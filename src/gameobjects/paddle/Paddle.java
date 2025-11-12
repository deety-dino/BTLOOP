package gameobjects.paddle;

import gameobjects.controller.objectInfo;
import gameobjects.GameObject;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import mng.gameInfo;

public class Paddle extends GameObject {
    private boolean hasLaser;
    private double shootCooldown;
    private static final double SHOOT_INTERVAL = 0.3;

    public Paddle(double x, double y, double w, double h) {
        super(x, y, w, h);
        shape = new Rectangle(w, h);
        Rectangle rect = (Rectangle) shape;
        hasLaser = false;
        shootCooldown = 0;

        try {
            Image paddleImage = new Image(getClass().getResourceAsStream("/gfx/pad/Pad.png"));
            rect.setFill(new ImagePattern(paddleImage));
        } catch (Exception e) {
            rect.setFill(Color.LIGHTBLUE);
            System.err.println("Warning: Could not load paddle image, using default color");
        }

        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void moveLeft(double time) {
        if (position.getX() > 0) {
            position.setPosition(position.getX() - objectInfo.paddleVelocity * time, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    public void moveRight(double time) {
        if (position.getX() + size.getWidth() < gameInfo.width) {
            position.setPosition(position.getX() + objectInfo.paddleVelocity * time, position.getY());
            shape.setLayoutX(position.getX());
        }
    }

    @Override
    public void update(double time) {
        if (shootCooldown > 0) {
            shootCooldown -= time;
        }
    }

    public boolean canShoot() {
        return hasLaser && shootCooldown <= 0;
    }

    public void resetShootCooldown() {
        shootCooldown = SHOOT_INTERVAL;
    }

    public boolean hasLaser() {
        return hasLaser;
    }

    public void setHasLaser(boolean hasLaser) {
        this.hasLaser = hasLaser;
    }

    public double getCenterX() {
        return position.getX() + size.getWidth() / 2;
    }
}
