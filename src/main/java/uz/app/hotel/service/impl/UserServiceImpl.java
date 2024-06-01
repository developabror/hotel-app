package uz.app.hotel.service.impl;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.UserService;
import uz.app.hotel.util.Context;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final DB db = DB.getInstance();
    @Override
    public void service() {
        User user = Context.getCurrentUser();
        System.out.printf("welcome to app %s\n",user.getName());
        while (true){
            String msg ="""
                    0 exit
                    """;
        }

    }

    @Override
    public void showHotels() {
        List<Hotel> hotels = db.showHotels();
        for (Hotel hotel : hotels) {
            System.out.println(hotel);
        }
    }

    @Override
    public void showReservations() {
        List<Reservation> reservations = db.getReservations(Context.getCurrentUser());
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    @Override
    public void reserve() {

    }

    @Override
    public void cancelReservation() {

    }

    @Override
    public void rescheduleReservation() {

    }

    @Override
    public void showHistory() {

    }
    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null)
            userService = new UserServiceImpl();
        return userService;
    }
}
