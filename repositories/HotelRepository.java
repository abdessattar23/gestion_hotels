package repositories;

import entities.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelRepository {
    void save(Hotel hotel);

    Optional<Hotel> findById(String hotelId);
    List<Hotel> findAll();
    List<Hotel> findByAvailability();

    void update(Hotel hotel);

    void delete(String hotelId);
    List<Hotel> findByName(String name);
    boolean existsById(String hotelId);
}
