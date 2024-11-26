import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


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
                    GUIRoomInfo roomInfo = new GUIRoomInfo(room.getRoomNumber(), room.getRoomPrice(), roomType, checkInDate, checkOutDate);
                    roomInfo.setVisible(true);
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
