package controller.dat;


import gameobjects.powerup.PowerUp;
import javafx.scene.Group;
import javafx.scene.text.Text;

import gameobjects.Ball.Ball;
import gameobjects.Brick.Brick;
import user.UserManager;


// Removed unused import

public class GameData {
    private static GameData instance;
    private final Group root;
    private GameStatusData status;
    private ObjectData object;

    //Constructor
    private GameData(Group root) {
        this.root = root;

        this.status = GameStatusData.getInstance();
        this.object = ObjectData.getInstance();
    }

    public static GameData getInstance(Group root) {
        if (instance == null) {
            instance = new GameData(root);
        }
        return instance;
    }

    public GameStatusData getStatus() {
        return status;
    }

    public ObjectData getObject() {
        return object;
    }

    public void getPowerUpText(Text laser, Text wide, Text speed) {
        object.getPowerUps().getText(laser, wide, speed);
    }

    public void getStatText(Text score, Text highScore, Text heart) {
        score.setText("Score: " + status.getScore());
        highScore.setText("High Score: " + status.getHighScore());
        heart.setText("Heart: " + status.getHeart());
    }
    public void loadData(int level) {
        refesh();
        status.setHighScore(UserManager.getInstance().getUser().getHighScore());
        switch (level) {
            case 1 -> {
                LevelData.level1();
                break;
            }
            case 2 -> {
                LevelData.level2();
                break;
            }
            case 3 -> {
                LevelData.level3();
                break;
            }
            default -> System.out.println("Coming soon...");
        }
        getGroup();
        status.setState_PLAYING();
    }

    public void refesh() {
        root.getChildren().removeAll(root.getChildren());
        status.reset();
        object.reset();
    }

    public void update(double time) {
        if (object.getBalls().isEmpty()) {
            status.setState_GAME_OVER();
            UserManager.getInstance().getUser().setHighScore(status.getHighScore());
        } else if (object.getBricks().isEmpty()) {
            status.setState_VICTORY();
            UserManager.getInstance().getUser().setHighScore(status.getHighScore());
        } else if (!status.isPause()) {
            status.setHighScore(Math.max(status.getHighScore(), status.getScore()));
            object.update(time);
        }
    }

    public void getGroup() {
        root.getChildren().removeAll(root.getChildren());
        for (Ball ball : object.getBalls().getBalls()) {
            root.getChildren().add(ball.getNode());
        }
        for (Brick brick : object.getBricks().getBricks()) {
            root.getChildren().add(brick.getNode());
        }
        for (PowerUp powerUp : object.getPowerUps().getPowerUps()) {
            root.getChildren().add(powerUp.getNode());
        }
        root.getChildren().add(object.getPaddle().getPaddle().getNode());
    }
}
