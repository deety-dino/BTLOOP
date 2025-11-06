package user;

public class User {
    private static User instance;
    private String name;
    private int highScore;
    private int level;
    private int selectedLevel;

    private boolean selected;
    private User(String name, int highScore, int level) {
        this.name = name;
        this.highScore = highScore;
        this.level = level;
        this.selected = false;
    }
    public static User getInstance() {
        return instance;
    }
    public static void login(String username, String password) {
        instance = new User(username, 1000, 3);
    }
    public int getSelectedLevel() {
        return selectedLevel;
    }

    public boolean isSelected() {
        return selected;
    }
    public static void setSelectedLevel(int selectedLevel) {
        instance.selectedLevel = selectedLevel;
    }
    public static void setSelected(boolean selected) {
        instance.selected = selected;
    }
}
