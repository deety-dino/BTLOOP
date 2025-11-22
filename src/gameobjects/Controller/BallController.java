package gameobjects.Controller;

import controller.dat.GameData;
import controller.dat.GameStatusData;
import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import mng.SceneManager;

import java.util.ArrayList;
import java.util.Iterator;

public class BallController implements ObjectController {
    private static BallController instance;
    private final ArrayList<Ball> balls;

    private BallController() {
        balls = new ArrayList<>();
    }

    public static BallController getInstance() {
        if (instance == null) {
            instance = new BallController();
        }
        return instance;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public boolean isEmpty() {
        return balls.isEmpty();
    }

    @Override
    public void update(double time) {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (ball.getY() > SceneManager.height) {
                it.remove();
                continue;
            }
            Brick brick = BrickController.getInstance().checkBrickCollision(ball);
            if (brick != null) {
                GameStatusData.getInstance().setScore(GameStatusData.getInstance().getScore() + 100);
                ball.bounce(brick);
            }
            if (PaddleController.getInstance().checkPaddleCollision(ball)) {
                ball.bouncePaddle(PaddleController.getInstance().getPaddle());
            }
            ball.update(time);
        }
    }

    @Override
    public void refresh() {
        balls.clear();
    }

}
