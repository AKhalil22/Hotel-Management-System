import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Queue;

public class GUIWaitList2 extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton bookButton;
    private JButton cancelButton;

    public GUIWaitList2(Queue<Customer> customerBookings) throws SQLException {
        // Set up the layout for this panel
        setLayout(new BorderLayout());

        // Create and set up the table
        this.populateTable();

        // Add scroll pane with table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        bookButton = new JButton("Book");
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(bookButton);
        buttonsPanel.add(cancelButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Populate the table with customer bookings
        populateBookings(customerBookings);

        // Set up event listeners for buttons
        setupButtonListeners();
    }

    private void populateTable() {
        String[] columnNames = {"Room Number", "Customer Name", "Loyalty Member", "Check-in", "Check-out"};
        String[][] rowsOfData = {};
        tableModel = new DefaultTableModel(rowsOfData, columnNames);
        table = new JTable(tableModel);
    }

    private void populateBookings(Queue<Customer> customerBookings) throws SQLException {
        while (!customerBookings.isEmpty()) {
            Customer customer = customerBookings.poll();

            String name = customer.getName();
            Integer customerKey = Database.getCustomerId(name);

            String roomNum = String.valueOf(Database.getRoomNumber(customerKey));
            String customerName = customer.getName();
            Boolean loyaltyMember = customer.getLoyaltyMember();
            String checkin = customer.getStartDate();
            String checkout = customer.getEndDate();

            String[] newRow = {roomNum, customerName, loyaltyMember.toString(), checkin, checkout};
            tableModel.addRow(newRow);
        }
    }

    private void setupButtonListeners() {
        bookButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object customerName = table.getValueAt(selectedRow, 1);
                Object customerRoom = table.getValueAt(selectedRow, 0);
                try {
                    Database.updateRoomAvailability(Integer.parseInt(customerRoom.toString()), false);
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Customer booking for: " + customerName + " successful.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No customer selected.");
            }
        });

        cancelButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object customerName = table.getValueAt(selectedRow, 1);
                try {
                    Integer customerKey = Database.getCustomerId(customerName.toString());
                    Database.removeBooking(customerKey);
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Customer booking for: " + customerName + " canceled.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No customer selected.");
            }
        });
    }
}

