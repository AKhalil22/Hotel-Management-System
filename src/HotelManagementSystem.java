import javax.swing.*;
import java.sql.SQLException;
import java.util.*;

public class HotelManagementSystem {

    // Store customers O(1) Lookup (Time Complexity)
    static ArrayList<Customer> customers = new ArrayList<>();

    static TreeMap<Integer, Customer> customersTreeMap = new TreeMap<>();

    // Key-Value Pair | Key = Room (Object) | Value = Price (Integer)
    // TODO: static TreeMap<Room, Integer> map = new TreeMap<>();

    // Wait-list for booked rooms, Priority will be given to member holders
    static Queue<Customer> waitList = new PriorityQueue<>(Comparator.comparing(Customer::getLoyaltyMember).reversed());

    // Store rooms, O(1) Search/Lookup & O(1) Inserting/Storage
    static BinarySearchTree roomTree = new BinarySearchTree();

    // Store available rooms
    static List<Room> availableRooms = new ArrayList<>();

    public static void firstTimeRun() throws SQLException {

        // If customers list is empty & room tree is empty & bookings wait list is empty reinitialize the program
        if (customers.isEmpty() && roomTree.root == null && waitList.isEmpty()) {
            // Initialize default customers
            Customer Ammar = new Customer("AK", "1234567890123456","5163148991", Boolean.TRUE);
            Customer Raghav = new Customer("Raghav", "5372728473073497","5167375661", Boolean.TRUE);
            Customer Hasan = new Customer("Hasan", "5132643139485327","6318328991", Boolean.FALSE);
            Customer Ayush = new Customer("Ayush", "5467126324069671","6310320001", Boolean.FALSE);

            // Insert customers to database
            Database.insertCustomer(Ammar.getName(), Ammar.getCardNumber(), Ammar.getPhoneNumber(), Ammar.getLoyaltyMember());
            Database.insertCustomer(Raghav.getName(), Raghav.getCardNumber(), Raghav.getPhoneNumber(), Raghav.getLoyaltyMember());
            Database.insertCustomer(Hasan.getName(), Hasan.getCardNumber(), Hasan.getPhoneNumber(), Hasan.getLoyaltyMember());
            Database.insertCustomer(Ayush.getName(), Ayush.getCardNumber(), Ayush.getPhoneNumber(), Ayush.getLoyaltyMember());

            // Initialize default rooms
            Room room1 = new Room(100, "Single", 100, true);
            Room room2 = new Room(101, "Single", 100, true);
            Room room3 = new Room(102, "Single", 100, true);
            Room room4 = new Room(103, "Double", 200, true);
            Room room5 = new Room(104, "Double", 200, true);
            Room room6 = new Room(105, "Double", 200, true);
            Room room7 = new Room(106, "Suite",400, true);
            Room room8 = new Room(107, "Suite",400, true);
            Room room9 = new Room(108, "Suite",400, true);

            // Insert rooms to database
            Database.insertRoom(room1.getRoomNumber(), room1.getRoomType(), room1.getRoomPrice(), room1.isAvailable());
            Database.insertRoom(room2.getRoomNumber(), room2.getRoomType(), room2.getRoomPrice(),room2.isAvailable());
            Database.insertRoom(room3.getRoomNumber(), room3.getRoomType(), room3.getRoomPrice(), room3.isAvailable());
            Database.insertRoom(room4.getRoomNumber(), room4.getRoomType(), room4.getRoomPrice(), room4.isAvailable());
            Database.insertRoom(room5.getRoomNumber(), room5.getRoomType(), room5.getRoomPrice(), room5.isAvailable());
            Database.insertRoom(room6.getRoomNumber(), room6.getRoomType(), room6.getRoomPrice(), room6.isAvailable());
            Database.insertRoom(room7.getRoomNumber(), room7.getRoomType(), room7.getRoomPrice(), room7.isAvailable());
            Database.insertRoom(room8.getRoomNumber(), room8.getRoomType(), room8.getRoomPrice(), room8.isAvailable());
            Database.insertRoom(room9.getRoomNumber(), room9.getRoomType(), room9.getRoomPrice(), room9.isAvailable());

            // Initialize default bookings
            Database.insertBooking(Ammar.getName(), room1.getRoomNumber(), "2022-01-01", "2022-01-03", "Pool, Gym");
            Database.insertBooking(Raghav.getName(), room2.getRoomNumber(), "2022-01-01", "2022-01-03", "Pool, Gym");
            Database.insertBooking(Hasan.getName(), room3.getRoomNumber(), "2022-01-01", "2022-01-03", "Pool, Gym");
            Database.insertBooking(Ayush.getName(), room4.getRoomNumber(), "2022-01-01", "2022-01-03", "Pool, Gym");
        }
    }

    public static void main(String[] args) throws SQLException {

        // Initialize the database tables & data structures
        Database.initializeDatabase();
        Database.loadDataStructures();

        // First time run
        firstTimeRun();

        // GUI
        /*
        Guest: customer name; password: guest
        Admin: admin; password: admin
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUIAdminRooms adminRooms = new GUIAdminRooms();
                new GUILoginPage(availableRooms).setVisible(true);
                // new GuestHomePageGUI(availableRooms).setVisible(true);
//                new GUIAdminPage().setVisible(true);

//                try {
//                    new GUIWaitlist(waitList);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });

    }

}
