import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GUIAdminRooms extends JFrame  {
    private JPanel panel;
    private JTable roomsTable;
    private JButton createNewRoomButton;
    private JButton RemoveRoomButton;

    private DefaultTableModel tableModel;
    private static List<Integer> listOfRooms;

    public GUIAdminRooms() {
        this.setTitle("Rooms");
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Populate table in accordance with customerBookings layout
        this.populateTable();

        // Show GUI to create new room
        createNewRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIAdminCreateRoom(GUIAdminRooms.this).setVisible(true);
            }
        });

        // Remove room
        RemoveRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = roomsTable.getSelectedRow(); // Get the selected row index

                if (selectedRow != -1) {

                    // Access the value at a specific column of the selected row
                    Integer roomNumber = (Integer) roomsTable.getValueAt(selectedRow, 0); // Get name column

                    // Remove room from database
                    try {
                        // Get room key from room number
                        Integer roomKey = Database.getRoomId(roomNumber);
                        Database.removeRoom(roomKey); // Remove room from database
                        HotelManagementSystem.roomTree.deleteRoom(HotelManagementSystem.roomTree.root, roomNumber);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Remove the selected row
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Room: " + roomNumber + " Deleted.");

                } else {
                    JOptionPane.showMessageDialog(null, "No Room Selected.");
                }
            }
        });
    }

    private void populateTable(){
        String[] columnNames = {"Room Number", "Room Type", "Room Price", "Is Available"};
        String[][] rowsOfData = {};
        tableModel = new DefaultTableModel(rowsOfData, columnNames);
        this.roomsTable.setModel(tableModel);

        BinarySearchTree roomTree = HotelManagementSystem.roomTree;

        // Traverse though Binary Search Tree (in-order) and add rooms to table
        listOfRooms = new ArrayList<>();
        listOfRooms = roomTree.inorder(roomTree.root, listOfRooms);

        for (Integer roomNumber : listOfRooms) {
            System.out.println("Adding room: " + roomNumber);

            // Get room data from BST
            String roomType = roomTree.search(roomTree.root, roomNumber).roomType;
            double roomPrice = roomTree.search(roomTree.root, roomNumber).roomPrice;
            boolean isAvailable = roomTree.search(roomTree.root, roomNumber).isAvailable;

            // Add to row in table
            addRoomToTable(roomNumber, roomType, roomPrice, isAvailable);

        }

    }

    // Add room to table method for populating initial table
    public void addRoomToTable(int roomNumber, String roomType, double roomPrice, boolean isAvailable) {
        tableModel.addRow(new Object[]{roomNumber, roomType, roomPrice, isAvailable});
    }

    // Modified Binary Search Algorithm to find insertion index
    public int findInsertionIndex(int roomNumber) {
        int rowCount = tableModel.getRowCount();
        int low = 0, high = rowCount - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents overflow
            int midRoomNumber = (int) tableModel.getValueAt(mid, 0);

            if (midRoomNumber == roomNumber) {
                return mid; // Room already exists (unlikely in this case)
            } else if (midRoomNumber < roomNumber) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low; // Correct insertion index
    }

    // Adds newly created rooms to table after finding insertion index
    public void addNewRoomToTable(int roomNumber, String roomType, double roomPrice, boolean isAvailable) {
        int insertionIndex = findInsertionIndex(roomNumber);
        tableModel.insertRow(insertionIndex, new Object[]{roomNumber, roomType, roomPrice, isAvailable});
    }

    //tree map to JTable method
    private JPanel createCustomerTablePanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Main heading label
        JLabel customerTableLabel = new JLabel("<html><b>Customer Table 2</b></html>", SwingConstants.CENTER);
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

        // Create JTable with customer data and make it non editable
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