import java.util.HashSet;
import java.util.Set;

public class Room {
    private int roomNumber;
    private String roomType; // e.g., "Single", "Double", "Suite"
    private Set<Amenity> amenities;

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.amenities = new HashSet<>(); // Initialize an empty Hashset of amenities
    }

    // Method to get room type
    public int getRoomNumber() { return roomNumber; }
    // Method to get room type (i.e. 2 bedroom, 1 bedroom)
    public String getRoomType() { return roomType; }
    // Method to add an amenity
    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
    }

    // Method to remove an amenity
    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
    }

    // Method to check if a room has a specific amenity
    public boolean hasAmenity(Amenity amenity) {
        return amenities.contains(amenity);
    }



}
