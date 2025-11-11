package mng;

import javafx.fxml.FXMLLoader;
import main.Arkanoid;

public interface gameInfo {
    public int height = 600;
    public int width = 800;

    public FXMLLoader loginFXML = new FXMLLoader(Arkanoid.class.getResource("/controller/fxml/login.fxml"));
    public FXMLLoader levelFXML = new FXMLLoader(Arkanoid.class.getResource("/controller/fxml/levelSelection.fxml"));
    public FXMLLoader playFXML = new FXMLLoader(Arkanoid.class.getResource("/controller/fxml/play.fxml"));
}
