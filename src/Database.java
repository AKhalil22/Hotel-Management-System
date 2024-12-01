import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    // Path to database file (SQL connection)
    private static final String URL = "jdbc:sqlite:hotel.db";

    /*
    Connection := Allows us to communicate w/the database
    Statement := Allows us to send queries/requests to the database such as: SELECT, INSERT, UPDATE, DELETE
    Result := Acts like a return statement for query statements such as SELECT
     */

    // Load all data into designated data structures
    public static void loadDataStructures() {
        loadRoomDataStructures();
        loadCustomerDataStructures();
        loadCustomerDataStructuresIntoTreeMap();
        System.out.println("All data structures loaded successfully.");
    }

    // One-Method Call to initialize all tables from HMS
    public static void initializeDatabase() {
        createCustomerTable();
        createRoomTable();
        createBookingTable();
        System.out.println("All database tables initialized successfully.");
    }

    // Establish connection to SQLite database
    public static Connection connect() {
        Connection connection = null; // Initialize Connection
        // Try-Catch to connect to database
        try {
            // Built-In Java method to connect to databases
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection; // Return so database can be queried
    }

    // Create/Initialize SQLite bookings table
    public static void createBookingTable() {
        // TODO: If getting errors: https://youtu.be/jM4KnPiedK0?t=228
        // SQL statement for creating a new table (Database Schema/Blueprint/Layout)
        String sql = """ 
            CREATE TABLE IF NOT EXISTS bookings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                customer_id INTEGER NOT NULL,
                room_id INTEGER NOT NULL,
                check_in_date TEXT NOT NULL,
                check_out_date TEXT NOT NULL,
                amenities TEXT, -- Comma separated list of amenities
                FOREIGN KEY (customer_id) REFERENCES customers(id),
                FOREIGN KEY (room_id) REFERENCES rooms(id)
            );
            """;

        // Connect to table if exists, else print error
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Bookings table created or exists.");
        } catch (SQLException e) {
            System.out.println("Error creating Booking table: " + e.getMessage());
        }
    }

    // Insert/Set to bookings table
    public static void insertBooking(String customerName, Integer roomNumber, String checkInDate, String checkOutDate, String amenities) throws SQLException {
        // Retrieve customer_id and room_id from customers and rooms tables
        int customerId = getCustomerId(customerName);
        int roomId = getRoomId(roomNumber);

        String sql = "INSERT INTO bookings(customer_id, room_id, check_in_date, check_out_date, amenities) VALUES(?,?,?,?,?)";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, customerId);
            preStatement.setInt(2, roomId);
            preStatement.setString(3, checkInDate);
            preStatement.setString(4, checkOutDate);
            preStatement.setString(5, amenities);
            preStatement.executeUpdate(); // Update database
            System.out.println("Bookings table updated successfully.");
        } catch (SQLException e) {
            System.out.println("Inserting into Bookings table failed: " + e.getMessage());
        }
    }


    // Query/Get from bookings table
    public static void viewBookings() {
        String sql = """
        SELECT b.id, c.name AS customer_name, r.room_type, b.check_in_date, b.check_out_date, b.amenities
        FROM bookings b
        JOIN customers c ON b.customer_id = c.id
        JOIN rooms r ON b.room_id = r.id
        """;

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql); ResultSet resultSet = preStatement.executeQuery(sql)) {
            // Loop/Iterate through each entry and print data in set
            while (resultSet.next()) {
                System.out.println("Booking ID: " + resultSet.getInt("id"));
                System.out.println("Customer: " + resultSet.getString("customer_name"));
                System.out.println("Room Type: " + resultSet.getString("room_type"));
                System.out.println("Check-in: " + resultSet.getString("check_in_date"));
                System.out.println("Check-out: " + resultSet.getString("check_out_date"));
                System.out.println("Amenities: " + resultSet.getString("amenities"));
                System.out.println("---------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Viewing Bookings table failed: " + e.getMessage());
        }
    }

    // Create/Initialize SQLite table customer table
    public static void createCustomerTable() {
        // Best practice is to store card number as text (Though can be changed to type: INTEGER)
        String sql = """
            CREATE TABLE IF NOT EXISTS customers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                phone_number TEXT NOT NULL,
                card_number TEXT NOT NULL,
                loyalty BOOLEAN
            );
            """;

        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Customers table created or exists.");
        } catch (SQLException e) {
            System.out.println("Error creating Customer table: " + e.getMessage());
        }
    }

    // Insert/Set to customer table
    public static void insertCustomer(String customerName, String cardNumber, String phoneNumber, boolean loyalty) {
        String sql = "INSERT INTO customers(name, card_number, phone_number, loyalty) VALUES(?,?,?,?)";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setString(1, customerName);
            preStatement.setString(2, cardNumber);
            preStatement.setString(3, phoneNumber);
            preStatement.setBoolean(4, loyalty);
            preStatement.executeUpdate();
            System.out.println("Customers table updated successfully.");
        } catch (SQLException e) {
            System.out.println("Inserting into Customer table failed: " + e.getMessage());
        }
    }

    // Query/Get from customer table
    public static void viewCustomers() {
        String sql = "SELECT * FROM customers;";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql); ResultSet resultSet = preStatement.executeQuery()) {
            // Loop/Iterate through each entry and print data in set
            while (resultSet.next()) {
                System.out.println("Customer ID: " + resultSet.getInt("id"));
                System.out.println("Customer: " + resultSet.getString("name"));
                System.out.println("Card Number: " + resultSet.getString("card_number"));
                System.out.println("Phone Number: " + resultSet.getString("phone_number"));
                System.out.println("Loyalty: " + resultSet.getBoolean("loyalty"));
                System.out.println("-------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Viewing Customer table failed: " + e.getMessage());
        }

    }

    // Create/Initialize room table
    public static void createRoomTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS rooms (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            room_number INTEGER NOT NULL,
            room_type TEXT NOT NULL,
            room_price REAL NOT NULL,
            is_available BOOLEAN NOT NULL
        );
        """;

        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Rooms table created or exists.");
        } catch (SQLException e) {
            System.out.println("Error creating Room table: " + e.getMessage());
        }
    }

    // Insert/Set to room table
    public static void insertRoom(Integer roomNumber, String roomType, double roomPrice, boolean isAvailable) {
        String sql = "INSERT INTO rooms(room_number, room_type, room_price, is_available) VALUES(?,?,?,?)";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, roomNumber);
            preStatement.setString(2, roomType);
            preStatement.setDouble(3, roomPrice);
            preStatement.setBoolean(4, isAvailable);
            preStatement.executeUpdate();
            System.out.println("Rooms table updated successfully.");
        } catch (SQLException e) {
            System.out.println("Inserting into Room table failed: " + e.getMessage());
        }
    }

    // Query/Get from room table
    public static void viewRooms() {
        String sql = "SELECT room_number, room_type, room_price, is_available FROM rooms;";

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("Room #: " + resultSet.getInt("room_number"));
                System.out.println("Room Type: " + resultSet.getString("room_type"));
                System.out.println("Price: $" + resultSet.getDouble("room_price"));
                System.out.println("Available: " + (resultSet.getBoolean("is_available") ? "Yes" : "No"));
                System.out.println("-------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Viewing Room table failed: " + e.getMessage());
        }
    }

    // Load data structures utilizing rooms
    public static void loadRoomDataStructures() {
        // SQL query to select all room data from the rooms table
        String sql = "SELECT room_number, room_type, room_price, is_available FROM rooms;";

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                // Create Room during runtime
                Room room = new Room(resultSet.getInt("room_number"), resultSet.getString("room_type"), resultSet.getDouble("room_price"), resultSet.getBoolean("is_available"));

                // Insert to room into Binary Search Tree
                HotelManagementSystem.roomTree.insert(HotelManagementSystem.roomTree.root, room.getRoomNumber(), room.getRoomType(), room.getRoomPrice(), room.isAvailable());

                // Insert to availableRooms ArrayList
                if (room.isAvailable()) {
                    HotelManagementSystem.availableRooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.out.println("Viewing Room table failed: " + e.getMessage());
        }
    }

    // Load data structures utilizing customers
    public static void loadCustomerDataStructures() {
        // SQL query to select all customers
        String sqlCustomers = "SELECT id, name, card_number, phone_number, loyalty FROM customers;";
        // SQL query to select all bookings
        String sqlBookings = "SELECT customer_id, check_in_date, check_out_date FROM bookings;";

        try (Connection connection = connect()) {
            // Load all customers
            Map<Integer, Customer> customerMap = new HashMap<>();
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlCustomers)) {
                while (resultSet.next()) {
                    // Create Customer instance
                    Customer customer = new Customer(
                            resultSet.getString("name"),
                            resultSet.getString("card_number"),
                            resultSet.getString("phone_number"),
                            resultSet.getBoolean("loyalty")
                    );

                    // Store in ArrayList and map for quick lookup
                    HotelManagementSystem.customers.add(customer);
                    customerMap.put(resultSet.getInt("id"), customer);
                }
            }

            // Load all bookings and match them with customers
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlBookings)) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");
                    Customer customer = customerMap.get(customerId);
                    if (customer != null) {
                        // Update customer with booking info
                        customer.setStartDate(resultSet.getString("check_in_date"));
                        customer.setEndDate(resultSet.getString("check_out_date"));

                        // Add customer to waitlist Priority Queue
                        HotelManagementSystem.waitList.add(customer);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }

    public static void loadCustomerDataStructuresIntoTreeMap() {
        // SQL query to select all customers
        String sqlCustomers = "SELECT id, name, card_number, phone_number, loyalty FROM customers;";
        // SQL query to select all bookings
        String sqlBookings = "SELECT customer_id, check_in_date, check_out_date FROM bookings;";

        try (Connection connection = connect()) {
            // Load all customers
            Map<Integer, Customer> customerMap = new HashMap<>();
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlCustomers)) {

                while (resultSet.next()) {
                    // Create Customer instance
                    Customer customer = new Customer(
                            resultSet.getString("name"),
                            resultSet.getString("card_number"),
                            resultSet.getString("phone_number"),
                            resultSet.getBoolean("loyalty")
                    );

                    // Store in ArrayList and map for quick lookup
                    int customerId = resultSet.getInt("id");
                    HotelManagementSystem.customersTreeMap.put(customerId, customer);
                    customerMap.put(resultSet.getInt("id"), customer);
                }
            }

            // Load all bookings and match them with customers
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlBookings)) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");
                    Customer customer = customerMap.get(customerId);
                    if (customer != null) {
                        // Update customer with booking info
                        customer.setStartDate(resultSet.getString("check_in_date"));
                        customer.setEndDate(resultSet.getString("check_out_date"));

                        // Add customer to waitList Priority Queue
                        HotelManagementSystem.waitList.add(customer);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }

    // Remove room from rooms table
    public static void removeRoom(Integer roomId) throws SQLException {
        String sql = "DELETE FROM rooms WHERE id = ?";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, roomId);
            preStatement.executeUpdate();
        }
    }

    // Return customer ID from customer name
    public static int getCustomerId(String customerName) throws SQLException {
        String sql = "SELECT id FROM customers WHERE name = ?";

        // Establish a connection and prepare the SQL statement
        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            // Set the customer name parameter in the SQL query
            preStatement.setString(1, customerName);

            // Execute the query and process the result set
            try (ResultSet resultSet = preStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Return the customer ID if found
                } else {
                    throw new SQLException("Customer not found"); // Throw exception if customer is not found
                }
            }
        }
    }

    // Return room ID from room number
    public static int getRoomId(Integer roomNumber) throws SQLException {
        String sql = "SELECT id FROM rooms WHERE room_number = ?";

        // Prepare the SQL statement and set the parameter
        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setString(1, String.valueOf(roomNumber));

            // Execute the query and process the result set
            try (ResultSet resultSet = preStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Return the room ID if found
                } else {
                    throw new SQLException("Room not found"); // Throw exception if room is not found
                }
            }
        }
    }

    // Return booked room number from customer ID via bookings table
    public static int getRoomNumber(Integer customerId) throws SQLException {
        String sql = "SELECT room_id FROM bookings WHERE customer_id = ?";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, customerId);

            try (ResultSet resultSet = preStatement.executeQuery()) {
                if (resultSet.next()) {
                    int roomId = resultSet.getInt("room_id");
                    return getRoomNumberFromRoomTable(roomId);
                } else {
                    throw new SQLException("No booking found for customer ID " + customerId);
                }
            }
        }
    }

    // Helper method to get the room number from the room table
    private static int getRoomNumberFromRoomTable(int roomId) throws SQLException {
        String sql = "SELECT room_number FROM rooms WHERE id = ?";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, roomId);

            try (ResultSet resultSet = preStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("room_number");
                } else {
                    throw new SQLException("No room found with ID " + roomId);
                }
            }
        }
    }

    // Update room availability after booking is accepted by admin
    public static void updateRoomAvailability(Integer roomNumber, boolean isAvailable) throws SQLException {
        String sql = "UPDATE rooms SET is_available = ? WHERE room_number = ?";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setBoolean(1, isAvailable);
            preStatement.setInt(2, roomNumber);
            preStatement.executeUpdate();
        }
    }

    // Remove booking from bookings table if admin cancels
    public static void removeBooking(Integer customerId) throws SQLException {
        String sql = "DELETE FROM bookings WHERE customer_id = ?";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, customerId);
            preStatement.executeUpdate();
        }
    }

    // Login Verification method for GUILoginPage
    public static boolean isValidUsername(String username) {
        String sql = "SELECT COUNT(*) AS count FROM customers WHERE name = ?";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setString(1, username);
            try (ResultSet resultSet = preStatement.executeQuery()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error validating username: " + e.getMessage());
        }
        return false;
    }

    // TODO: Optionally add delete methods

}
