package controller.dat;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import gameobjects.Brick.BrickFactory;
import gameobjects.Controller.BallController;
import gameobjects.Controller.BrickController;

public class LevelData {
    public static void level1() {

        BallController.getInstance().addBall(new Ball(400, 100, 8));
        int rows = 10;
        int cols = 32;
        double brickWidth = 20;
        double brickHeight = 20;
        double offsetX = 35;
        double offsetY = 50;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        offsetX + col * (brickWidth + 3),
                        offsetY + row * (brickHeight + 3),
                        brickWidth, brickHeight);
                BrickController.getInstance().addBrick(brick);
            }
        }
    }
}
