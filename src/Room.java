public class Room {
    private int roomNumber;
    private String roomType; // e.g., "Single", "Double", "Suite"
    private double roomPrice; // e.g., "Single", "Double", "Suite"
    private boolean available;


    public Room(int roomNumber, String roomType, double roomPrice, boolean available) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.available = true;
    }

    // Method to get room type
    public int getRoomNumber() {
        return roomNumber;
    }

    // Method to get room type (i.e. 2 bedroom, 1 bedroom)
    public String getRoomType() {
        return roomType;
    }

    // Method to check if room is available
    public boolean isAvailable() {
        return available;
    }

    // Method to set room availability
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }
}
