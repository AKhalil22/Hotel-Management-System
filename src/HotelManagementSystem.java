import java.sql.SQLException;
import java.util.*;

public class HotelManagementSystem {

    public static void main(String[] args) throws SQLException {
        // Store customers O(1) Lookup (Time Complexity)
        ArrayList<Customer> customers = new ArrayList<>();

        // Key-Value Pair | Key = Room (Object) | Value = Price (Integer)
        TreeMap<Room, Integer> map = new TreeMap<>();

        // Wait-list for booked rooms, Priority will be given to member holders
        Queue<Customer> waitList = new PriorityQueue<>();

        // Store rooms, O(1) Search/Lookup & O(1) Inserting/Storage
        BinarySearchTree roomTree = new BinarySearchTree();

        // Initialize Rooms
        Room room1 = new Room(100, "Single", 100);
        Room room2 = new Room(101, "Single", 100);
        Room room3 = new Room(102, "Single", 100);
        Room room4 = new Room(103, "Double", 200);
        Room room5 = new Room(104, "Double", 200);
        Room room6 = new Room(105, "Double",200);
        Room room7 = new Room(106, "Suite",400);
        Room room8 = new Room(107, "Suite",400);
        Room room9 = new Room(108, "Suite",400);

        // Initialize customers
        Customer Ammar = new Customer("AK", "1234567890123456","5163148991", Boolean.TRUE);


        /*TODO: Integrate Binary Search Tree (BST)
           treeHouse.root = treeHouse.insert(treeHouse.root, room1);
        */

        // Initialize the database & tables
        Database.initializeDatabase();

        //Add customers in database
        Database.insertCustomer(Ammar.getName(), Ammar.getCardNumber(), Ammar.getPhoneNumber(), Ammar.getLoyaltyMember());

        // Add rooms in database
        Database.insertRoom(room1.getRoomNumber(), room1.getRoomType(), room1.getRoomPrice(), room1.isAvailable());
        Database.insertRoom(room2.getRoomNumber(), room2.getRoomType(), room2.getRoomPrice(),room2.isAvailable());
        Database.insertRoom(room3.getRoomNumber(), room3.getRoomType(), room3.getRoomPrice(), room3.isAvailable());
        Database.insertRoom(room4.getRoomNumber(), room4.getRoomType(), room4.getRoomPrice(), room4.isAvailable());
        Database.insertRoom(room5.getRoomNumber(), room5.getRoomType(), room5.getRoomPrice(), room5.isAvailable());
        Database.insertRoom(room6.getRoomNumber(), room6.getRoomType(), room6.getRoomPrice(), room6.isAvailable());
        Database.insertRoom(room7.getRoomNumber(), room7.getRoomType(), room7.getRoomPrice(), room7.isAvailable());
        Database.insertRoom(room8.getRoomNumber(), room8.getRoomType(), room8.getRoomPrice(), room8.isAvailable());
        Database.insertRoom(room9.getRoomNumber(), room9.getRoomType(), room9.getRoomPrice(), room9.isAvailable());


        //Add bookings in database
        Database.insertBooking(Ammar.getName(), room1.getRoomType(), "2022-01-01", "2022-01-03", "Pool, Gym");


        // View Customers
        Database.viewCustomers();

        // View Rooms
        Database.viewRooms();

        // View Bookings
        Database.viewBookings();

    }

}
