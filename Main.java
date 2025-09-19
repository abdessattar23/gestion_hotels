import entities.Client;
import entities.Hotel;
import entities.Reservation;
import repositories.*;
import repositories.inmemory.*;
import services.*;
import services.implementation.*;
import utils.Login;
import utils.Register;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ClientRepository repository = new InmemoryClientRepo();
    static HotelRepository hotelRepository = new inmemoryHotelRepo();
    static ReservationRepository reservationRepository = new InmemoryReservRepo();
    static AuthService auth = new ImplAuth(repository);
    static HotelService hotelService = new ImplHotel(hotelRepository);
    static ReservationService reservationService = new ImplReserv(reservationRepository, hotelRepository, repository);
    static boolean loggedIn = false;
    static Client User = null;

    public static boolean login(){
        Login login = new Login();

        login.login();
        try{
            Optional<Client> User = auth.login(login.getEmail(), login.getPassword());
            if(User.isPresent()){
                System.out.println("Welcome " + User.get().getFullName() + " among us !!");
                loggedIn = true;
                Main.User = User.get();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void register(){
        Register register = new Register();

        try{
            register.register();
            Client User = auth.register(register.getFullName(), register.getEmail(), register.getPassword());
            if(User != null){
                System.out.println("Welcome " + User.getFullName() + " among us !!");
                loggedIn = true;
                Main.User = User;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logout(){
        if(User != null){
            User.setConnected(false);
            repository.save(User);
        }
        loggedIn = false;
        User = null;
        System.out.println("You have been logged out successfully!");
    }

    public static void list(){
        List<Client> users = auth.getAllUsers();
        if(!users.isEmpty()) {
            System.out.println(users.get(0));
        } else {
            System.out.println("No clients found");
        }
    }

    public static void LoggedInMenu(){
        Scanner input = new Scanner(System.in);

        System.out.println("===================================");
        System.out.println("        WELCOME " + User.getFullName().toUpperCase());
        System.out.println("        Role: " + (User.isAdmin() ? "ADMIN" : "USER"));
        System.out.println("===================================");

        if(User.isAdmin()){
            // admin
            System.out.println("  [1] View Profile");
            System.out.println("  [2] Create Hotel");
            System.out.println("  [3] View All Hotels");
            System.out.println("  [4] List All Users");
            System.out.println("  [5] View All Reservations");
            System.out.println("  [6] Logout");
        } else {
            // usee
            System.out.println("  [1] View Profile");
            System.out.println("  [2] View Available Hotels");
            System.out.println("  [3] My Reservations");
            System.out.println("  [4] Make Reservation");
            System.out.println("  [5] Logout");
        }
        System.out.println("===================================");

        System.out.print("Select an option: ");
        int choice = input.nextInt();

        if(User.isAdmin()){
            AdminChoice(choice);
        } else {
            UserChoice(choice);
        }
    }

    public static void AdminChoice(int choice){
        switch (choice){
            case 1:
                System.out.println("Profile: " + User.toString());
                break;
            case 2:
                createHotel();
                break;
            case 3:
                viewAllHotels();
                break;
            case 4:
                list();
                break;
            case 5:
                viewAllReservations();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public static void UserChoice(int choice){
        switch (choice){
            case 1:
                System.out.println("Profile: " + User.toString());
                break;
            case 2:
                viewAvailableHotels();
                break;
            case 3:
                myReservations();
                break;
            case 4:
                makeReservation();
                break;
            case 5:
                logout();
                break;
            default:
                System.out.println("Invalid option, please try again");
        }
    }

    public static void createHotel(){
        Scanner input = new Scanner(System.in);
        System.out.println("\n=== CREATE NEW HOTEL ===");

        System.out.print("Enter hotel name: ");
        String name = input.nextLine();

        System.out.print("Enter hotel address: ");
        String address = input.nextLine();

        System.out.print("Enter number of available rooms: ");
        int availableRooms = input.nextInt();

        System.out.print("Enter hotel rating : ");
        double rating = input.nextDouble();

        try {
            //generer l hotel id with prefix HTL (hotel)
            String hotelId = "HTL" + name.replaceAll("\\s+", "");

            Hotel hotel = new Hotel(hotelId, name, address, availableRooms, rating);
            hotel.setOwner(User.getId());
            hotelService.save(hotel);
            System.out.println("Hotel '" + name + "' created successfully with ID: " + hotelId);
        } catch (Exception e) {
            System.out.println("Error creating hotel: " + e.getMessage());
        }
    }

    public static void viewAllHotels(){
        System.out.println("\n=== ALL HOTELS ===");
        List<Hotel> allHotels = hotelService.findAll();

        if(allHotels.isEmpty()){
            System.out.println("No hotels found in the system");
            return;
        }

        System.out.println("All Hotels in the System:");
        for(int i = 0; i < allHotels.size(); i++){
            Hotel hotel = allHotels.get(i);
            System.out.println((i+1) + ". " + hotel.getName() + " - " + hotel.getAddress() +
                " (Rooms: " + hotel.getAvailableRooms() + ", Rating: " + hotel.getRating() + ")");
        }
    }

    public static void viewAvailableHotels(){
        System.out.println("\n=== AVAILABLE HOTELS ===");
        try {
            List<Hotel> availableHotels = hotelRepository.findByAvailability();

            if(availableHotels.isEmpty()){
                System.out.println("No hotels with available rooms found.");
                return;
            }

            System.out.println("Hotels with Available Rooms:");
            for(int i = 0; i < availableHotels.size(); i++){
                Hotel hotel = availableHotels.get(i);
                System.out.println((i+1) + ". " + hotel.getName() + " - " + hotel.getAddress() +
                    " (Available Rooms: " + hotel.getAvailableRooms() + ", Rating: " + hotel.getRating() + ")");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving available hotels: " + e.getMessage());
        }
    }

    public static void myReservations() {
        System.out.println("\n=== MY RESERVATIONS ===");
        List<Reservation> reservations = reservationService.getUserReservations(User.getId());

        if(reservations.isEmpty()){
            System.out.println("You have no reservations.");
            return;
        }

        System.out.println("Your Reservations:");
        for(int i = 0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            String hotelName = reservationService.getHotelNameById(reservation.getHotelId());
            System.out.println((i+1) + ". Hotel: " + hotelName +
                ", Nights: " + reservation.getNights() +
                ", Date: " + reservation.getTimestamp() +
                ", Reservation ID: " + reservation.getId());
        }

        Scanner input = new Scanner(System.in);
        System.out.print("\nWould you like to cancel a reservation? : ");
        String choice = input.nextLine();

        if(choice.equalsIgnoreCase("y")){
            System.out.print("Enter reservation number to cancel : ");
            int reservationIndex = input.nextInt() - 1;

            if(reservationIndex >= 0 && reservationIndex < reservations.size()){
                try {
                    Reservation reservationToCancel = reservations.get(reservationIndex);
                    boolean cancelled = reservationService.cancelReservation(reservationToCancel.getId(), User.getId());
                    if(cancelled){
                        System.out.println("Reservation cancelled successfully!");
                    } else {
                        System.out.println("Failed to cancel reservation");
                    }
                } catch (Exception e) {
                    System.out.println("Error cancelling reservation: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid reservation");
            }
        }
    }

    public static void makeReservation() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n=== MAKE A RESERVATION ===");

        List<Hotel> availableHotels;
        try {
            availableHotels = hotelRepository.findByAvailability();
            if(availableHotels.isEmpty()){
                System.out.println("No hotels with available rooms found");
                return;
            }

            System.out.println("Available Hotels:");
            for(int i = 0; i < availableHotels.size(); i++){
                Hotel hotel = availableHotels.get(i);
                System.out.println((i+1) + ". " + hotel.getName() + " - " + hotel.getAddress() +
                    " (Available Rooms: " + hotel.getAvailableRooms() + ", Rating: " + hotel.getRating() +
                    ", ID: " + hotel.getHotelId() + ")");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving available hotels: " + e.getMessage());
            return;
        }

        System.out.print("\nEnter hotel ID from the list above: ");
        String hotelId = input.nextLine();

        System.out.print("Enter number of nights: ");
        int nights = input.nextInt();

        if(nights <= 0){
            System.out.println("Number of nights must be > 0");
            return;
        }

        try {
            Reservation reservation = reservationService.makeReservation(hotelId, User.getId(), nights);
            System.out.println("Reservation made successfully!");
            System.out.println("Reservation ID: " + reservation.getId());
            System.out.println("Hotel: " + reservationService.getHotelNameById(hotelId));
            System.out.println("Nights: " + nights);
            System.out.println("Date: " + reservation.getTimestamp());
        } catch (Exception e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    public static void viewAllReservations() {
        System.out.println("\n=== ALL RESERVATIONS ===");
        List<Reservation> allReservations = reservationService.getAllReservations();

        if(allReservations.isEmpty()){
            System.out.println("No reservations found in the system.");
            return;
        }

        System.out.println("All Reservations in the System:");
        for(int i = 0; i < allReservations.size(); i++){
            Reservation reservation = allReservations.get(i);
            String hotelName = reservationService.getHotelNameById(reservation.getHotelId());
            String clientName = reservationService.getClientNameById(reservation.getClientId());
            System.out.println((i+1) + ". Client: " + clientName +
                ", Hotel: " + hotelName +
                ", Nights: " + reservation.getNights() +
                ", Date: " + reservation.getTimestamp() +
                ", Reservation ID: " + reservation.getId());
        }
    }

    public static void showGuestMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("===================================");
        System.out.println("        GESTION DES HOTELLES");
        System.out.println("===================================");
        System.out.println("  [1] Login");
        System.out.println("  [2] Register");
        System.out.println("  [3] View Available Hotels");
        System.out.println("  [4] Exit");
        System.out.println("===================================");

        System.out.print("Select an option: ");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                viewAvailableHotels();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option, please try again");
        }
    }

    public static void main(String[] args) {
        /* important stuff to declare b7al scanner etc.... */
        boolean running = true;

        while (running) {
            if(loggedIn){
                LoggedInMenu();
            } else {
                showGuestMenu();
            }
        }
    }
}