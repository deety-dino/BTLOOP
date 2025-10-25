package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import gameobjects.GameObject;
import gameobjects.Paddle;
import gameobjects.Ball;
import gameobjects.Brick;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IngameController {

    @FXML
    private AnchorPane root;

    private Paddle paddle;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private Ball ball;
    private List<Brick> bricks = new ArrayList<>();
    private AnimationTimer gameLoop;

    @FXML
    public void initialize() {
        //Create game objects
        paddle = new Paddle(325, 550, 150, 15);
        ball = new Ball(400, 300, 8);
        createBricks();

        //Add to scene
        root.getChildren().addAll(
                paddle.getNode(),
                ball.getNode()
        );

        for (Brick brick : bricks) {
            root.getChildren().add(brick.getNode());
        }

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) leftPressed = true;
            if (e.getCode() == KeyCode.RIGHT) rightPressed = true;
        });

        root.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) leftPressed = false;
            if (e.getCode() == KeyCode.RIGHT) rightPressed = false;
        });

        root.setFocusTraversable(true);
        Platform.runLater(() -> root.requestFocus());

        //Game Loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        gameLoop.start();
    }

    private void createBricks() {
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double offsetX = 35;
        double offsetY = 0;

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

    private void updateGame() {
        // Handle paddle movement
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight(root.getWidth());
        }

        ball.update();

        //Bounce
        if (ball.getX() <= 0 || ball.getX() >= root.getWidth()) {
            ball.bounceX();
        }
        if (ball.getY() <= 0) {
            ball.bounceY();
        }

        //Paddle collision
        if (ball.intersects(paddle)) {
            double ballBottom = ball.getY() + ball.getH() / 2;
            double paddleTop = paddle.getY();

            if (ballBottom <= paddleTop + 5) {
                ball.bounceY();
            } else {
                ball.bounceX();
            }
        }

        //Brick collision
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (ball.intersects(brick)) {

                // Get edges of Ball and Brick
                double ballLeft = ball.getX() - ball.getW() / 2;
                double ballRight = ball.getX() + ball.getW() / 2;
                double ballTop = ball.getY() - ball.getH() / 2;
                double ballBottom = ball.getY() + ball.getH() / 2;
                double brickLeft = brick.getX();
                double brickRight = brick.getX() + brick.getW();
                double brickTop = brick.getY();
                double brickBottom = brick.getY() + brick.getH();

                // Compute overlap distances for every situation
                double overlapLeft = ballRight - brickLeft;
                double overlapRight = brickRight - ballLeft;
                double overlapTop = ballBottom - brickTop;
                double overlapBottom = brickBottom - ballTop;

                // Find the smallest overlap to tell side of collision
                double minOverlapX = Math.min(overlapLeft, overlapRight);
                double minOverlapY = Math.min(overlapTop, overlapBottom);

                if (minOverlapX < minOverlapY) {
                    ball.bounceX(); // X-axis
                } else {
                    ball.bounceY(); // Y-axis
                }

                root.getChildren().remove(brick.getNode());
                it.remove();
                break;
            }
        }

        //Lose game
        if (ball.getY() > root.getHeight()) {
            gameLoop.stop();
            System.out.println("Game Over");
        }


    }


}
