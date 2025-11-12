package gameobjects.controller;

import controller.dat.IngameData;
import gameobjects.laser.Laser;
import java.util.ArrayList;

public class LaserController implements objectInfo {
    private static LaserController instance;
    private ArrayList<Laser> lasers;
    private IngameData gameData;

    private LaserController() {
        lasers = new ArrayList<>();
    }

    public void setGameData(IngameData gameData) {
        this.gameData = gameData;
    }

    public static LaserController getInstance() {
        if (instance == null) {
            instance = new LaserController();
        }
        return instance;
    }

    public void addLaser(Laser laser) {
        lasers.add(laser);
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    @Override
    public void refresh() {
        lasers.clear();
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < lasers.size(); i++) {
            Laser laser = lasers.get(i);
            laser.update(time);

            if (laser.isOffScreen()) {
                lasers.remove(i);
                i--;
                continue;
            }

            BrickController brickController = BrickController.getInstance();
            for (int j = 0; j < brickController.getBricks().size(); j++) {
                if (laser.intersects(brickController.getBricks().get(j))) {
                    brickController.getBricks().get(j).hit();
                    if (brickController.getBricks().get(j).isDestroyed()) {
                        if (gameData != null) {
                            gameData.addScore(brickController.getBricks().get(j).getScoreValue());
                        }
                        brickController.getBricks().remove(j);
                    }
                    lasers.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
}
