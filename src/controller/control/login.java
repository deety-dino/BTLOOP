package controller.control;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import mng.SceneManager;
import mng.GameEngine;
import user.UserManager;

public class login {

    @FXML
    protected Pane root;
    @FXML
    protected Group loginField, signUpField;
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
            UserManager.getInstance().login(lUserName.getText(), lPassword.getText());
            GameEngine.sceneState = SceneManager.SceneState.LEVEL_SELECETION_SCENE;
            GameEngine.letShow();
            //lUserName.clear();
            //lPassword.clear();
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
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
            UserManager.getInstance().signup(sUserName.getText(), sPassword.getText(), sRePassword.getText());
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
        }
    }
}
