package uz.app.hotel.service;

import uz.app.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {
    void add(Hotel hotel);
    Hotel show(String id);
    List<Hotel> showAll();
    boolean edit(String id, Hotel hotel);//kim edit qilgani, eski nomi va yangi nomi log ga yozib boriladi
    boolean delete(String id);
}
