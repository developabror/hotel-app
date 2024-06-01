package uz.app.hotel.service.impl;

import uz.app.hotel.Enums.ReservationStatus;
import uz.app.hotel.database.DB;
import uz.app.hotel.entity.*;
import uz.app.hotel.service.AdminService;
import uz.app.hotel.util.Context;
import uz.app.hotel.util.Utill;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static uz.app.hotel.Enums.ReservationStatus.CANCELEDBYADMIN;
import static uz.app.hotel.util.Utill.getInt;
import static uz.app.hotel.util.Utill.getText;

public class AdminServiceImpl implements AdminService {
    private final DB db = DB.getInstance();
    @Override
    public void service() {
        User user = Context.getCurrentUser();
        System.out.printf("welcome to app %s\n",user.getName());
        while (true){
            String msg ="""
                    0 exit
                    1 add hotel
                    2 show hotel
                    3 show hotels
                    4 edit hotels
                    5 delete hotels
                    6 show reservation
                    7 show reservation history
                    8 cancel reservation
                    9 reserve for user
                    """;
            switch (getInt(msg)){
                case 0->{
                    System.out.println("see you soon!");
                }
                case 1->addHotel();
                case 2->showHotel();
                case 3->showHotels();
                case 4->editHotel();
                case 5->deleteHotel();
                case 6->{}// by hotel -> active reservations
                case 7->showReservationHistory();//
                case 8->cancelReservation();
                case 9->reserveForUser();
            }
        }
    }


    public void addHotel() {
        Hotel hotel = new Hotel();
        hotel.setName(getText("Enter name:"));
        hotel.setFloors(Utill.getInt("Enter floor:"));
        hotel.setRoomsCount(Utill.getInt("Enter room count:"));
        hotel.setLocation(Location.TASHKENT);
        db.addHotel(hotel);
        System.out.println("Hotel added successfully!");
    }
    public void showHotel() {
        String id = getText("Enter id:");
        Optional<Hotel> optionalHotel = db.getHotelById(id);
        if (optionalHotel.isPresent()) {
            System.out.println(optionalHotel.get());
            return;
        }
        System.out.println("Id is incorrect!");
    }
    public void showHotels() {
        for (Hotel hotel : db.showHotels()) {
            System.out.println(hotel);
        }
    }
    public void editHotel() {
        showHotels();
        String id = Utill.getText("Enter ID: ");
        Hotel hotel = new Hotel();
        hotel.setName(getText("Enter name:"));
        hotel.setFloors(Utill.getInt("Enter floor:"));
        hotel.setRoomsCount(Utill.getInt("Enter room count:"));
        hotel.setLocation(Location.TASHKENT);
        db.saveHotel(id,hotel);
    }
    public void deleteHotel() {
        showHotels();
        String id = Utill.getText("Enter ID: ");
        db.deleteHotel(id);
    }
    public void showUsers() {
       List<User> users = db.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
    public void showReservationHistory() {
        List<Reservation> reservations = db.getReservations();
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
    public void cancelReservation() {
        String id = getText("Enter ID: ");
        db.cancelReserv(id, CANCELEDBYADMIN);
    }
    public void reserveForUser() {
        Reservation reservation = new Reservation();
        Optional<Hotel> optionalHotel = db.getHotelById(getText("Enter id"));
        if (!optionalHotel.isPresent()) {
            System.out.println("Hotel not found");
            return;
        }
        if (setHotelToReservation(optionalHotel.get(), reservation)) return;
        if (!setUserToReservation(reservation)) {
            System.out.println("some issues occured with date");
            return;
        }

        if (db.checkAvailable(reservation)) {
            db.addReservation(reservation);
            System.out.println("reserved");
        }else {
            System.out.println("failed!");
        }
    }
    private boolean setUserToReservation(Reservation reservation) {
        String name = getText("Enter name:");
        String email = getText("Enter email:");
        User user = new User(name,email, Role.ANNONYMOUS_USER);
        reservation.setUser(user);
        LocalDate from = LocalDate.parse(getText("yyyy-mm-dd"));
        LocalDate to = LocalDate.parse(getText("yyyy-mm-dd"));
        if (from.isBefore(LocalDate.now()) || to.isBefore(from)){
            return false;
        }
        reservation.setStartDate(from);
        reservation.setEndDate(to);
        reservation.setReservationStatus(ReservationStatus.ACTIVE);
        return true;
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
        return false;
    }

    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null)
            adminService = new AdminServiceImpl();
        return adminService;
    }
}
