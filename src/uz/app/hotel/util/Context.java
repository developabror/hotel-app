package uz.app.hotel.util;

import uz.app.hotel.entity.User;

public class Context {
    private static User currentUser;
    public static void setUser(User user){
        currentUser = user;
    }


    public static User getCurrentUser(){
        return currentUser;
    }
}
