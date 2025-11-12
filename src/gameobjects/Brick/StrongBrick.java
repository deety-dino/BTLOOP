package gameobjects.Brick;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;

public class StrongBrick extends Brick {
    private Image fullHealthImage;
    private Image damagedImage;

    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 2, Color.web("#808080"));

        // Load both brick states
        try {
            fullHealthImage = new Image(getClass().getResourceAsStream("/gfx/brick/Brick2.png"));
            // Return to brick1 sprite after being damaged
            damagedImage = new Image(getClass().getResourceAsStream("/gfx/brick/Brick1.png"));

            ImageView imageView = new ImageView(fullHealthImage);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(false);
            shape = imageView;
            shape.setLayoutX(x);
            shape.setLayoutY(y);
        } catch (Exception e) {
            System.out.println("Strong brick image not found, using fallback");
            e.printStackTrace();
        }
    }

    @Override
    protected void onHit() {
        if (hitPoints == 1) {
            // Change appearance when damaged
            if (shape instanceof ImageView) {
                ((ImageView) shape).setOpacity(0.6); // Make it more transparent
                // Or switch to damaged image if you have one
                // ((ImageView) shape).setImage(damagedImage);
            } else if (shape instanceof Rectangle) {
                ((Rectangle) shape).setFill(Color.web("#B0B0B0"));
            }
        }
    }

    @Override
    public void onDestroyed() {

    }
}
