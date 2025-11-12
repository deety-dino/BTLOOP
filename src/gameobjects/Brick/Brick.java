package gameobjects.Brick;

import gameobjects.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;

public abstract class Brick extends GameObject {

    protected int hitPoints;

    public Brick(double x, double y, double w, double h, int hitPoints, Color color) {
        super(x, y, w, h);
        this.hitPoints = hitPoints;

        shape = new Rectangle(w, h, color);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    protected void loadBrickImage(String imagePath, Color fallbackColor) {
        try {
            Image brickImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(brickImage);
            imageView.setFitWidth(size.getWidth());
            imageView.setFitHeight(size.getHeight());
            imageView.setPreserveRatio(false);
            shape = imageView;
            shape.setLayoutX(position.getX());
            shape.setLayoutY(position.getY());
        } catch (Exception e) {
            System.out.println("Brick image not found: " + imagePath + ", using fallback color");
            e.printStackTrace();
            Rectangle rect = new Rectangle(size.getWidth(), size.getHeight(), fallbackColor);
            shape = rect;
            shape.setLayoutX(position.getX());
            shape.setLayoutY(position.getY());
        }
    }

    public void hit() {
        hitPoints--;
        onHit();
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    public void onDestroyed() {}
    protected void onHit() {}

    @Override
    public void update(double time) {

    }

}
