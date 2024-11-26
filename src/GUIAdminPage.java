import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GUIAdminPage extends JFrame {

    public GUIAdminPage() {
        super("Admin Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setLayout(new BorderLayout());
        addGuiComponents();
    }

    private void addGuiComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Customer Table", createCustomerTablePanel());
        tabbedPane.add("Rooms Table", createRoomsTablePanel());
        tabbedPane.add("Bookings Table", createBookingsTablePanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBookingsTablePanel() {
        JPanel panel = new JPanel();
        JLabel bookingsTableLabel = new JLabel("<html><b>Bookings Table</b></html>");
        bookingsTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        panel.add(bookingsTableLabel);
        return panel;
    }

    private JPanel createRoomsTablePanel() {
        JPanel panel = new JPanel();
        JLabel roomsTableLabel = new JLabel("<html><b>Rooms Table</b></html>");
        roomsTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));

        //Define Column Name
        String[] columnNames = {"Room Number", "Room Type", "Room Price", "Is Available"};

        //Convert Tree to 2D Array
        BinarySearchTree rooms = HotelManagementSystem.roomTree;

        panel.add(roomsTableLabel);
        return panel;
    }

    private JPanel createCustomerTablePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Main heading label
        JLabel customerTableLabel = new JLabel("<html><b>Customer Table</b></html>", SwingConstants.CENTER);
        customerTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        customerTableLabel.setBounds(300, 50, 400, 50);

        //Defines column names
        String[] columnNames = {"Name", "Card Number", "Phone Number", "Loyalty Member"};

        //Convert ArrayList into 2d Array
        ArrayList<Customer> customers = HotelManagementSystem.customers;
        Object[][] customerData = new Object[customers.size()][4];
        for (int i = 0; i<customers.size(); i++){
            Customer customer = customers.get(i);
            customerData[i][0] = customer.getName();
            customerData[i][1] = customer.getCardNumber();
            customerData[i][2] = customer.getPhoneNumber();
            customerData[i][3] = customer.getLoyaltyMember() ? "Yes" : "No";
        }

        //JTable
        DefaultTableModel tableModel = new DefaultTableModel(customerData, columnNames);
        JTable table = new JTable(tableModel);
        table.setBounds(300, 150, 400, 400);

        //Adding scroll pane into JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 150, 400, 400);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add components to panel
        panel.add(customerTableLabel);

        return panel;
    }




}

/*

Customer Table
for customer table be able to sort the whole table via their ID and loyalty
and have search bar to find a certain name in the database

Room Table
Sort by room type and room number
search bar for certain room number


 */