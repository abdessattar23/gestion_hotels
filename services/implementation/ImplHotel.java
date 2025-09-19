package services.implementation;

import entities.Hotel;
import repositories.HotelRepository;
import services.HotelService;

import java.util.List;

public class ImplHotel implements HotelService {
    private final HotelRepository repository;

    public ImplHotel(HotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Hotel save(Hotel hotel) {
        repository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel update(Hotel hotel) {
        if (!repository.existsById(hotel.getHotelId())) {
            throw new IllegalArgumentException("Hotel with ID " + hotel.getHotelId() + " does not exist.");
        }
        repository.update(hotel);
        return hotel;
    }

    @Override
    public void delete(String hotelId) {
        if (!repository.existsById(hotelId)) {
            throw new IllegalArgumentException("Hotel with ID " + hotelId + " does not exist.");
        }
        repository.delete(hotelId);
    }

    @Override
    public List<Hotel> findAll() {
        return repository.findAll();
    }
}
