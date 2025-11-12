package gameobjects.powerup;
import gameobjects.Ball.Ball;
import gameobjects.Controller.BallController;
import gameobjects.Controller.objectInfo;
import gameobjects.GameObject;
import gameobjects.paddle.Paddle;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Random;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private double fallSpeed;
    private final Random random = new Random();

    public enum PowerUpType {
        WIDE_PADDLE,
        SPEED_BALL,
        MULTI_BALL,
        LASER_PADDLE
    }

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;

        // Try to load power-up image based on type
        String imagePath = getPowerUpImagePath(type);
        Color fallbackColor = getPowerUpColor(type);

        try {
            Image powerUpImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(powerUpImage);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            imageView.setPreserveRatio(false);
            shape = imageView;
        } catch (Exception e) {
            System.out.println("Power-up image not found: " + imagePath + ", using fallback");
            e.printStackTrace();

            // Fallback to colored rectangle
            Rectangle rect = new Rectangle(20, 20);
            rect.setFill(fallbackColor);
            rect.setArcWidth(10);
            rect.setArcHeight(10);
            rect.setStroke(Color.WHITE);
            rect.setStrokeWidth(2);
            shape = rect;
        }

        // Position the shape according to the GameObject position
        shape.setLayoutX(position.getX());
        shape.setLayoutY(position.getY());

        // Randomize initial fall speed slightly
        fallSpeed = objectInfo.BASE_FALL_SPEED * (0.8 + random.nextDouble() * 0.4);
    }

    private String getPowerUpImagePath(PowerUpType type) {
        switch (type) {
            case WIDE_PADDLE:
                return "/gfx/power-up/PaddleSize.png";
            case SPEED_BALL:
                return "/gfx/power-up/Speed.png";
            case MULTI_BALL:
                return "/gfx/power-up/double.png";  // You'll need to add this image
            case LASER_PADDLE:
                return "/gfx/power-up/bullet.png";  // You'll need to add this image
            default:
                return "/gfx/power-up/PaddleSize.png";
        }
    }

    private Color getPowerUpColor(PowerUpType type) {
        switch (type) {
            case WIDE_PADDLE:
                return Color.YELLOW;
            case SPEED_BALL:
                return Color.RED;
            case MULTI_BALL:
                return Color.GREEN;
            case LASER_PADDLE:
                return Color.PURPLE;
            default:
                return Color.WHITE;
        }
    }

    @Override
    public void update(double time) {
        getPosition().setPosition(position.getX(), position.getY() + fallSpeed * time);
        shape.setLayoutX(getPosition().getX());
        shape.setLayoutY(getPosition().getY());
    }

    //Wide Paddle powerup
    public static void widePaddle_Activation(Paddle paddle) {
        if (paddle.getNode() instanceof Rectangle) {
            Rectangle paddleShape = (Rectangle) paddle.getNode();
            paddleShape.setWidth(objectInfo.defaultPaddleSize * 1.5);
        } else if (paddle.getNode() instanceof ImageView) {
            ImageView paddleImage = (ImageView) paddle.getNode();
            paddleImage.setFitWidth(objectInfo.defaultPaddleSize * 1.5);
        }
    }

    public static void widePaddle_Deactivation(Paddle paddle) {
        if (paddle.getNode() instanceof Rectangle) {
            Rectangle paddleShape = (Rectangle) paddle.getNode();
            paddleShape.setWidth(objectInfo.defaultPaddleSize * 1.0);
        } else if (paddle.getNode() instanceof ImageView) {
            ImageView paddleImage = (ImageView) paddle.getNode();
            paddleImage.setFitWidth(objectInfo.defaultPaddleSize * 1.0);
        }
    }

    //Speed Ball powerup
    public static void speedBall_Activation() {
        for (Ball ball : BallController.getInstance().getBalls()) {
            ball.setVe_Multi(1.5);
        }
    }

    public static void speedBall_Deactivation() {
        for (Ball ball : BallController.getInstance().getBalls()) {
            ball.setVe_Multi(1.0);
        }
    }

    //Multi Ball powerup
    public static void multiBall_Activation() {
        ArrayList<Ball> balls = BallController.getInstance().getBalls();
        int i = balls.size() - 1;
        while (balls.size() < 1000 && i >= 0) {
            try {
                Ball ball = balls.get(i).getCopy();
                ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
                BallController.getInstance().addBall(ball);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            i--;
        }
    }

    //Laser Paddle powerup
    public static void laserPaddle_Activation() {
    }

    public PowerUpType getType() {
        return type;
    }
}
