import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class ListOfAvailableRoomsGUI extends JFrame {

    private final String roomType;
    private final String checkInDate;
    private final String checkOutDate;
    private final List<Room> availableRooms;
    public ListOfAvailableRoomsGUI(List<Room> availableRooms, String roomType, String checkInDate, String checkOutDate) {
        super("List of Available Rooms");

        this.availableRooms = availableRooms;

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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(1000, 3000));

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBounds(0, 0, 1000, 800);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScrollPane);

        JLabel pageHeader = new JLabel("<html><b>Hotel Name</b></html>", SwingConstants.CENTER);
        pageHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        pageHeader.setBounds(300, 50, 400, 50);
        mainPanel.add(pageHeader);

        JLabel availableRoomsHeader = new JLabel("<html><b>Available Rooms</b></html>", SwingConstants.CENTER);
        availableRoomsHeader.setFont(new Font("Dialog", Font.PLAIN, 24));
        availableRoomsHeader.setBounds(300, 115, 400, 50);
        mainPanel.add(availableRoomsHeader);

        int yPosition = 200;
        for (Room room : availableRooms) {
            JPanel roomBox = new JPanel();
            roomBox.setLayout(null);
            roomBox.setBounds(100, yPosition, 800, 80);
            roomBox.setBorder(new LineBorder(Color.BLACK, 1));
            roomBox.setBackground(Color.WHITE);

            JLabel roomNumberLabel = new JLabel("Room " + room.getRoomNumber());
            roomNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            roomNumberLabel.setBounds(20, 20, 200, 30);
            roomBox.add(roomNumberLabel);

            JLabel roomPriceLabel = new JLabel("Price: $" + room.getRoomPrice() + " per night");
            roomPriceLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            roomPriceLabel.setBounds(250, 20, 200, 30);
            roomBox.add(roomPriceLabel);

            roomBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    RoomInfoGUI roomInfo = new RoomInfoGUI("Room " + room.getRoomNumber(), room.getRoomPrice(), roomType, checkInDate, checkOutDate);
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

            mainPanel.add(roomBox);
            yPosition += 100;
        }
    }




}
