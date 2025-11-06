package controller.dat;

import java.util.ArrayList;
import java.util.Iterator;

import gameobjects.BrickFactory;
import gameobjects.Paddle;
import gameobjects.Ball;
import gameobjects.Brick;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class IngameData implements dat {
    private Group group;
    private boolean isPause;
    private boolean isRunning;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    ArrayList<Brick> bricks = new ArrayList<Brick>();

    Paddle paddle;

    //Constructor
    public IngameData(Group group) {
        this.group = group;
        isPause = false;
        isRunning = true;
        paddle = new Paddle(350, 550, 100, 15);

    }

    public void loadData(int level) {
        isPause = false;
        isRunning = true;
        switch (level) {
            case 1:
                level1();
                break;
            default:
                System.out.println("Coming soon...");
        }
    }

    //level Pane
    private void level1() {
        balls.clear();
        bricks.clear();
        balls.add(new Ball(400, 100, 8));
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 50;
        double offsetX = 35;
        double offsetY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        offsetX + col * (brickWidth + 5),
                        offsetY + row * (brickHeight + 5),
                        brickWidth, brickHeight);
                bricks.add(brick);
            }
        }
    }

    //update
    private void updatePaddle() {
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight();
        }
    }

    private void updateBall() {
        if (balls.size() == 0) {
            isRunning = false;
            return;
        } else {
            for (Ball ball : balls) {
                if (ball.getY() > height) {
                    balls.remove(ball);
                    continue;
                }

                //Brick collision
                Iterator<Brick> it = bricks.iterator();
                while (it.hasNext()) {
                    Brick brick = it.next();
                    if (ball.intersects(brick)) {
                        ball.bounce(brick);
                        group.getChildren().remove(brick.getNode());
                        it.remove();
                        break;
                    }
                }
                //Paddle collision
                if (ball.intersects(paddle)) {
                    ball.bounce(paddle);
                }
                ball.update();
            }
        }

    }

    public void update() {
        updatePaddle();
        updateBall();
    }

    //Getter and setter
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void getRoot() {
        group.getChildren().clear();
        group.getChildren().add(paddle.getNode());
        for (Brick brick : bricks) {
            group.getChildren().add(brick.getNode());
        }
        for (Ball ball : balls) {
            group.getChildren().add(ball.getNode());
        }
    }

    public void setPause() {
        isPause = !isPause;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning() {
        isRunning = true;
    }
}
