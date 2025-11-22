package user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class UserManager {
    private User user;
    private static UserManager instance;
    private ArrayList<User> users;

    private UserManager() {
        users = new ArrayList<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void loadData() {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("src/user/data/users.json");
            Type playerListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            users = gson.fromJson(reader, playerListType);
            reader.close();
            System.out.println("Connected to users database!");
        } catch (IOException e) {
            System.out.println("Failed to load user database" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            Gson gson = new Gson();
            FileWriter writer1 = new FileWriter("src/user/data/users.json");
            gson.toJson(users, writer1);
            writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public User getUser() {return user;}

    public void login(String username, String password) throws Exception {
        if( users.isEmpty()) {loadData();}
        if (Objects.equals(username, "")) {
            throw new Exception("Please enter username");
        } else if (Objects.equals(password, "")) {
            throw new Exception("Please enter password");
        } else {
            if (!users.isEmpty()){
                for (User user : users) {
                    if (username.equals(user.getUserName())) {
                        if (password.equals(user.getPassword())) {
                            this.user = user;
                            return;
                        } else {
                            throw new Exception("Username or password incorrect");
                        }
                    }
                }
            }
            throw new Exception("Not found user!");
        }
    }

    public void logout() {
        saveData();
        user = null;
    }

    public void signup(String username, String password, String confirm) throws Exception {
        if (Objects.equals(username, "")) {
            throw new Exception("Please enter username");
        } else if (Objects.equals(password, "")) {
            throw new Exception("Please enter password");
        } else if (!confirm.equals(password)) {
            throw new Exception("Passwords do not match");
        } else if(!users.isEmpty()) {
            for (User user : users) {
                if (username.equals(user.getUserName())) {
                    throw new Exception("Username already in use");
                }
            }
        }
        int[] newHighScore = new int[] {0,0,0};
        users.add(new User(username, password, newHighScore,1));
        saveData();
        System.out.println("Signup successful!");
    }
}
