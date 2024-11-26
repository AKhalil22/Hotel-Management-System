import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public static void insertBooking(String customerName, String roomType, String checkInDate, String checkOutDate, String amenities) throws SQLException {
        // Retrieve customer_id and room_id from customers and rooms tables
        int customerId = getCustomerId(customerName);
        int roomId = getRoomId(roomType);

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

    public static void loadRoomDataStructures() {
        // SQL query to select all room data from the rooms table
        String sql = "SELECT room_number, room_type, room_price, is_available FROM rooms;";

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                // Create Room during runtime
                Room room = new Room(resultSet.getInt("room_number"), resultSet.getString("room_type"), resultSet.getDouble("room_price"), resultSet.getBoolean("is_available"));
                // Insert to room tree data structure
                // TODO: HotelManagementSystem.roomTree.insert(HotelManagementSystem.roomTree.root, Integer.parseInt("room_number"));
                // Insert to availableRooms ArrayList
                if (room.isAvailable()) {
                    HotelManagementSystem.availableRooms.add(room);
                }
                // Insert to map data structure
                // TODO: HotelManagementSystem.map.put(room, room.getRoomNumber());
            }
        } catch (SQLException e) {
            System.out.println("Viewing Room table failed: " + e.getMessage());
        }
    }

    public static void loadCustomerDataStructures() {
        // SQL query to select all customer data from the customers table
        String sql = "SELECT id, name, card_number, phone_number, loyalty FROM customers;";

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                // Create Customer during runtime
                Customer customer = new Customer(resultSet.getString("name"), resultSet.getString("card_number"), resultSet.getString("phone_number"), resultSet.getBoolean("loyalty"));
                // Insert to customers ArrayList
                HotelManagementSystem.customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Viewing Customer table failed: " + e.getMessage());
        }
    }

    private static int getCustomerId(String customerName) throws SQLException {
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

    private static int getRoomId(String roomType) throws SQLException {
        String sql = "SELECT id FROM rooms WHERE room_type = ?";

        // Prepare the SQL statement and set the parameter
        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setString(1, roomType);

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



    public static List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT room_number, room_type, room_price FROM rooms WHERE is_available = 1";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String roomType = resultSet.getString("room_type");
                double roomPrice = resultSet.getDouble("room_price");
                rooms.add(new Room(roomNumber, roomType, roomPrice, true));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching available rooms: " + e.getMessage());
        }

        return rooms;
    }

    public static boolean isRoomAvailable(int roomNumber, String checkInDate, String checkOutDate) {
        // Query to check if the room is already booked for the requested dates
        String sql = """
        SELECT COUNT()
        FROM bookings
        WHERE room_number = ?
        AND NOT (check_out_date <= ? OR check_in_date >= ?)
    """;

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, roomNumber); // Set room number
            preStatement.setString(2, checkInDate); // Set check-in date
            preStatement.setString(3, checkOutDate); // Set check-out date

            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1); // Get the number of conflicting bookings
                return count == 0; // If count is 0, the room is available
            }
        } catch (SQLException e) {
            System.out.println("Error checking room availability: " + e.getMessage());
        }

        return false;
    }


    // TODO: Optionally add delete methods

}
