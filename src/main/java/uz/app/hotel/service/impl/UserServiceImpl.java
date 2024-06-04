package uz.app.hotel.service.impl;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.UserService;
import uz.app.hotel.util.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static uz.app.hotel.util.Utill.getText;

public class UserServiceImpl implements UserService {
    private final DB db = DB.getInstance();
    private final ReservationServiceImpl reservationService = ReservationServiceImpl.getInstance();
    @Override
    public void service() {
        User user = Context.getCurrentUser();
        System.out.printf("welcome to app %s\n",user.getName());
        while (true){
            String msg ="""
                    0 exit
                    1 showHotels
                    2 showReservations
                    3 reserve
                    4 cancelReservation
                    5 rescheduleReservation
                    6 showHistory
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
        List<Reservation> reservations = db.getReservations(Context.getCurrentUser(),false);
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    @Override
    public void reserve() {
        Optional<Reservation> optionalReservation = reservationService.newReservation();
        if (optionalReservation.isEmpty()){
            return;
        }
        Reservation reservation = optionalReservation.get();
        reservation.setUser(Context.getCurrentUser());
        reservationService.addReservation(reservation);
    }

    @Override
    public void cancelReservation() {
        reservationService.cancelReservation(getText("Enter reservation id"),false);
    }

    @Override
    public void rescheduleReservation() {
        reservationService.rescheduleReservation(getText("enter id"), LocalDate.parse(getText("start date as (yyyy-MM-dd)")),LocalDate.parse(getText("end date as (yyyy-MM-dd)")));
    }

    @Override
    public void showHistory() {
        List<Reservation> reservations = db.getReservations(Context.getCurrentUser(),true);
        reservations.forEach(System.out::println);
    }



    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null)
            userService = new UserServiceImpl();
        return userService;
    }
}
