package uz.app.hotel.database;

import uz.app.hotel.Enums.ReservationStatus;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DB {

    private ArrayList<User> users = new ArrayList<>();

    private List<Hotel> hotels = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    private DB() {
    }

    private static DB db;

    public static DB getInstance() {
        if (db == null)
            db = new DB();
        return db;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public Optional<Hotel> getHotelById(String id) {
        for (Hotel hotel : hotels) {
            if (hotel.getId().equals(id)) return Optional.of(hotel);
        }
        return Optional.empty();
    }

    public List<Hotel> showHotels() {
        return hotels;
    }


    public void addUser(User user) {
        users.add(user);
    }

    public boolean ifUserExists(User user) {
        for (User temp : users) {
            if (temp.getEmail().equalsIgnoreCase(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public Optional<User> checkUser(String username, String password) {

        String message = "user not found";
        for (User temp : users) {
            if (temp.getEmail().equalsIgnoreCase(username) &&
                    temp.getPassword().equalsIgnoreCase(password)) {
                if (!temp.getConfirmed()) {
                    message = "account not confirmed";
                    break;
                }
                return Optional.of(temp);
            }
        }
        System.out.println(message);
        return Optional.empty();

    }

    public Optional<User> getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void saveHotel(String id, Hotel hotel) {
        Optional<Hotel> hotelById = getHotelById(id);
        if (hotelById.isPresent()) {
            Hotel hotell = hotelById.get();
            hotell.setName(hotel.getName());
            hotell.setFloors(hotel.getFloors());
            hotell.setLocation(hotel.getLocation());
            hotell.setRoomsCount(hotel.getRoomsCount());
        }
    }

    public void deleteHotel(String id) {
        Optional<Hotel> hotelById = getHotelById(id);
        if (hotelById.isPresent()) {
            hotels.remove(hotelById.get());
            System.out.println("Success");
        } else {
            System.out.println("Not Found");
            return;
        }
    }

    public void cancelReserv(String id, ReservationStatus reservationStatus) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                reservation.setReservationStatus(reservationStatus);
                return;
            }
        }
    }

    public Boolean checkAvailable(Reservation reservation) {
        return 0 == reservations
                .stream()
                .filter(res ->
                        res.getHotel().getId().equals(reservation.getHotel().getId()) &&
                                res.getReservationStatus().equals(ReservationStatus.ACTIVE) &&
                                res.getFloor().equals(reservation.getFloor()) &&
                                res.getRoom().equals(reservation.getRoom())
                )
                .filter(res -> {
                    LocalDate startDate = res.getStartDate();
                    LocalDate endDate = res.getEndDate();
                    return (reservation.getStartDate().isAfter(startDate) &&
                            reservation.getStartDate().isBefore(endDate)) ||
                            (reservation.getEndDate().isAfter(startDate) &&
                                    reservation.getEndDate().isBefore(endDate)) ||
                            (reservation.getStartDate().isBefore(startDate) &&
                                    reservation.getEndDate().isAfter(endDate));
                })
                .limit(1)
                .count();
    }

    public Boolean checkAvailable(Reservation reservation, String id) {
        return 0 == reservations
                .stream()
                .filter(res ->
                        !res.getId().equals(id) &&
                                res.getHotel().getId().equals(reservation.getHotel().getId()) &&
                                res.getReservationStatus().equals(ReservationStatus.ACTIVE) &&
                                res.getFloor().equals(reservation.getFloor()) &&
                                res.getRoom().equals(reservation.getRoom())
                )
                .filter(res -> {
                    LocalDate startDate = res.getStartDate();
                    LocalDate endDate = res.getEndDate();
                    return (reservation.getStartDate().isAfter(startDate) &&
                            reservation.getStartDate().isBefore(endDate)) ||
                            (reservation.getEndDate().isAfter(startDate) &&
                                    reservation.getEndDate().isBefore(endDate)) ||
                            (reservation.getStartDate().isBefore(startDate) &&
                                    reservation.getEndDate().isAfter(endDate));
                })
                .limit(1)
                .count();
    }

    public void addReservation(Reservation reservation) {
        Optional<Reservation> any = reservations.stream().parallel().filter(reservation1 ->
                reservation1.getId().equals(reservation.getId())).findAny();
        if (any.isEmpty())
            reservations.add(reservation);
        else {
            //edit qilish kerak
        }
    }

    public List<Reservation> getReservations(User user, boolean isAll) {
        return reservations
                .stream()
                .filter(reservation -> reservation.getUser().getId().equals(user.getId()))
                .filter(reservation -> isAll || reservation.getReservationStatus().equals(ReservationStatus.ACTIVE))
                .toList();
    }

    public Optional<Reservation> getreservation_byId(String id) {
        return reservations.stream().parallel().filter(s -> s.getId().equals(id)).findAny();
    }


}
