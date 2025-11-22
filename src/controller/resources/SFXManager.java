package controller.resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.Arkanoid;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SFXManager   {
    private static SFXManager instance;
    private MediaPlayer SFX_BrickBreaker;
    private MediaPlayer SFX_Contact;
    private MediaPlayer SFX_PowerUp;
    private MediaPlayer SFX_LobbyTheme;
    private MediaPlayer SFX_LostTheme;
    private MediaPlayer SFX_OST1;
    private MediaPlayer SFX_OST2;
    private MediaPlayer SFX_OST3;

    private SFXManager() {
        loadResources();
    }

    public static SFXManager getInstance() {
        if (instance == null) {
            instance = new SFXManager();
        }
        return instance;
    }


    private void loadResources() {
        try {
            SFX_BrickBreaker = load("/sfx/InGameFX/BrickBreak.wav");
            SFX_Contact = load("/sfx/InGameFX/Contact.wav");
            SFX_PowerUp = load("/sfx/InGameFX/PowerUp.wav");
            SFX_LobbyTheme = load("/sfx/LobbyTheme.wav");
            SFX_LostTheme = load("/sfx/LostTheme.wav");
            SFX_OST1 = load("/sfx/OST1.wav");
            SFX_OST2 = load("/sfx/OST2.wav");
            SFX_OST3 = load("/sfx/OST3.wav");
            System.out.println("SFX resources load successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer load(String filePath) throws IOException {
        URL url = Arkanoid.class.getResource(filePath);
        Media media = new Media(url.toString());
        return new MediaPlayer(media);
    }

    public MediaPlayer getSFX_BrickBreaker() {return SFX_BrickBreaker;}

    public MediaPlayer getSFX_Contact() {
        return SFX_Contact;
    }

    public MediaPlayer getSFX_PowerUp() {
        return SFX_PowerUp;
    }

    public MediaPlayer getSFX_LobbyTheme() {
        return SFX_LobbyTheme;
    }

    public MediaPlayer getSFX_LostTheme() {
        return SFX_LostTheme;
    }

    public MediaPlayer getSFX_OST1() {
        return SFX_OST1;
    }

    public MediaPlayer getSFX_OST2() {
        return SFX_OST2;
    }

    public MediaPlayer getSFX_OST3() {
        return SFX_OST3;
    }
}
