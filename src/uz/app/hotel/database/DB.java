package uz.app.hotel.database;

import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;
import uz.app.hotel.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

public class DB {

    private ArrayList<User> users = new ArrayList<>();


    private DB() {
    }

    private static DB db;

    public static DB getInstance() {
        if (db == null)
            db = new DB();
        return db;
    }


    public void addUser(User user) {
        users.add(user);
    }

    public boolean ifUserExists(User user) {
        for (User temp : users) {
            if (temp.getUsername().equalsIgnoreCase(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public Optional<User> checkUser(String username, String password) {

        String message = "user not found";
        for (User temp : users) {
            if (temp.getUsername().equalsIgnoreCase(username) &&
                    temp.getPassword().equalsIgnoreCase(password)) {
                if (!temp.getConfirmed()) {
                    message = "account not confirmed";
                    break;
                }
                return Optional.of(temp);
            }
        }
        System.out.println(message);
        return Optional.empty();

    }

    public Optional<User> getUserByEmail(String email) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(email)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
