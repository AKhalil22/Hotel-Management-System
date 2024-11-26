import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private static String name;
    private static String password;

    // Constructor
    public Admin(String username, String password) {
        this.name = username;
        this.password = password;
    }

    // Method to verify login
    public static boolean verifyLogin(String enteredName, String enteredPassword) {
        return name.equals(enteredName) && password.equals(enteredPassword);
    }

    // Method to create a booking (interacts with Database class)
    public void createBooking(String name, int roomNumber, String checkInDate, String checkOutDate, String amenities) {
        try {
            // Call the Database class to insert booking information
            Database.insertBooking(name, roomNumber, checkInDate, checkOutDate, amenities);
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
