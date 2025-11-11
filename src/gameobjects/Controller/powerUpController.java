package gameobjects.Controller;

import gameobjects.Ball.Ball;
import gameobjects.paddle.Paddle;
import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.scene.text.Text;
import mng.gameInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class PowerUpController implements objectInfo {
    private static PowerUpController powerUpController;
    private ArrayList<PowerUp> powerUps;
    private final double powerUp_time = 5;
    private double widePaddle_time;
    private double speedBall_time;
    private boolean active;
    private double laserPaddle_time;

    public static PowerUpController getInstance() {
        if (powerUpController == null) {
            powerUpController = new PowerUpController();
        }
        return powerUpController;
    }
    private PowerUpController() {
        widePaddle_time = 0.0;
        speedBall_time = 0.0;
        active = false;
        laserPaddle_time = 0.0;
        powerUps = new ArrayList<>();
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
    public void refresh() {
        powerUps.clear();
    }
    public void setActive(PowerUp powerUp) {
        switch (powerUp.getType()) {
            case WIDE_PADDLE: {
                widePaddle_time = powerUp_time;
                break;
            }
            case SPEED_BALL: {
                speedBall_time = powerUp_time;
                break;
            }
            case MULTI_BALL: {
                active = true;
                break;
            }
            case LASER_PADDLE: {
                laserPaddle_time = powerUp_time;
                break;
            }

        }
    }

    public void addPowerUp(PowerUp powerUp, Group root) {
        powerUps.add(powerUp);
        root.getChildren().add(powerUp.getNode());
    }

    public void update(Group root, double time) {
        if(laserPaddle_time > 0) {
            laserPaddle_time -= time;
            if(laserPaddle_time > 0) {
                PowerUp.laserPaddle_Activation();
            }
        }
        if (widePaddle_time > 0) {
            widePaddle_time -= time;
            if (widePaddle_time > 0) {
                PowerUp.widePaddle_Activation(PaddleController.getInstance().getPaddle());
            } else {
                PowerUp.widePaddle_Deactivation(PaddleController.getInstance().getPaddle());
            }
        }
        if (speedBall_time > 0) {
            speedBall_time -= time;
            if(speedBall_time > 0) {
                PowerUp.speedBall_Activation();
            } else {
                PowerUp.speedBall_Deactivation();
            }
        }
        if(active) {
            active = false;
            PowerUp.multiBall_Activation();
        }
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp powerUp = it.next();
            powerUp.update(time);
            if (powerUp.getY() > gameInfo.height + 50) {
                powerUps.remove(it);
                root.getChildren().remove(powerUp.getNode());
            }
            if (powerUp.intersects(PaddleController.getInstance().getPaddle())) {
                setActive(powerUp);
                powerUps.remove(it);
                root.getChildren().remove(powerUp.getNode());
            }
        }
    }
    private String toString(double a) {
        double var = (a > 0) ? a : 0;
        return String.format("%.2fs", var);
    }
    public void getText(Text laser, Text wide, Text speed) {
        laser.setText(toString(laserPaddle_time));
        speed.setText(toString(speedBall_time));
        wide.setText(toString(widePaddle_time));
    }
}
