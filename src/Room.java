import java.util.HashSet;
import java.util.Set;

public class Room {
    private int roomNumber;
    private String roomType; // e.g., "Single", "Double", "Suite"

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    // Method to get room type
    public int getRoomNumber() { return roomNumber; }
    // Method to get room type (i.e. 2 bedroom, 1 bedroom)
    public String getRoomType() { return roomType; }

}
