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

public class GUIAdminRooms2 extends JPanel {
    private JTable roomsTable;
    private JButton createNewRoomButton;
    private JButton RemoveRoomButton;

    private DefaultTableModel tableModel;
    private static List<Integer> listOfRooms;

    public GUIAdminRooms2() {
        // Set up the layout for this panel
        setLayout(new BorderLayout());

        // Create and set up the table
        this.populateTable();

        // Add scroll pane with table
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        createNewRoomButton = new JButton("Create New Room");
        RemoveRoomButton = new JButton("Remove Room");
        buttonsPanel.add(createNewRoomButton);
        buttonsPanel.add(RemoveRoomButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Set up event listeners for buttons
        createNewRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIAdminCreateRoom(GUIAdminRooms2.this).setVisible(true);
            }
        });

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
        roomsTable = new JTable(tableModel);

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
}