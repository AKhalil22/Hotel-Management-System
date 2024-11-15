public class Room {
    private int roomNumber;
    private String roomType; // e.g., "Single", "Double", "Suite"
    private boolean available;

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.available = true;
    }

    // Method to get room type
    public int getRoomNumber() { return roomNumber; }
    // Method to get room type (i.e. 2 bedroom, 1 bedroom)
    public String getRoomType() { return roomType; }

    // Method to check if room is available
    public boolean isAvailable() { return available; }

    // Method to set room availability
    public void setAvailable(boolean available) { this.available = available; }

}
