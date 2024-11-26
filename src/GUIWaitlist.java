import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Queue;

public class GUIWaitlist extends JFrame {
    private JPanel listView;
    private JButton bookButton;
    private JButton cancelButton;
    private JScrollPane scrollPane;
    private JTable table;

    private DefaultTableModel tableModel;

    public GUIWaitlist(Queue<Customer> customerBookings) throws SQLException {
        // TODO: Layout Design - https://cdn.prod.website-files.com/60941cc490e6347e586171d0/6271a609e9cba43bcf089265_Queuing.png

        // GUI Initialization
        this.setTitle("Bookings Waitlist");
        this.setContentPane(listView);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Populate table in accordance with customerBookings layout
        this.populateTable();

        System.out.println("Creating customer list...");

        // Loop through customerBookings and add to table
        while (!customerBookings.isEmpty()) {
            System.out.println("Adding Customer");
            Customer customer = customerBookings.poll();

            String name = customer.getName();

            // Get customer key from name
            Integer customerKey = Database.getCustomerId(name);

            // Get customer variables from PriorityQueue
            String roomNum = String.valueOf(Database.getRoomNumber(customerKey));
            String customerName = customer.getName();
            Boolean loyaltyMember = customer.getLoyaltyMember();
            String checkin = customer.getStartDate();
            String checkout = customer.getEndDate();

            // Create a new customer row for the table
            String[] newRow = {roomNum, customerName, loyaltyMember.toString(), checkin, checkout};
            tableModel.addRow(newRow);
        }

        // If Admin books room then
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // Get the selected row index

                if (selectedRow != -1) {

                    // Access the value at a specific column of the selected row
                    Object customerName = table.getValueAt(selectedRow, 1); // Get name column
                    Object customerRoom = table.getValueAt(selectedRow, 0); // Get room number column

                    // Update Room Availability in Database
                    try {
                        Database.updateRoomAvailability(Integer.parseInt(customerRoom.toString()), false);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Remove the selected row
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Customer booking for: " + customerName + " successful.");
                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // Get the selected row index

                if (selectedRow != -1) {

                    // Access the value at a specific column of the selected row
                    Object customerName = table.getValueAt(selectedRow, 1); // Get name column

                    // Remove booking from database
                    try {
                        // Get customer key from name
                        Integer customerKey = Database.getCustomerId(customerName.toString());
                        Database.removeBooking(customerKey);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Remove the selected row
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Customer booking for: " + customerName + " canceled.");

                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
                }
            }
        });
    }

    private void populateTable(){
        String[] columnNames = {"Room Number", "Customer Name", "Loyalty Member", "Check-in", "Check-out"};
        String[][] rowsOfData = {};
        tableModel = new DefaultTableModel(rowsOfData, columnNames);
        this.table.setModel(tableModel);
    }

}