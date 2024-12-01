import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class GUIRoomDetails extends JFrame {
    private JPanel panel;
    private JLabel roomTitle;
    private JLabel roomImageLabel;
    private JTextArea roomDescription;
    private JButton checkoutButton;


    public GUIRoomDetails(int roomNumber, String roomImageName, String roomDetails, double roomPrice, String roomType, String checkInDate, String checkOutDate) {

        // Initialize Components
        setTitle("Room Details");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(810, 750);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        setLocationRelativeTo(null);
        setVisible(true);

        // Title Label customization
        roomTitle.setText("Currently Viewing Room: " + roomNumber);
        roomTitle.setFont(new Font("Arial", Font.BOLD, 24));
        roomTitle.setForeground(Color.BLACK);
        roomTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Custom Image Panel
        ImageIcon roomImageIcon = new ImageIcon("src/images/" + roomImageName); // Adjust the path
        // Adjust the width and height accordingly to match the panel size (40 = padding)
        Image roomImage = roomImageIcon.getImage().getScaledInstance(panel.getWidth() - 40, (int) (panel.getHeight()/1.5), Image.SCALE_SMOOTH);
        roomImageLabel.setIcon(new ImageIcon(roomImage));

        // Room Details
        roomDescription.setText(roomDetails);
        roomDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        roomDescription.setEditable(false);

        // Proceed Button customization
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Checkout Button Clicked
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUICheckOut(roomNumber, roomPrice, roomType, checkInDate, checkOutDate).setVisible(true);
            }
        });
    }

    // Test Run
    public static void main(String[] args) {

        String roomDetails = """
                Room 101 Features:

                • Bedroom: A Plush King-Size Bed With Premium Linens For A Restful Night's Sleep.
                • Living Area: A Separate Living Space With Stylish Furnishings, Including A Cozy Sofa And A Dedicated Workspace.
                • Bathroom: An En-Suite Spa-Like Bathroom With A Deep Soaking Tub, Walk-In Rainfall Shower, And Luxurious Amenities.
                • Modern Amenities: High-Speed Wi-Fi, A Flat-Screen TV, Minibar, And Coffee Maker.
                • Views: Large Windows Offering Panoramic Cityscapes Or Serene Garden Views.
                """;

        GUIRoomDetails main = new GUIRoomDetails(101, "DefaultSuite1.jpg", roomDetails, 300.00, "Suite", "2023-07-02", "2023-07-03");
    }
}