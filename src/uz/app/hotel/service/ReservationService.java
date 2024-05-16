package uz.app.hotel.service;

import uz.app.hotel.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface ReservationService {
    boolean addReservation(Reservation reservation);
    Reservation showReservation(String id);
    List<Reservation> showReservationByUser(String id);
    List<Reservation> showReservationByHotel(String id);
    boolean cancelReservation(String id);// kim cancel qilgani va reserv id log ga yozib boriladi
    boolean finishReservation(String id,LocalDate date);
    boolean rescheduleReservation(String id, LocalDate from, LocalDate to);//
}
