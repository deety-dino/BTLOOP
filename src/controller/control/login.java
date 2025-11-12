package controller.control;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import mng.gameManager;
import user.User;

public class login {

    @FXML
    protected Pane root;
    @FXML
    protected Group loginField, signUpField ;
    @FXML
    protected TextField lUserName, lPassword, sUserName, sPassword, sRePassword;


    @FXML
    protected void initialize() {
        loginField.setVisible(true);
        signUpField.setVisible(false);
    }
    @FXML
    protected void lLogin() {
        try {
            User.login(lUserName.getText(), lPassword.getText());
            gameManager.State = gameManager.ApplicationState.LEVEL_SELECTION_SCREEN;
            gameManager.letShow();
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return;
        }
    }

    @FXML
    protected void lSignUp() {
        loginField.setVisible(false);
        signUpField.setVisible(true);
    }

    @FXML
    protected void sLogin() {
        loginField.setVisible(true);
        signUpField.setVisible(false);
    }

    @FXML
    protected void sSignUp() {
        try {
            User.signup(sUserName.getText(), sPassword.getText(), sRePassword.getText());
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return;
        }
    }
}
