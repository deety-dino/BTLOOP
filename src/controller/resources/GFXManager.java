package controller.resources;

import javafx.scene.image.Image;
import main.Arkanoid;

import java.io.IOException;

public class GFXManager  {
    private static GFXManager instance;
    private Image GFX_Ball;
    private Image GFX_Paddle;
    private Image GFX_DEFAULT_BRICK;
    private Image GFX_STRONG_BRICK;
    private Image GFX_POWERUP_BRICK;
    private Image GFX_MULTIBALL_POWERUP;
    private Image GFX_SPEEDBALL_POWERUP;
    private Image GFX_WIDE_PADDLE_POWERUP;
    private Image GFX_LASER_PADDLE_POWERUP;

    private GFXManager(){
        loadResources();
    }

    public static GFXManager getInstance(){
        if(instance == null){
            instance = new GFXManager();
        }
        return instance;
    }

    private void loadResources() {
        try {
            GFX_Ball = load("/gfx/ball/Ball.png");
            GFX_Paddle = load("/gfx/pad/pad.png");
            GFX_DEFAULT_BRICK = load("/gfx/brick/brick1.png");
            GFX_STRONG_BRICK = load("/gfx/brick/brick2.png");
            GFX_POWERUP_BRICK = load("/gfx/brick/brick3.png");
            GFX_MULTIBALL_POWERUP = load("/gfx/power-up/double.png");
            GFX_WIDE_PADDLE_POWERUP = load("/gfx/power-up/WidePaddle.png");
            GFX_SPEEDBALL_POWERUP = load("/gfx/power-up/Speed.png");
            GFX_LASER_PADDLE_POWERUP = load("/gfx/power-up/LaserPaddle.png");
            System.out.println("GFX resources load successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image load (String filePath) throws IOException {
        return new Image(Arkanoid.class.getResourceAsStream(filePath));
    }

    public Image getGFX_Ball() {
        return GFX_Ball;
    }

    public Image getGFX_Paddle() {
        return GFX_Paddle;
    }

    public Image getGFX_DEFAULT_BRICK() {
        return GFX_DEFAULT_BRICK;
    }

    public Image getGFX_STRONG_BRICK() {
        return GFX_STRONG_BRICK;
    }

    public Image getGFX_POWERUP_BRICK() {
        return GFX_POWERUP_BRICK;
    }

    public Image getGFX_MULTIBALL_POWERUP() {
        return GFX_MULTIBALL_POWERUP;
    }

    public Image getGFX_SPEEDBALL_POWERUP() {
        return GFX_SPEEDBALL_POWERUP;
    }

    public Image getGFX_WIDE_PADDLE_POWERUP() {
        return GFX_WIDE_PADDLE_POWERUP;
    }

    public Image getGFX_LASER_PADDLE_POWERUP() {
        return GFX_LASER_PADDLE_POWERUP;
    }
}
