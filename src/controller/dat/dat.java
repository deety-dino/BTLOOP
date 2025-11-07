package controller.dat;

import javafx.fxml.FXMLLoader;
import main.Arkanoid;

public interface dat {
    int width=800;
    int height=600;
    double ball_velocity = 5;
    double paddle_velocity = 6;

    int loginStatus = 0;
    int levelStatus = 1;
    int ingameStatus = 2;

//    void update();
}
