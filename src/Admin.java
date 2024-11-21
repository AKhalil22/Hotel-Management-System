import java.sql.SQLException;

public class Admin {
    public void createBooking(String name, String roomType, String checkInDate, String checkOutDate, String amenities){
        try {
            Database.insertBooking(name, roomType, checkInDate, checkOutDate, amenities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
