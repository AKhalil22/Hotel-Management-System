import java.sql.*;

public class Database {
    // Path to database file (SQL connection)
    private static final String URL = "jdbc:sqlite:hotel.db";

    /*
    Connection := Allows us to communicate w/the database
    Statement := Allows us to send queries/requests to the database such as: SELECT, INSERT, UPDATE, DELETE
    Result := Acts like a return statement for query statements such as SELECT
     */

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
    public static void insertCustomer(String customerName, String cardNumber, boolean loyalty) {
        String sql = "INSERT INTO customers(name, card_number, loyalty) VALUES(?,?,?)";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setString(1, customerName);
            preStatement.setString(2, cardNumber);
            preStatement.setBoolean(3, loyalty);
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
    public static void insertRoom(Integer roomNumber, String roomType, boolean available) {
        String sql = "INSERT INTO rooms(room_number, room_type, is_available) VALUES(?,?, ?)";

        try (Connection connection = connect(); PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, roomNumber);
            preStatement.setString(2, roomType);
            preStatement.setBoolean(3, available);
            preStatement.executeUpdate();
            System.out.println("Rooms table updated successfully.");
        } catch (SQLException e) {
            System.out.println("Inserting into Room table failed: " + e.getMessage());
        }
    }

    // Query/Get from room table
    public static void viewRooms() {
        String sql = "SELECT * FROM rooms;";

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("Room ID: " + resultSet.getInt("id"));
                System.out.println("Room #: " + resultSet.getInt("room_number"));
                System.out.println("Room Type: " + resultSet.getString("room_type"));
                System.out.println("Available: " + resultSet.getBoolean("available"));
                System.out.println("-------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Viewing Room table failed: " + e.getMessage());
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

    // TODO: Optionally add delete methods

}