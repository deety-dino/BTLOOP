package user;


public class User {
    private final String userName;
    private final String password;
    private final int[] highScore;
    private int level;
    private int selectedLevel;

    private boolean selected;

    protected User(String name, String password, int[] highScore, int level) {
        this.userName = name;
        this.password = password;
        this.highScore = highScore;
        this.level = level;
        this.selected = false;
    }

    public String getUserName() {
        return userName;
    }

    public int getHighScore() {return highScore[selectedLevel - 1];}

    public void setHighScore(int highScore) {
        this.highScore[selectedLevel - 1] = highScore;
    }

    public String getPassword() {
        return password;
    }

    public int getSelectedLevel() {return selectedLevel;}

    public boolean isSelected() {
        return selected;
    }

    public void setLevel (int level) {this.level = (level < 3) ? level : 3;}

    public void setSelectedLevel(int selectedLevel) {this.selectedLevel = selectedLevel;}

    public void setSelected(boolean selected) {this.selected = selected;}
}
