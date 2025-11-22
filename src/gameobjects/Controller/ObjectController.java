package gameobjects.Controller;

public interface ObjectController {
    int BALL_VELOCITY = 200;

    double BASE_FALL_SPEED = 3;

    int defaultPaddleWidth = 100;
    int defaultPaddleHeight = 20;
    int paddleVelocity = 200;

    double powerUp_time = 5.0;

    void update(double delta);
    void refresh();
}
