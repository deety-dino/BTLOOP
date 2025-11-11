package gameobjects.Controller;

import gameobjects.Brick.Brick;
import javafx.scene.Group;

import java.util.ArrayList;

public class BrickController implements objectInfo {
    private static BrickController instance;
    private ArrayList<Brick> bricks;

    private BrickController() {
        bricks = new ArrayList<>();
    }
    public static BrickController getInstance() {
        if (instance == null) {
            instance = new BrickController();
        }
        return instance;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }
    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }
    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    @Override
    public void update(Group root, double time) {

    }

    @Override
    public void refresh() {
        bricks.clear();
    }
}
