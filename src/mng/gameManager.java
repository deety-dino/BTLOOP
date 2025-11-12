package mng;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Arkanoid;
import user.User;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class gameManager {
    private static final Object lock = new Object();
    private AnimationTimer loop;
    private static gameManager gameManager;
    private static FXMLLoader[] fxmlLoader = new FXMLLoader[3];
    private static Scene[] scene = new Scene[3];
    private static Scene primaryScene;
    private static Stage stage;
    private static User user;

    // BGM MediaPlayers
    private static MediaPlayer currentBGM;
    private static MediaPlayer loginBGM;
    private static MediaPlayer lobbyBGM;
    private static MediaPlayer[] gameBGMs = new MediaPlayer[3]; // Array for 3 OSTs
    private static Random random = new Random();

    public enum ApplicationState {
        LOGIN_SCREEN,
        LEVEL_SELECTION_SCREEN,
        IN_GAME_SCREEN
    };

    public static ApplicationState State;

    // ... existing getInstance and constructor code ...

    public static gameManager getInstance(Stage stage) {
        if (gameManager == null) {
            synchronized (lock) {
                if (gameManager == null) {
                    gameManager = new gameManager(stage);
                }
            }
        }
        return gameManager;
    }

    private gameManager(Stage stage) {
        this.State = ApplicationState.LOGIN_SCREEN;
        gameManager.stage = stage;
    }

    public void loadResource() throws IOException {
        User.initialize();

        // Load scenes
        try {
            scene[0] = new Scene(gameInfo.loginFXML.load(), 800, 600);
            System.out.println("Login scene loaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load login scene: " + e.getMessage());
            throw e;
        }

        try {
            scene[1] = new Scene(gameInfo.levelFXML.load(), 800, 600);
            System.out.println("Level selection scene loaded successfully!");
        } catch (IOException e)  {
            System.err.println("Failed to load level selection scene: " + e.getMessage());
            throw e;
        }

        try {
            scene[2] = new Scene(gameInfo.playFXML.load(), 800, 600);
            System.out.println("Play scene loaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load play scene: " + e.getMessage());
            throw e;
        }

        // Load BGM
        loadBGM();
    }

    private void loadBGM() {
        try {
            // Load login BGM (Lost theme.wav)
            URL loginURL = getClass().getResource("/sfx/Lobby theme.wav");
            if (loginURL != null) {
                Media loginMedia = new Media(loginURL.toString());
                loginBGM = new MediaPlayer(loginMedia);
                loginBGM.setCycleCount(MediaPlayer.INDEFINITE);
                loginBGM.setVolume(0.5);
            }

            // Load lobby/level selection BGM (Lobby theme.wav)
            URL lobbyURL = getClass().getResource("/sfx/Lobby theme.wav");
            if (lobbyURL != null) {
                Media lobbyMedia = new Media(lobbyURL.toString());
                lobbyBGM = new MediaPlayer(lobbyMedia);
                lobbyBGM.setCycleCount(MediaPlayer.INDEFINITE);
                lobbyBGM.setVolume(0.5);
            }

            // Load all 3 in-game OSTs
            String[] ostFiles = {"/sfx/OST1.wav", "/sfx/OST2.wav", "/sfx/OST3.wav"};
            for (int i = 0; i < ostFiles.length; i++) {
                URL ostURL = getClass().getResource(ostFiles[i]);
                if (ostURL != null) {
                    Media ostMedia = new Media(ostURL.toString());
                    gameBGMs[i] = new MediaPlayer(ostMedia);
                    gameBGMs[i].setCycleCount(MediaPlayer.INDEFINITE);
                    gameBGMs[i].setVolume(0.5);
                }
            }

            System.out.println("BGM loaded successfully!");
        } catch (Exception e) {
            System.err.println("Failed to load BGM: " + e.getMessage());
        }
    }

    private static void playBGM(MediaPlayer newBGM) {
        // Stop current BGM if playing
        if (currentBGM != null) {
            currentBGM.stop();
        }

        // Play new BGM
        if (newBGM != null) {
            currentBGM = newBGM;
            currentBGM.seek(Duration.ZERO);
            currentBGM.play();
        }
    }

    private static void playRandomGameBGM() {
        // Pick a random OST from the 3 available
        int randomIndex = random.nextInt(3);
        MediaPlayer selectedBGM = gameBGMs[randomIndex];

        if (selectedBGM != null) {
            System.out.println("Playing OST" + (randomIndex + 1) + ".wav");
            playBGM(selectedBGM);
        }
    }

    public static void letShow() throws IOException {
        switch(State) {
            case LOGIN_SCREEN ->  {
                stage.setScene(scene[0]);
                playBGM(loginBGM);
                stage.show();
            }
            case LEVEL_SELECTION_SCREEN ->  {
                stage.setScene(scene[1]);
                playBGM(lobbyBGM);
                stage.show();
            }
            case IN_GAME_SCREEN ->  {
                stage.setScene(scene[2]);
                playRandomGameBGM(); // Randomly select one of the 3 OSTs
                stage.show();
            }
        }
    }

    // Optional: Methods to control BGM
    public static void pauseBGM() {
        if (currentBGM != null) {
            currentBGM.pause();
        }
    }

    public static void resumeBGM() {
        if (currentBGM != null) {
            currentBGM.play();
        }
    }

    public static void stopBGM() {
        if (currentBGM != null) {
            currentBGM.stop();
        }
    }

    public static void setBGMVolume(double volume) {
        if (currentBGM != null) {
            currentBGM.setVolume(Math.max(0.0, Math.min(1.0, volume)));
        }
        // Also set volume for all game BGMs
        for (MediaPlayer bgm : gameBGMs) {
            if (bgm != null) {
                bgm.setVolume(Math.max(0.0, Math.min(1.0, volume)));
            }
        }
        // Set volume for login and lobby BGMs
        if (loginBGM != null) loginBGM.setVolume(Math.max(0.0, Math.min(1.0, volume)));
        if (lobbyBGM != null) lobbyBGM.setVolume(Math.max(0.0, Math.min(1.0, volume)));
    }
}
