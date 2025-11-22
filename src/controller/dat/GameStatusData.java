package controller.dat;

public class GameStatusData {
    private static GameStatusData instance;
    private int score;
    private int highScore;
    private int heart;

    private enum GameState {
        LOADING,
        PLAYING,
        PAUSE,
        VICTORY,
        GAME_OVER,
    }

    private GameState state;
    private boolean pause;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    private GameStatusData() {
        this.score = 0;
        state = GameState.LOADING;
        this.pause = false;
        this.isLeftPressed = false;
        this.isRightPressed = false;
    }
    public static GameStatusData getInstance() {
        if (instance == null) {
            instance = new GameStatusData();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {return highScore;}

    public void setHighScore(int highScore) {this.highScore = highScore;}

    public int getHeart() {return heart;}

    public void setHeart(int heart) {this.heart = heart;}

    public boolean isPause() {
        return pause;
    }

    public void setPause( ) {
        this.pause = !this.pause;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        isLeftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        isRightPressed = rightPressed;
    }

    public void setState_PAUSE() {state=GameState.PAUSE;}

    public void setState_LOADING() {state=GameState.LOADING;}

    public void setState_PLAYING() {state=GameState.PLAYING;}

    public void setState_VICTORY() {state=GameState.VICTORY;}

    public void setState_GAME_OVER() {state=GameState.GAME_OVER;}

    public boolean isState_LOADING() {return state == GameState.LOADING;}

    public boolean isState_PLAYING() {return state == GameState.PLAYING;}

    public boolean isState_VICTORY() {return state == GameState.VICTORY;}

    public boolean isState_GAME_OVER() {return state == GameState.GAME_OVER;}

    public boolean isState_PAUSE() {return state == GameState.PAUSE;}

    public void reset() {
        this.score = 0;
        this.highScore = 0;
        state = GameState.LOADING;
        this.pause = false;
        this.isLeftPressed = false;
        this.isRightPressed = false;
    }
}
