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
    private Ball ball;
    private List<Brick> bricks = new ArrayList<>();
    private AnimationTimer gameLoop;

    @FXML
    public void initialize() {
        //Create game objects
        paddle = new Paddle(350, 550, 100, 15);
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
            if (e.getCode() == KeyCode.LEFT) {
                paddle.moveLeft();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                paddle.moveRight(root.getWidth());
            }
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

    private void updateGame() {
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
            ball.bounceY();
        }

        //Brick collision
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (ball.intersects(brick)) {
                ball.bounceY();
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
