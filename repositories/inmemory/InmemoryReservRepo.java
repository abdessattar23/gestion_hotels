package repositories.inmemory;

import entities.Reservation;
import repositories.ReservationRepository;
import java.util.*;
import java.util.stream.Collectors;

public class InmemoryReservRepo implements ReservationRepository {
    private Map<UUID, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getId() == null) {
            reservation.setId(UUID.randomUUID());
        }
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public List<Reservation> findByClientId(UUID clientId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByHotelId(String hotelId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getHotelId().equals(hotelId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(UUID id) {
        return reservations.remove(id) != null;
    }
}
