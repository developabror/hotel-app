package uz.app.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.app.hotel.Enums.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation implements Cloneable{
    private final String  id= UUID.randomUUID().toString();
    private User user;
    private Hotel hotel;
    private Integer floor;
    private Integer room;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus reservationStatus;

    @Override
    public Reservation clone() throws CloneNotSupportedException {
        return (Reservation)super.clone();
    }
}
