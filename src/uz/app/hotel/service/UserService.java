package uz.app.hotel.service;

import uz.app.hotel.database.DB;

public interface UserService {
    void service();
    void showHotels();
    void showReservations();
    void reserve();
    void cancelReservation();
    void rescheduleReservation();
    void showHistory();



}
