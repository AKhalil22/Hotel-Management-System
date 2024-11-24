import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private String name;
    private String password;

    // A list to store customers and rooms
    private List<Customer> customers;
    private List<Room> rooms;

    // Constructor
    public Admin() {
        this.name = "admin";
        this.password = "admin";
        this.customers = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    // Method to verify login
    public boolean verifyLogin(String enteredName, String enteredPassword) {
        return this.name.equals(enteredName) && this.password.equals(enteredPassword);
    }

    // Method to get all customers
    public List<Customer> getCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found."); //using console for the time being
        }
        return new ArrayList<>(customers);
    }

    // Method to book a customer
    // Method to book a customer
    public boolean bookCustomer(Customer customer, Room room, String checkInDate, String checkOutDate) {
        if (room == null || customer == null) {
            return false; // Cannot proceed with booking if either is null.
        }
        if (isRoomAvailable(room, checkInDate, checkOutDate)) {
            // Associate the customer with the room
            customer.setStartDate(checkInDate);
            customer.setEndDate(checkOutDate);
            customers.add(customer); // Add customer to the admin's managed customers
            return true;
        } else {
            return false;
        }
    }

    // Helper method to check room availability (placeholder logic)
    private boolean isRoomAvailable(Room room, String checkInDate, String checkOutDate) {
        List<Room> availableRooms = Database.getAvailableRooms(); // Get all available rooms

        // Check if the specific room is available in the list
        for (Room availableRoom : availableRooms) {
            if (availableRoom.getRoomNumber() == room.getRoomNumber()) {
                // Check if the room is available for the given date range
                return Database.isRoomAvailable(room.getRoomNumber(), checkInDate, checkOutDate);
            }
        }
        return false;
    }





    // Method to retrieve all rooms
    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    // Method to create a booking (interacts with Database class)
    public void createBooking(String name, String roomType, String checkInDate, String checkOutDate, String amenities) {
        try {
            // Call the Database class to insert booking information
            Database.insertBooking(name, roomType, checkInDate, checkOutDate, amenities);
        } catch (SQLException e) {
            // Handle SQL exception and rethrow as a runtime exception
            throw new RuntimeException(e);
        }
    }

    // Getters for Admin properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
