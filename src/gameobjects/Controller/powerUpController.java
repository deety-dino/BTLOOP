package gameobjects.controller;

import gameobjects.powerup.PowerUp;
import javafx.scene.text.Text;
import mng.gameInfo;

import java.util.ArrayList;

public class PowerUpController implements gameobjects.controller.objectInfo {

    private static PowerUpController powerUpController;
    private ArrayList<PowerUp> powerUps;
    private final double powerUp_time = 5;
    private volatile double widePaddle_time;
    private volatile double speedBall_time;
    private volatile boolean active;
    private volatile double laserPaddle_time;

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

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void update(double time) {
        if (laserPaddle_time > 0) {
            laserPaddle_time -= time;
            if (laserPaddle_time > 0) {
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
            if (speedBall_time > 0) {
                PowerUp.speedBall_Activation();
            } else {
                PowerUp.speedBall_Deactivation();
            }
        }
        if (isActive()) {
            setActive(false);
            PowerUp.multiBall_Activation();
        }
        for(int i = 0; i <  powerUps.size(); i++) {
            powerUps.get(i).update(time);
            if(powerUps.get(i).getY() > gameInfo.height + 5) {
                powerUps.remove(i);
                i--;
            } else if (powerUps.get(i).intersects(PaddleController.getInstance().getPaddle())) {
                setActive(powerUps.get(i));
                powerUps.remove(i);
                i--;
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

    public double getLaserPaddle_time() {
        return laserPaddle_time;
    }

    public void setLaserPaddle_time(double laserPaddle_time) {
        this.laserPaddle_time = laserPaddle_time;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getSpeedBall_time() {
        return speedBall_time;
    }

    public void setSpeedBall_time(double speedBall_time) {
        this.speedBall_time = speedBall_time;
    }

    public double getWidePaddle_time() {
        return widePaddle_time;
    }

    public void setWidePaddle_time(double widePaddle_time) {
        this.widePaddle_time = widePaddle_time;
    }
}
