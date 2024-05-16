package uz.app.hotel.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {
    private final String  id= UUID.randomUUID().toString();
    private User user;
    private Hotel hotel;
    private Integer floor;
    private Integer room;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate earliestFinishedDate;
    private Boolean earlierFinished;


    public Reservation(User user, Hotel hotel, Integer floor, Integer room, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.hotel = hotel;
        this.floor = floor;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", hotel=" + hotel +
                ", floor=" + floor +
                ", room=" + room +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
