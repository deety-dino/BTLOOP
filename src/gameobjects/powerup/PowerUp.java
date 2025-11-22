package gameobjects.powerup;

import gameobjects.Ball.Ball;
import gameobjects.Controller.BallController;
import gameobjects.Controller.ObjectController;
import gameobjects.Controller.PaddleController;
import gameobjects.GameObject;


public abstract class PowerUp extends GameObject   {
    public PowerUp(double x, double y) {
        super(x, y, 16, 16);
    }


    @Override
    public void update(double dt) {
        position.setPosition(position.getX(), position.getY() + ObjectController.BASE_FALL_SPEED);
        shape.setLayoutX(position.getX());
        shape.setLayoutY(position.getY());
    }

    public static void multiBall_Activation() {
        for(int i = BallController.getInstance().getBalls().size() - 1; i >= 0; i--) {
            Ball ball = BallController.getInstance().getBalls().get(i).getCopy();
            ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
            BallController.getInstance().getBalls().add(ball);
        }
    }

    public static void speedBall_Activation() {
        if(!BallController.getInstance().isEmpty()){
            for (Ball ball : BallController.getInstance().getBalls()) {
                ball.setVe_Multi(2);
            }
        }
    }

    public static void speedBall_Deactivation() {
        if(!BallController.getInstance().isEmpty()){
            for (Ball ball : BallController.getInstance().getBalls()) {
                ball.setVe_Multi(1);
            }
        }
    }

    public static void widePaddle_Activation() {
        PaddleController.getInstance().getPaddle().getNode().setScaleX(1.5);
    }

    public static void widePaddle_Deactivation() {
        PaddleController.getInstance().getPaddle().getNode().setScaleX(1);
    }
}
