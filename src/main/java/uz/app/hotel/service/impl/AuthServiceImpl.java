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
import java.util.Optional;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static uz.app.hotel.util.Utill.getInt;
import static uz.app.hotel.util.Utill.getText;

public class AuthServiceImpl implements AuthService {


    private final DB db = DB.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final AdminService adminService = AdminServiceImpl.getInstance();


    Logger log = Logger.getLogger("users");

    {
        try {
            FileHandler handler = new FileHandler("resources/",true);
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);
            db.addUser(new User("admin","admin","admin",true,"",null,Role.ADMIN));
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
                case 3->
                    confirmSms(getText("enter eail"),getText("enter code"));
            }
        }
    }

    @Override
    public void signUp() {
        User user = new User();
        user.setName(getText("enter name"));
        user.setEmail(getText("enter email"));
        user.setPassword(getText("enter password"));
        user.setRole(Role.USER);
        user.setConfirmed(false);
        user.setConfirmationTime(LocalDateTime.now().plusMinutes(1));
        if (db.ifUserExists(user)) {
            System.out.println("email exists!");
            return;
        }
        user.setCode(generateRandomCode());
        Sms.send(user);
        db.addUser(user);
        log.log(Level.INFO, user.toString());
        System.out.println("successfully registered!");
    }

    @Override
    public void signIn() {
        String email = getText("enter email");
        String password = getText("enter password");
        Optional<User> optionalUser = db.checkUser(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Context.setUser(user);
            switch (user.getRole()) {
                case USER ->
                    userService.service();
                case ADMIN ->
                    adminService.service();
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
                    System.out.println("confirmed");
                    user.setConfirmed(true);
                }
            } else {
                System.out.println("confirmation time expired, we'll send another code!");
                user.setCode(generateRandomCode());
                user.setConfirmationTime(LocalDateTime.now().plusMinutes(1));
                Sms.send(user);
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
