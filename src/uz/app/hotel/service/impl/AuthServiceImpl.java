package uz.app.hotel.service.impl;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Role;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;
import uz.app.hotel.service.AuthService;
import uz.app.hotel.service.UserService;
import uz.app.hotel.util.Context;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uz.app.hotel.util.Utill.*;

public class AuthServiceImpl implements AuthService {


    private final DB db = DB.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final AdminService adminService = AdminServiceImpl.getInstance();

    Logger log = Logger.getLogger("users");

    {
        try {
            FileHandler handler = new FileHandler();
            log.addHandler(handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void service() {
        while (true) {
            switch (getInt("""
                    0 exit
                    1 sign in
                    2 sign up
                    3 confirm sms
                    """)) {
                case 0 -> {
                    System.out.println("see you soon!");
                    return;
                }
                case 1 -> {
                    signIn();
                }
                case 2 -> {
                    signUp();
                }
            }
        }
    }

    @Override
    public void signUp() {
        User user = new User();
        user.setName(getText("enter name"));
        user.setUsername(getText("enter email"));
        user.setPassword(getText("enter password"));
        user.setRole(Role.USER);
        user.setConfirmed(false);
        user.setConfirmationTime(LocalDateTime.now().plusMinutes(1));
        if (db.ifUserExists(user)) {
            System.out.println("username exists!");
            return;
        }
        user.setCode(generateRandomCode());
        //send email message
        db.addUser(user);
        log.log(Level.INFO, user.toString());
        System.out.println("successfully registered!");
    }

    @Override
    public void signIn() {
        String username = getText("enter username");
        String password = getText("enter password");
        Optional<User> optionalUser = db.checkUser(username, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Context.setUser(user);
            switch (user.getRole()) {
                case USER -> {
                    userService.service();
                }
                case ADMIN -> {
                    adminService.service();
                }
            }
            Context.setUser(null);
        }
    }


    public void confirmSms(String email, String code) {
        Optional<User> optionalUser = db.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getConfirmationTime().isAfter(LocalDateTime.now())) {
                if (user.getCode().equalsIgnoreCase(code)) {
                    user.setConfirmed(true);
//                    db.addUser(user);
                }
            } else {
                System.out.println("confirmation time expired, we'll send another code!");
                user.setCode(generateRandomCode());
                //send email
                user.setConfirmationTime(LocalDateTime.now().plusMinutes(1));
            }
        } else {
            System.out.println("user not found!");
        }

    }


    Random random = new Random();

    private String generateRandomCode() {
        return String.valueOf(random.nextInt(100000, 1000000));
    }
}
