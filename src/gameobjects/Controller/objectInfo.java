package gameobjects.Controller;

import javafx.scene.Group;

public interface objectInfo {
    public int ballVelocity = 200;
    public int paddleVelocity = 200;

    public double BASE_FALL_SPEED = 150.0;

    public int defaultPaddleSize = 100;

    public void update(double delta);
    public void refresh();
}
