package uz.app.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class Hotel {
    private final String  id=UUID.randomUUID().toString();
    private String name;
    private Location location;
    private Integer floors;
    private Integer roomsCount;


    public Hotel(String name, Location location, Integer floors, Integer roomsCount) {
        this.name = name;
        this.location = location;
        this.floors = floors;
        this.roomsCount = roomsCount;
    }

}
