package services;

import entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    void delete(String hotelId);
    List<Hotel> findAll();
}
