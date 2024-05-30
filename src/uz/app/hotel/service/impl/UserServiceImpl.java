package uz.app.hotel.service.impl;

import uz.app.hotel.database.DB;
import uz.app.hotel.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void service() {

    }

    @Override
    public void showHotels() {

    }

    @Override
    public void showReservations() {

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
