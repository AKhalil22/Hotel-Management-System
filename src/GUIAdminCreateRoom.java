import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAdminCreateRoom extends JFrame {
    JPanel panel;
    private JTextField roomNumber;
    private JTextField roomType;
    private JTextField roomPrice;
    private JCheckBox Availability;
    private JButton createAddRoomButton;
    private GUIAdminRooms2 adminRooms; // Reference to GUIAdminRooms

    public GUIAdminCreateRoom(GUIAdminRooms2 adminRooms) {
        // Initialize components
        this.setTitle("Create Room");
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.adminRooms = adminRooms;

        // Verify that user input is correct
        createAddRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roomNumber.getText().isEmpty() || roomType.getText().isEmpty() || roomPrice.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill Out All Parts");
                } else if (roomNumber.getText().matches("[0-9]+") &&  roomType.getText().matches("[a-zA-Z]*+") && roomPrice.getText().matches("[0-9]+")) {
                    createRoom(Integer.parseInt(roomNumber.getText()), roomType.getText(), Double.parseDouble(roomPrice.getText()), Availability.isSelected());
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
            }
        });
    }

    // Method to create new roomNumber & add to database
    public void createRoom (Integer roomNumber, String roomType, double roomPrice, boolean isAvailable) {

        // Verify if room already exists
        if (HotelManagementSystem.roomTree.search(HotelManagementSystem.roomTree.root, roomNumber) != null) {
            JOptionPane.showMessageDialog(null, "Room Already Exists");
            return; // Prevent duplicate room creation
        }

        // Insert/Set to room table in database
        Database.insertRoom(roomNumber, roomType, roomPrice, isAvailable);

        // Insert to room tree data structure
        HotelManagementSystem.roomTree.insert(HotelManagementSystem.roomTree.root, roomNumber, roomType, roomPrice, isAvailable);

        // Notify adminRooms to add the new room to the JTable
        if (adminRooms != null) {
            // Get room location in room tree
            adminRooms.addNewRoomToTable(roomNumber, roomType, roomPrice, isAvailable);
        }

        JOptionPane.showMessageDialog(null, "Room Created Successfully");
        closeGUI();
    }

    public void closeGUI() {
    int result = JOptionPane.showConfirmDialog(null, "Do you want to create another room?", "Create Another Room?", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        GUIAdminCreateRoom newRoomPage = new GUIAdminCreateRoom(adminRooms);
        newRoomPage.setTitle("Create Room");
        newRoomPage.setContentPane(newRoomPage.panel);
        newRoomPage.pack();
        newRoomPage.setVisible(true);
        newRoomPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    } else {
        this.dispose();
    }
}


}
