package repositories.inmemory;
import repositories.HotelRepository;
import entities.Hotel;

import java.util.*;

public class inmemoryHotelRepo implements HotelRepository {
    private HashMap<String, Hotel> Hotels = new HashMap<>();

    public void save(Hotel Hotel){
        Hotels.put(Hotel.getHotelId(), Hotel);
    }

    public void update(Hotel Hotel){
        Hotel old = Hotels.get(Hotel.getHotelId());
        old.setAddress(Hotel.getAddress());
        old.setName(Hotel.getName());
        old.setAvailableRooms(Hotel.getAvailableRooms());
        old.setRating(Hotel.getRating());
        this.save(old);
    }

    public List<Hotel> findAll(){
        return new ArrayList<>(Hotels.values());
    }

    public Optional<Hotel> findById(String hotelId){
        return Optional.ofNullable(Hotels.get(hotelId));
    }

    public List<Hotel> findByAvailability(){
        return Hotels.values().stream().filter(hotel -> hotel.getAvailableRooms() > 0).collect(ArrayList::new, (list, hotel) -> list.add(hotel), (list1, list2) -> list1.addAll(list2));
    }

    public void delete(String hotelId){
        Hotels.remove(hotelId);
    }

    public List<Hotel> findByName(String name){
        Hotel hotel = Hotels.values().stream().filter(h -> h.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if(hotel != null){
            return List.of(hotel);
        } else {
            return List.of();
        }
    }

    public boolean existsById(String hotelId){
        return Hotels.containsKey(hotelId);
    }

}
