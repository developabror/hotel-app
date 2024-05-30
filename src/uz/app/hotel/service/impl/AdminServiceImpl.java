package uz.app.hotel.service.impl;

import uz.app.hotel.service.AdminService;
import uz.app.hotel.service.UserService;

public class AdminServiceImpl implements AdminService {
    @Override
    public void service() {

    }

    @Override
    public void addHotel() {

    }

    @Override
    public void showHotel() {

    }

    @Override
    public void showHotels() {

    }

    @Override
    public void editHotel() {

    }

    @Override
    public void deleteHotel() {

    }

    @Override
    public void showUsers() {

    }

    @Override
    public void showReservationHistory() {

    }

    @Override
    public void calcelReservation() {

    }

    @Override
    public void reserveForUser() {

    }

    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null)
            adminService = new AdminServiceImpl();
        return adminService;
    }
}
