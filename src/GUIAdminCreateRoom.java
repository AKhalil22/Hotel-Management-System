import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAdminCreateRoom extends JFrame {
    private JPanel panel;
    private JTextField roomNumber;
    private JTextField roomType;
    private JTextField roomPrice;
    private JCheckBox Availability;
    private JButton createAddRoomButton;

    public GUIAdminCreateRoom() {
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
        // Insert/Set to room table in database
        Database.insertRoom(roomNumber, roomType, roomPrice, isAvailable);
        // Insert to room tree data structure
        HotelManagementSystem.roomTree.insert(HotelManagementSystem.roomTree.root, roomNumber);

        JOptionPane.showMessageDialog(null, "Room Created Successfully");
        closeGUI();
    }

    public void closeGUI() {
    int result = JOptionPane.showConfirmDialog(null, "Do you want to create another room?", "Create Another Room?", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        GUIAdminCreateRoom newRoomPage = new GUIAdminCreateRoom();
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

    public void createGUI() {
        GUIAdminCreateRoom createRoomPage = new GUIAdminCreateRoom();
        createRoomPage.setTitle("Create Room");
        createRoomPage.setContentPane(createRoomPage.panel);
        createRoomPage.pack();
        createRoomPage.setVisible(true);
        createRoomPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        GUIAdminCreateRoom createRoomPage = new GUIAdminCreateRoom();
        createRoomPage.setTitle("Create Room");
        createRoomPage.setContentPane(createRoomPage.panel);
        createRoomPage.pack();
        createRoomPage.setVisible(true);
        createRoomPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
