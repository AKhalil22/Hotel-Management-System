import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GUIListOfAvailableRooms extends JFrame {
    private final String roomType;
    private final String checkInDate;
    private final String checkOutDate;
    private final List<Room> availableRooms;
    private final JComboBox<String> roomTypeComboBox;


    public GUIListOfAvailableRooms(List<Room> availableRooms, JComboBox<String> roomTypeComboBox, String roomType, String checkInDate, String checkOutDate) {
        super("List of Available Rooms");

        this.availableRooms = availableRooms;
        this.roomTypeComboBox = roomTypeComboBox;

        // Store the additional parameters
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Create a panel to add all the components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(1000, 3000));

        // Create a scroll pane to hold the panel
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBounds(0, 0, 1000, 800);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScrollPane);

        // Create a label to display the hotel name
        JLabel pageHeader = new JLabel("<html><b>Hotel Name</b></html>", SwingConstants.CENTER);
        pageHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        pageHeader.setBounds(300, 50, 400, 50);
        mainPanel.add(pageHeader);

        // Create a label to display the header above the list of available rooms
        JLabel availableRoomsHeader = new JLabel("<html><b>Available Rooms</b></html>", SwingConstants.CENTER);
        availableRoomsHeader.setFont(new Font("Dialog", Font.PLAIN, 24));
        availableRoomsHeader.setBounds(300, 115, 400, 50);
        mainPanel.add(availableRoomsHeader);

        // Set the initial y position for the first room box
        int yPosition = 200;

        GUIGuestHomePage guiGuestHomePage = new GUIGuestHomePage(availableRooms);
        String roomTypeSelected = guiGuestHomePage.getRoomTypeWanted(roomTypeComboBox);
        List<Room> roomListWithSelectedRoomType = new ArrayList<>();

        for (Room room : availableRooms) {
            if (room.getRoomType().equals(roomTypeSelected)) {
                roomListWithSelectedRoomType.add(room);
            }
        }



        // Iterate over the list of available rooms and add a box for each room
        for (Room room : roomListWithSelectedRoomType) {
            // Create a panel for each room
            JPanel roomBox = new JPanel();
            roomBox.setLayout(null);
            roomBox.setBounds(100, yPosition, 800, 80);
            roomBox.setBorder(new LineBorder(Color.BLACK, 1));
            roomBox.setBackground(Color.WHITE);

            // Create a label to display the room number
            JLabel roomNumberLabel = new JLabel("Room " + room.getRoomNumber());
            roomNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            roomNumberLabel.setBounds(20, 20, 200, 30);
            roomBox.add(roomNumberLabel);

            // Create a label to display the room price
            JLabel roomPriceLabel = new JLabel("Price: $" + room.getRoomPrice() + " per night");
            roomPriceLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            roomPriceLabel.setBounds(250, 20, 200, 30);
            roomBox.add(roomPriceLabel);

            // Add a mouse listener to the room box to open the room info page when clicked
            roomBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    String roomImage = null;
                    String roomDetails = null;

                    // Choose random image
                    Random random = new Random();
                    int index = random.nextInt(2);

                    if (roomType.equals("Suite")) {

                        String[] defaultSuiteImages = {"DefaultSuite1.jpg", "DefaultSuite2.png"};
                        roomImage = defaultSuiteImages[index];

                        roomDetails = String.format("""
                            Room %s Features:
                        
                            • Bedroom: A Plush King-Size Bed With Premium Linens For A Restful Night's Sleep.
                            • Living Area: A Separate Living Space With Stylish Furnishings, Including A Cozy Sofa And A Dedicated Workspace.
                            • Bathroom: An En-Suite Spa-Like Bathroom With A Deep Soaking Tub, Walk-In Rainfall Shower, And Luxurious Amenities.
                            • Modern Amenities: High-Speed Wi-Fi, A Flat-Screen TV, Minibar, And Coffee Maker.
                            • Views: Large Windows Offering Panoramic Cityscapes Or Serene Garden Views.
                        """, room.getRoomNumber());

                    } else if (roomType.equals("Double")) {

                        String[] defaultDoubleImages = {"DefaultDouble1.jpg", "DefaultDouble2.png"};
                        roomImage = defaultDoubleImages[index];

                        roomDetails = String.format("""
                            Room %s Features:
                        
                            • Bedroom: Two Plush Queen-Size Beds With Premium Linens For A Comfortable Stay.
                            • Living Area: A Cozy Seating Area With Stylish Furnishings And A Functional Workspace.
                            • Bathroom: A Spacious Bathroom Featuring A Walk-In Rainfall Shower And Complimentary Luxurious Amenities.
                            • Modern Amenities: High-Speed Wi-Fi, A Flat-Screen TV, Minibar, And Coffee Maker.
                            • Views: Expansive Windows Offering Cityscape Or Tranquil Garden Views.
                        """, room.getRoomNumber());

                    } else { // Room type is single

                        String[] defaultSingleImages = {"DefaultSingle1.jpg", "DefaultSingle2.png"};
                        roomImage = defaultSingleImages[index];

                        roomDetails = String.format("""
                            Room %s Features:
                        
                            • Bedroom: A Plush Twin-Size Bed With Premium Linens For A Restful Night.
                            • Living Area: A Compact Yet Functional Space With A Comfortable Armchair And Small Work Desk.
                            • Bathroom: A Well-Appointed Bathroom With A Walk-In Shower And Essential Amenities.
                            • Modern Amenities: High-Speed Wi-Fi, A Flat-Screen TV, And A Coffee Maker.
                            • Views: Large Window With Courtyard Or Garden Views For A Relaxing Ambiance.
                        """, room.getRoomNumber());

                    }

                    new GUIRoomDetails(room.getRoomNumber(), roomImage, roomDetails, room.getRoomPrice(), room.getRoomType(), checkInDate, checkOutDate);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    roomBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    roomBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            // Add the room box to the main panel
            mainPanel.add(roomBox);

            // Increment the y position for the next room box
            yPosition += 100;
        }
    }


}