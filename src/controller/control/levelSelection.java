package controller.control;

import controller.dat.dat;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import mng.gameManager;
import user.User;

import java.io.IOException;

public class levelSelection {
    @FXML
    protected Pane root;

    @FXML
    protected void loadLevel1() throws IOException {
        try {
            gameManager.letShow(dat.ingameStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void loadLevel2() throws IOException {
        try {
            gameManager.letShow(dat.ingameStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void loadLevel3() throws IOException {
        try {
            gameManager.letShow(dat.ingameStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
