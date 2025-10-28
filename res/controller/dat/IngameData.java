package controller.dat;

import java.util.ArrayList;
import java.util.Iterator;

import gameobjects.Paddle;
import gameobjects.Ball;
import gameobjects.Brick;
import javafx.scene.layout.AnchorPane;

public class IngameData implements dat{
    private AnchorPane root;
    private boolean isContinue = true;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    ArrayList<Brick> bricks = new ArrayList<Brick>();

    Paddle paddle;
    //Constructor
    public IngameData(AnchorPane root) {
        this.root = root;
        root.setPrefSize(width, height);
        paddle = new Paddle(350, 550, 100, 15);
    }

    public void loadData(int level){
        isContinue = true;
        switch (level){
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
                Brick brick = new Brick(
                        offsetX + col * (brickWidth + 5),
                        offsetY + row * (brickHeight + 5),
                        brickWidth, brickHeight
                );
                bricks.add(brick);
            }
        }
    }

    //update
    private void updatePaddle(){
        if(leftPressed) {
            paddle.moveLeft();
        }
        if(rightPressed) {
            paddle.moveRight();
        }
    }
    private void updateBall() {
        if(balls.size() == 0) {
            isContinue = false;
            return;
        }
        for (Ball ball : balls) {
            if(ball.getY() > height){
                balls.remove(ball);
                continue;
            }
            //Brick collision
            Iterator<Brick> it = bricks.iterator();
            while (it.hasNext()) {
                Brick brick = it.next();
                if (ball.intersects(brick)) {
                    ball.bounce(brick);
                    root.getChildren().remove(brick.getNode());
                    it.remove();
                    break;
                }
            }

            //Paddle collision
            if(ball.intersects(paddle)){
                ball.bounce(paddle);
            }
            ball.update();
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
    public void getRoot(/*AnchorPane root*/) {
        root.getChildren().add(paddle.getNode());
        for(Brick brick : bricks) {
            root.getChildren().add(brick.getNode());
        }
        for(Ball ball : balls) {
            root.getChildren().add(ball.getNode());
        }
    }
    public boolean isContinue() {
        return isContinue;
    }

}
