import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

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
        tabbedPane.add("Customer Table", createCustomerTablePanel2());
        tabbedPane.add("Rooms Table", createRoomsTablePanel());
        tabbedPane.add("Bookings Table", createBookingsTablePanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBookingsTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Main heading label
        JLabel bookingsTableLabel = new JLabel("<html><b>Bookings Table</b></html>", SwingConstants.CENTER);
        bookingsTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        panel.add(bookingsTableLabel, BorderLayout.NORTH);

        try {
            Queue<Customer> customerBookings = HotelManagementSystem.waitList;
            GUIWaitList2 waitListPanel = new GUIWaitList2(customerBookings);

            // Embed the GUIWaitList2 panel into this tab
            panel.add(waitListPanel, BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading wait list data", SwingConstants.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createRoomsTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Main heading label
        JLabel roomsTableLabel = new JLabel("<html><b>Rooms Table</b></html>", SwingConstants.CENTER);
        roomsTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        panel.add(roomsTableLabel, BorderLayout.NORTH);

        GUIAdminRooms2 guiAdminRooms = new GUIAdminRooms2();

        // Embed the GUIAdminRooms2 panel into this tab
        panel.add(guiAdminRooms, BorderLayout.CENTER);

        return panel;
    }

    /*
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
        table.setBounds(150, 150, 700, 400);

        //Adding scroll pane into JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 150, 700, 400);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add components to panel
        panel.add(customerTableLabel);

        return panel;
    }
     */

    //tree map to JTable method
    private JPanel createCustomerTablePanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Main heading label
        JLabel customerTableLabel = new JLabel("<html><b>Customer Table</b></html>", SwingConstants.CENTER);
        customerTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        customerTableLabel.setBounds(300, 50, 400, 50);

        //  column names
        String[] columnNames = {"ID", "Name", "Card Number", "Phone Number", "Loyalty Member"};

        // Convert TreeMap into 2D array
        TreeMap<Integer, Customer> customersTreeMap = HotelManagementSystem.customersTreeMap;
        Object[][] customerData = new Object[customersTreeMap.size()][5];

        int rowIndex = 0;
        for (Map.Entry<Integer, Customer> entry : customersTreeMap.entrySet()) {
            Integer id = entry.getKey();
            Customer customer = entry.getValue();

            customerData[rowIndex][0] = id; // ID from TreeMap key
            customerData[rowIndex][1] = customer.getName();
            customerData[rowIndex][2] = customer.getCardNumber();
            customerData[rowIndex][3] = customer.getPhoneNumber();
            customerData[rowIndex][4] = customer.getLoyaltyMember() ? "Yes" : "No";

            rowIndex++;
        }

        // Create JTable with customer data and make it non-editable
        JTable table = new JTable(new DefaultTableModel(customerData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });


        // Add scroll pane to JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 150, 700, 400);
        panel.add(scrollPane);

        panel.add(customerTableLabel);

        return panel;
    }

}