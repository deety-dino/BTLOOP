package mng;

import controller.resources.SFXManager;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer theme;
    private ArrayList<MediaPlayer> soundEffect;

    private SoundManager() {
        theme = SFXManager.getInstance().getSFX_LobbyTheme();
        soundEffect =  new ArrayList<>();
    }
    public static SoundManager getInstance() {
        if (instance == null) {
            SFXManager.getInstance();
            instance = new SoundManager();
        }
        return instance;
    }
    public void playTheme() {
        theme.stop();
        if(GameEngine.sceneState == SceneManager.SceneState.GAME_SCENE) {
            theme = SFXManager.getInstance().getSFX_LobbyTheme();
        } else {
            Random rand = new Random();
            int r = rand.nextInt(3);
            switch (r) {
                case 1 -> {theme = SFXManager.getInstance().getSFX_OST1();}
                case 2 -> {theme = SFXManager.getInstance().getSFX_OST2();}
                default ->  {theme = SFXManager.getInstance().getSFX_OST3();}
            }

        }
        theme.setCycleCount(MediaPlayer.INDEFINITE);
        theme.play();
    }
    public void playBounceEffect() {
        if(soundEffect.size() < 3) {
            MediaPlayer effect = SFXManager.getInstance().getSFX_Contact();
            soundEffect.add(effect);
            effect.setOnEndOfMedia(() -> {
                effect.dispose();
                soundEffect.remove(effect);
            });
            effect.play();
        }
    }
}
