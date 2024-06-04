package uz.app.hotel.service.impl;

import lombok.SneakyThrows;
import uz.app.hotel.Enums.ReservationStatus;
import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;
import uz.app.hotel.service.ReservationService;
import uz.app.hotel.util.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static uz.app.hotel.Enums.ReservationStatus.CANCELEDBYADMIN;
import static uz.app.hotel.Enums.ReservationStatus.CANCELEDBYUSER;
import static uz.app.hotel.util.Utill.getInt;
import static uz.app.hotel.util.Utill.getText;

public class ReservationServiceImpl implements ReservationService {
    private DB db=DB.getInstance();
    @Override
    public boolean addReservation(Reservation reservation) {
        if (db.checkAvailable(reservation)) {
            db.addReservation(reservation);
            System.out.println("reserved");
        }else {
            System.out.println("failed!");
        }
        return false;
    }

    @Override
    public Reservation showReservation(String id) {
        db.getreservation_byId(id);
        return null;
    }

    @Override
    public List<Reservation> showReservationByUser(String id) {
        return null;
    }

    @Override
    public List<Reservation> showReservationByHotel(String id) {
        return null;
    }

    @Override
    public boolean cancelReservation(String id,Boolean isAdmin) {
        if (!isAdmin && getReservationByidAndCurrentUser(id).isEmpty()){
            return false;
        }
        db.cancelReserv(id, isAdmin?CANCELEDBYADMIN:CANCELEDBYUSER);
        return true;
    }

    @Override
    public boolean finishReservation(String id, LocalDate date) {
        return false;
    }

    @SneakyThrows
    @Override
    public boolean rescheduleReservation(String id, LocalDate from, LocalDate to)  {
        Optional<Reservation> optionalReservation = getReservationByidAndCurrentUser(id);
        if (optionalReservation.isEmpty()){
            return false;
        }
        Reservation reservation = optionalReservation.get();


        Reservation reservationClone = reservation.clone();
        reservationClone.setStartDate(from);
        reservationClone.setEndDate(to);
        if (!db.checkAvailable(reservationClone,id)) {
            System.out.println("reservation cannot be changed!");
            return false;
        }

        reservation.setStartDate(from);
        reservation.setEndDate(to);
        db.addReservation(reservation);
        return true;
    }


    private Optional<Reservation> getReservationByidAndCurrentUser(String id){
        Optional<Reservation> optionalReservation = db.getreservation_byId(id);
        if (optionalReservation.isEmpty()) {
            System.out.println("Reservation topilmadi");
            return Optional.empty();
        }
        Reservation reservation = optionalReservation.get();
        User currentUser = Context.getCurrentUser();
        if (!reservation.getUser().getId().equals(currentUser.getId())) {
            System.out.println("Reservation sizga tegishli emas");
            return Optional.empty();
        }
        return Optional.of(reservation);
    }



    public Optional<Reservation> newReservation(){
        Reservation reservation = new Reservation();
        Optional<Hotel> optionalHotel = db.getHotelById(getText("Enter id"));
        if (!optionalHotel.isPresent()) {
            System.out.println("Hotel not found");
            return Optional.empty();
        }
        if (setHotelToReservation(optionalHotel.get(), reservation))
            return Optional.empty();
        return Optional.of(reservation);
    }
    private boolean setHotelToReservation(Hotel hotel, Reservation reservation) {
        reservation.setHotel(hotel);
        System.out.printf("Siz maksimal %d balanlikkacha xona zakaz qiloolasiz\n",hotel.getFloors());
        Integer floor=Math.abs(getInt("Enter floor"));
        if(floor>hotel.getFloors()){
            System.out.println("Wrong floor");
            return true;
        }
        reservation.setFloor(floor);
        System.out.printf("Siz maksimal %d raqamgacha xona bor\n",hotel.getRoomsCount());
        Integer room=Math.abs(getInt("Enter room number " ));
        if(room>hotel.getRoomsCount()){
            System.out.println("Wrong room number ");
            return true;
        }
        reservation.setRoom(room);
        LocalDate from = LocalDate.parse(getText("yyyy-mm-dd"));
        LocalDate to = LocalDate.parse(getText("yyyy-mm-dd"));
        if (from.isBefore(LocalDate.now()) || to.isBefore(from)){
            return false;
        }
        reservation.setStartDate(from);
        reservation.setEndDate(to);
        reservation.setReservationStatus(ReservationStatus.ACTIVE);
        return false;
    }



    private static ReservationServiceImpl reservationService;

    public static ReservationServiceImpl getInstance() {
        if (reservationService == null)
            reservationService = new ReservationServiceImpl();
        return reservationService;
    }
}
