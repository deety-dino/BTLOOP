package user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class User {
    private static ArrayList<User> users;
    private static User instance;
    private String userName;
    private String password;
    private int[] highScore;
    private int level;
    private int selectedLevel;

    private boolean selected;
    private User(String name, String password, int[] highScore, int level) {
        this.userName = name;
        this.password = password;
        this.highScore = highScore;
        this.level = level;
        this.selected = false;
    }
    public static User getInstance() {
        return instance;
    }
    public static void login(String username, String password) throws Exception {
        if (username == "" ) {
            throw new Exception("Please enter username");
        } else if (password == "" ) {
            throw new Exception("Please enter password");
        } else {
            for (int i = 0; i < users.size(); i++) {
                if (username.equals(users.get(i).getUserName())) {
                    if (password.equals(users.get(i).getPassowrd())) {
                        instance = users.get(i);
                        return;
                    } else {
                        throw new Exception("Username or password incorrect");
                    }
                }
            }
            throw new Exception("Not found user!");
        }
    }
    public static void logout() {

    }
    public static void signup(String username, String password, String confirm) throws Exception {
        if (username == "") {
            throw new Exception("Please enter username");
        } else if (password == "") {
            throw new Exception("Please enter password");
        } else if (!confirm.equals(password)) {

            throw new Exception("Passwords do not match" + password + ":" + confirm);
        } else {
            for (int i = 0; i < users.size(); i++) {
                if (username.equals(users.get(i).getUserName())) {
                    throw new Exception("Username already in use");
                }
            }
            int[] newHighScore = new int[] {1,2,3};
            users.add(new User(username, password, newHighScore,1));
            try {
                Gson gson = new Gson();
                FileWriter writer1 = new FileWriter("src/user/data/users.json");
                gson.toJson(users, writer1);
                writer1.close();;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initialize() {
        users = new ArrayList<>();
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("src/user/data/users.json");
            Type playerListType = new TypeToken<ArrayList<User>>(){}.getType();
            users = gson.fromJson(reader, playerListType);
            reader.close();
            System.out.println("Users have been initialized!");
        } catch (IOException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int[] getHighScore() {
        return highScore;
    }
    public void setHighScore(int[] highScore) {
        this.highScore = highScore;
    }
    public String getPassowrd() {
        return password;
    }
    public void setPassowrd(String passowrd) {
        this.password = passowrd;
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
