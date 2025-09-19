package services.implementation;

import entities.Client;
import entities.Hotel;
import entities.Reservation;
import repositories.ClientRepository;
import repositories.HotelRepository;
import repositories.ReservationRepository;
import services.ReservationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ImplReserv implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final ClientRepository clientRepository;

    public ImplReserv(ReservationRepository reservationRepository, HotelRepository hotelRepository, ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.clientRepository = clientRepository;
    }

    public Reservation makeReservation(String hotelId, UUID clientId, int nights) throws Exception {
        // Check if hotel exists
        Optional<Hotel> OldHotel = hotelRepository.findById(hotelId);
        if (OldHotel.isEmpty()) {
            throw new Exception("Hotel not found with id: " + hotelId);
        }

        Hotel hotel = OldHotel.get();

        if (hotel.getAvailableRooms() <= 0) {
            throw new Exception("No available rooms in hotel: " + hotel.getName());
        }

        Client client = clientRepository.find(clientId);
        if (client == null) {
            throw new Exception("Client not found");
        }

        Reservation reservation = new Reservation(hotelId, clientId, nights);
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(UUID clientId) {
        return reservationRepository.findByClientId(clientId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public boolean cancelReservation(UUID reservationId, UUID clientId) throws Exception {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isEmpty()) {
            throw new Exception("Reservation not found");
        }

        Reservation reservation = reservationOpt.get();

        // just authorization check
        if (!reservation.getClientId().equals(clientId)) {
            throw new Exception("Access Denied, reservation does not belong to you");
        }

        Optional<Hotel> hotelOpt = hotelRepository.findById(reservation.getHotelId());
        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
            hotelRepository.save(hotel);
        }

        return reservationRepository.deleteById(reservationId);
    }

    public String getHotelNameById(String hotelId) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        return hotelOpt.map(Hotel::getName).orElse("Unknown Hotel");
    }

    public String getClientNameById(UUID clientId) {
        Client client = clientRepository.find(clientId);
        return client != null ? client.getFullName() : "Unknown Client";
    }
}
