package entities;

import java.util.UUID;

public class Hotel {
    private String hotelId;
    private String name;
    private String address;
    private int availableRooms;
    private double rating;
    private UUID owner;

    public Hotel(String name, String address, int availableRooms, double rating) {
        this.name = name;
        this.address = address;
        this.availableRooms = availableRooms;
        this.rating = rating;
    }
    
    public Hotel(String hotelId, String name, String address, int availableRooms, double rating) {
        this.name = name;
        this.address = address;
        this.availableRooms = availableRooms;
        this.rating = rating;
        this.hotelId = hotelId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
