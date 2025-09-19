package services;

import entities.*;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    Reservation makeReservation(String hotelId, UUID clientId, int nights) throws Exception;
    List<Reservation> getUserReservations(UUID clientId);
    List<Reservation> getAllReservations();
    boolean cancelReservation(UUID reservationId, UUID clientId) throws Exception;
    String getHotelNameById(String hotelId);
    String getClientNameById(UUID clientId);
}
