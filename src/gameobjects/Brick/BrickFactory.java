package gameobjects.Brick;

import java.util.Random;

public class BrickFactory {
    private static final Random random = new Random();

    //Create brick by type
    public static Brick createBrick(String type, double x, double y, double w, double h) {
        switch (type.toLowerCase()) {
            case "normal":
                return new NormalBrick(x, y, w, h);
            case "strong":
                return new StrongBrick(x, y, w, h);
            case "powerup":
                return new PowerUpBrick(x, y, w, h);
            default:
                throw new IllegalArgumentException("Unknown brick type: " + type);
        }
    }

    //Random - Cải thiện tỷ lệ powerup và cân bằng hơn
    public static Brick createRandomBrick(double x, double y, double width, double height) {
        Random rand = new Random();
        int choice = rand.nextInt(10); // tweak ratios
        if (choice < 4) return createBrick("normal", x, y, width, height);      // 40% Normal
        else if (choice < 7) return createBrick("strong", x, y, width, height); // 30% Strong
        else return createBrick("powerup", x, y, width, height);                // 30% PowerUp (tăng từ 10% lên 30%)
    }

}
