package gameobjects.controller;

import gameobjects.laser.Laser;
import gameobjects.paddle.Paddle;
import javafx.scene.Group;

public class PaddleController implements objectInfo {
    private static PaddleController instance;
    private Paddle paddle;

    private PaddleController() {
        paddle = new Paddle(350, 550, 100, 15);
    }

    @Override
    public void refresh() {
        paddle.getPosition().setPosition(350, 550);
        paddle.getNode().setLayoutX(350);
        paddle.getNode().setLayoutY(550);
        paddle.setHasLaser(false);
    }
    public Paddle getPaddle() {
        return paddle;
    }
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public static PaddleController getInstance() {
        if (instance == null) {
            instance = new PaddleController();
        }
        return instance;
    }

    @Override
    public void update(double time) {
        paddle.update(time);
    }

    public void update(double time, boolean leftPressed, boolean rightPressed, boolean spacePressed) {
        if(leftPressed) {
            paddle.moveLeft(time);
        }
        if(rightPressed) {
            paddle.moveRight(time);
        }
        if(spacePressed && paddle.canShoot()) {
            shootLaser();
        }
        paddle.update(time);
    }

    private void shootLaser() {
        double laserX = paddle.getCenterX() - 2.5;
        double laserY = paddle.getY() - 15;
        Laser laser = new Laser(laserX, laserY);
        LaserController.getInstance().addLaser(laser);
        paddle.resetShootCooldown();
    }
}
