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
        paddle = new Paddle(350, 550, 100, 15);
        ball = new Ball(400, 300, 8);
        createBricks();

        root.getChildren().addAll(
                paddle.getNode(),
                ball.getNode()
        );

        for (Brick brick : bricks) {
            root.getChildren().add(brick.getNode());
        }




    }





}
