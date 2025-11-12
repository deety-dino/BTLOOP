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

    private static void level2() {
        BallController.getInstance().addBall(new Ball(400, 100, 10));

        int rows = 5; // pyramid height
        double brickWidth = 70;
        double brickHeight = 50;
        double startX = 35;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            int bricksInRow = rows - row; // pyramid decreases each row
            double offsetX = startX + row * (brickWidth / 2); // center pyramid
            for (int col = 0; col < bricksInRow; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        offsetX + col * (brickWidth + 5),
                        startY + row * (brickHeight + 5),
                        brickWidth, brickHeight);
                BrickController.getInstance().addBrick(brick);
            }
        }
    }

    private void level3() {
        // Add 2 balls
        BallController.getInstance().addBall(new Ball(300, 100, 12));
        BallController.getInstance().addBall(new Ball(500, 100, 12));

        int rows = 7; // more rows
        int cols = 12; // more columns
        double brickWidth = 60; // smaller bricks
        double brickHeight = 40;
        double startX = 20;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        startX + col * (brickWidth + 5),
                        startY + row * (brickHeight + 5),
                        brickWidth, brickHeight);
                BrickController.getInstance().addBrick(brick);
            }
        }

    }



}

    public static void level2() {
        BallController.getInstance().addBall(new Ball(400, 100, 10));

        int rows = 5; // pyramid height
        double brickWidth = 70;
        double brickHeight = 50;
        double startX = 35;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            int bricksInRow = rows - row; // pyramid decreases each row
            double offsetX = startX + row * (brickWidth / 2); // center pyramid
            for (int col = 0; col < bricksInRow; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        offsetX + col * (brickWidth + 5),
                        startY + row * (brickHeight + 5),
                        brickWidth, brickHeight);
                BrickController.getInstance().addBrick(brick);
            }
        }
    }

    public static void level3() {
        // Add 2 balls
        BallController.getInstance().addBall(new Ball(300, 100, 12));
        BallController.getInstance().addBall(new Ball(500, 100, 12));

        int rows = 7; // more rows
        int cols = 12; // more columns
        double brickWidth = 60; // smaller bricks
        double brickHeight = 40;
        double startX = 20;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick brick = BrickFactory.createRandomBrick(
                        startX + col * (brickWidth + 5),
                        startY + row * (brickHeight + 5),
                        brickWidth, brickHeight);
                BrickController.getInstance().addBrick(brick);
            }
        }

    }



}