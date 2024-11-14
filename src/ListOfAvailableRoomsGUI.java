
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListOfAvailableRoomsGUI extends JFrame {
    public ListOfAvailableRoomsGUI() {
        super("List of Available Rooms");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(1000, 3000));

        // Add main panel to scroll pane
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBounds(0, 0, 1000, 800);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScrollPane);

        // Page Header
        JLabel pageHeader = new JLabel("<html><b>Hotel Name</b></html>", SwingConstants.CENTER);
        pageHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        pageHeader.setBounds(300, 50, 400, 50);
        mainPanel.add(pageHeader);

        // Available Room Label
        JLabel availableRoomsHeader = new JLabel("<html><b>Available Rooms</b></html>", SwingConstants.CENTER);
        availableRoomsHeader.setFont(new Font("Dialog", Font.PLAIN, 24));
        availableRoomsHeader.setBounds(300, 115, 400, 50);
        mainPanel.add(availableRoomsHeader);

        // Sample data for rooms
        String[][] rooms = {
                {"Room 101", "$100 per night"},
                {"Room 102", "$120 per night"},
                {"Room 103", "$90 per night"},
                {"Room 104", "$150 per night"},
                {"Room 105", "$110 per night"}
        };

        // Adding boxes for each rook
        int yPosition = 200;
        for (String[] room : rooms) {
            String roomName = room[0];
            String roomPrice = room[1];

            // JPanel for box for each room
            JPanel roomBox = new JPanel();
            roomBox.setLayout(null);
            roomBox.setBounds(100, yPosition, 800, 80);
            roomBox.setBorder(new LineBorder(Color.BLACK, 1));
            roomBox.setBackground(Color.WHITE);

            // Room number label
            JLabel roomNumberLabel = new JLabel(roomName);
            roomNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            roomNumberLabel.setBounds(20, 20, 200, 30);
            roomBox.add(roomNumberLabel);

            // Room price label
            JLabel roomPriceLabel = new JLabel("Price: " + roomPrice);
            roomPriceLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            roomPriceLabel.setBounds(250, 20, 200, 30);
            roomBox.add(roomPriceLabel);

            // Add MouseListener to open RoomInfoGUI when clicked
            roomBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    RoomInfoGUI roomInfo = new RoomInfoGUI(roomName);
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
