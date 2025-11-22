package gameobjects.Controller;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Brick.PowerUpBrick;

import java.util.ArrayList;

public class BrickController implements ObjectController {
    private static BrickController instance;
    private final ArrayList<Brick> bricks;

    private BrickController() {
        bricks = new ArrayList<>();
    }

    public static BrickController getInstance() {
        if (instance == null) {
            instance = new BrickController();
        }
        return instance;
    }

    @Override
    public void update(double time) {
    }

    @Override
    public void refresh() {
        bricks.clear();
    }


    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    public boolean isEmpty() {
        return bricks.isEmpty();
    }

    public Brick checkBrickCollision(Ball ball) {
        for (Brick brick : bricks) {
            if (ball.intersects(brick)) {
                brick.hit();
                if (brick.isDestroyed()) {
                    if (brick instanceof PowerUpBrick) {
                        PowerUpController.getInstance().addPowerUp(((PowerUpBrick) brick).getPowerUp());
                    }
                    bricks.remove(brick);
                }
                return brick;
            }
        }
        return null;
    }
}
