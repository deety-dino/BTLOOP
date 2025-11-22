package gameobjects.Controller;

import gameobjects.powerup.*;
import gameobjects.powerup.PowerUp;
import javafx.scene.text.Text;
import mng.SceneManager;

import java.util.ArrayList;

public class PowerUpController implements ObjectController {
    private static PowerUpController PowerUpController;

    private final ArrayList<PowerUp> powerUps;
    private double widePaddle_time;
    private double speedBall_time;
    private boolean active;

    public static PowerUpController getInstance() {
        if (PowerUpController == null) {
            PowerUpController = new PowerUpController();
        }
        return PowerUpController;
    }

    private PowerUpController() {
        widePaddle_time = 0.0;
        speedBall_time = 0.0;
        active = false;
        powerUps = new ArrayList<>();
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void refresh() {
        powerUps.clear();
    }

    public void setActive(PowerUp powerUp) {
        if (powerUp instanceof WidePaddle_PowerUp) { widePaddle_time = powerUp_time;}
        if (powerUp instanceof SpeedBall_PowerUp) { speedBall_time = powerUp_time;}
        if (powerUp instanceof MultiBall_PowerUp) {active = true;}
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void update(double time) {
        if (widePaddle_time > 0) {
            widePaddle_time -= time;
            if (widePaddle_time > 0) {
                PowerUp.widePaddle_Activation();
            } else {
                PowerUp.widePaddle_Deactivation();
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
            active = false;
            PowerUp.multiBall_Activation();
        }
        if(!powerUps.isEmpty()){
            for (PowerUp powerUp : powerUps) {
                powerUp.update(time);
                if (powerUp.getY() > SceneManager.height + 5) {
                    powerUps.remove(powerUp);
                    break;
                } else if (powerUp.intersects(PaddleController.getInstance().getPaddle())) {
                    setActive(powerUp);
                    powerUps.remove(powerUp);
                    break;
                }
            }
        }
    }

    private String toString(double a) {
        double var = (a > 0) ? a : 0;
        return String.format("%.2fs", var);
    }

    public void getText(Text laser, Text wide, Text speed) {
        speed.setText(toString(speedBall_time));
        wide.setText(toString(widePaddle_time));
    }


    public boolean isActive() {
        return active;
    }
}
