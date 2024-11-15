import java.sql.SQLException;
import java.util.*;

public class HotelManagementSystem {

    public static void main(String[] args) throws SQLException {
        // Store customers O(1) Lookup (Time Complexity)
        ArrayList<Customer> customers = new ArrayList<>();
        // Key-Value Pair | Key = Room (Object) | Value = Price (Integer)
        TreeMap<Room, Integer> map = new TreeMap<>();
        // Wait-list for booked rooms, Priority will be given to member holders
        Queue<Customer> waitlist = new PriorityQueue<>();
        // Store rooms, O(1) Search/Lookup & O(1) Inserting/Storage
        BinarySearchTree treeHouse = new BinarySearchTree();

        // Initialize Rooms
        Room room1 = new Room(1, "Single");
        Room room2 = new Room(2, "Double");
        Room room3 = new Room(3, "Suite");

        // Initialize customers
        Customer Ammar = new Customer("AK", "1234567890123456", Boolean.TRUE);

        /*TODO: Integrate Binary Search Tree (BST)
           treeHouse.root = treeHouse.insert(treeHouse.root, room1);
        */

        // Initialize the database & tables
        Database.initializeDatabase();

        /* Add customers in database
        Database.insertCustomer(Ammar.getName(), Ammar.getCardNumber(), Ammar.getLoyaltyMember());

        // Add rooms in database
        Database.insertRoom(room1.getRoomNumber(), room1.getRoomType(), room1.isAvailable());
        Database.insertRoom(room2.getRoomNumber(), room2.getRoomType(), room2.isAvailable());
        Database.insertRoom(room3.getRoomNumber(), room3.getRoomType(), room3.isAvailable());

        / Add bookings in database
        Database.insertBooking(Ammar.getName(), room1.getRoomType(), "2022-01-01", "2022-01-03", "Pool, Gym");
        */

        // View Customers
        Database.viewCustomers();

        // View Rooms
        Database.viewRooms();

        // View Bookings
        Database.viewBookings();

    }

}
