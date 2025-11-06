package controller.control;

import controller.dat.dat;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import mng.gameManager;
import user.User;

import java.io.IOException;

public class levelSelection {
    @FXML
    protected Pane root;
    @FXML
    protected void loadLevel1() throws IOException {
        User.setSelectedLevel(1);
        User.setSelected(true);
        gameManager.letShow(dat.ingameStatus);
    }
    @FXML
    protected void loadLevel2() {
        User.setSelectedLevel(2);
    }
    @FXML
    protected void loadLevel3() {
        User.setSelectedLevel(3);
    }
}
