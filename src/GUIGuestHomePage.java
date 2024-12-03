import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class GUIGuestHomePage extends JFrame {

    private final List<Room> availableRooms;

    public GUIGuestHomePage(List<Room> availableRooms) {
        this.availableRooms = availableRooms;

        // Set frame properties
        setTitle("Dream Stay");
        double scaleFactor = 1.3;
        setSize((int) (1920 / scaleFactor), (int) (1080 / scaleFactor));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String hotelImage;
        Random random = new Random();
        int index = random.nextInt(2);
        String[] defaultHotelImages = {"CityscapeHotel.gif", "WaterViewHotel.gif"};
        hotelImage = defaultHotelImages[index];

        // Add custom background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/" + hotelImage);
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Create a container panel for center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Make it transparent so the background shows
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // Add top spacer for balancing height
        centerPanel.add(Box.createVerticalGlue()); // Spacer for dynamic centering

        // Add title label
        JLabel titleLabel = new JLabel("Find Your Dream Stay!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 44));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);

        // Add labeled form components panel
        JPanel labeledForm = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set color for the background of the panel
                g2d.setColor(new Color(255, 255, 255, 229)); // Semi-transparent white

                // Draw a rounded rectangle
                int arcWidth = 15;  // Width of the curve
                int arcHeight = 15; // Height of the curve
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
            }
        };
        labeledForm.setLayout(new GridLayout(1, 4, 10, 0));
        labeledForm.setOpaque(false); // Make sure the panel background is painted via paintComponent
        labeledForm.setMaximumSize(new Dimension(600, 120)); // Adjust the height for labels
        labeledForm.setPreferredSize(new Dimension(600, 120));
        labeledForm.setAlignmentX(Component.CENTER_ALIGNMENT);

        labeledForm.setLayout(new GridLayout(1, 4, 10, 0));
        labeledForm.setBackground(new Color(255, 255, 255, 229)); // Semi-transparent white
        labeledForm.setMaximumSize(new Dimension(600, 75)); // Adjust the height for labels
        labeledForm.setPreferredSize(new Dimension(600, 75));
        labeledForm.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Calendar Setup
        UtilDateModel checkInModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        // Check In Calendar
        JDatePanelImpl checkInDatePanel = new JDatePanelImpl(checkInModel, properties);
        JDatePickerImpl checkInDatePicker = new JDatePickerImpl(checkInDatePanel, new DateLabelFormatter());

        // Check Out Calendar
        UtilDateModel checkOutModel = new UtilDateModel();
        JDatePanelImpl checkOutDatePanel = new JDatePanelImpl(checkOutModel, properties);
        JDatePickerImpl checkOutDatePicker = new JDatePickerImpl(checkOutDatePanel, new DateLabelFormatter());

        // Add form components
        JComboBox<String> roomTypeBox = new JComboBox<>(new String[]{"Room Type", "Single", "Double", "Suite"});
        JButton searchButton = new JButton("Search Now");

        // Panel for "Check-In" label and date picker
        JPanel checkInPanel = new JPanel();
        checkInPanel.setLayout(new BoxLayout(checkInPanel, BoxLayout.Y_AXIS));
        checkInPanel.setOpaque(false);
        JLabel checkInLabel = new JLabel("Check-In Date");
        checkInLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkInPanel.add(checkInLabel);
        checkInPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add small space
        checkInPanel.add(checkInDatePicker);

        // Panel for "Check-Out" label and date picker
        JPanel checkOutPanel = new JPanel();
        checkOutPanel.setLayout(new BoxLayout(checkOutPanel, BoxLayout.Y_AXIS));
        checkOutPanel.setOpaque(false);
        JLabel checkOutLabel = new JLabel("Check-Out Date");
        checkOutLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkOutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkOutPanel.add(checkOutLabel);
        checkOutPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add small space
        checkOutPanel.add(checkOutDatePicker);

        // Panel for "Room Type" label and dropdown
        JPanel roomTypePanel = new JPanel();
        roomTypePanel.setLayout(new BoxLayout(roomTypePanel, BoxLayout.Y_AXIS));
        roomTypePanel.setOpaque(false);

        JLabel roomTypeLabel = new JLabel("Room Type");
        roomTypeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomTypePanel.add(roomTypeLabel);

        roomTypePanel.add(Box.createRigidArea(new Dimension(0, -15)));
        roomTypePanel.add(roomTypeBox);

        // Panel for "Search" button
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel(""); // Empty label to align with others
        searchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(searchLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        searchPanel.add(searchButton);

        // Add all panels to the labeled form
        labeledForm.add(checkInPanel);
        labeledForm.add(checkOutPanel);
        labeledForm.add(roomTypePanel);
        labeledForm.add(searchPanel);

        // Add labeled form to center panel
        centerPanel.add(labeledForm);
        centerPanel.add(Box.createVerticalGlue()); // Spacer for dynamic centering

        // User clicks checkout Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get Stay Duration
                int stayDuration = (int) getStayDuration(checkInDatePicker, checkOutDatePicker);
                System.out.println("Stay Duration: " + stayDuration);

                // Get Check-In Date
                Object checkInDateObj = checkInDatePicker.getModel().getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String checkInDate = "";
                if (checkInDateObj != null) {
                    checkInDate = sdf.format(checkInDateObj); // Check-in date stored in this variable
                }

                // Get Check-Out Date
                Object checkOutDateObj = checkOutDatePicker.getModel().getValue();
                String checkOutDate = "";
                if (checkOutDateObj != null) {
                    checkOutDate = sdf.format(checkOutDateObj); // Check-out date stored in this variable
                }

                // Get Room Type
                String roomType = getRoomTypeWanted(roomTypeBox); // Retrieve the selected room type

                // Check if both dates are selected
                if (checkInDate.isEmpty() || checkOutDate.isEmpty() || roomType == "Room Type") {
                    JOptionPane.showMessageDialog(GUIGuestHomePage.this,
                            "Please select both check-in and check-out dates or room type.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Pass additional parameters to ListOfAvailableRoomsGUI
                    dispose();
                    new GUIListOfAvailableRooms(availableRooms, roomTypeBox, roomType, checkInDate, checkOutDate, stayDuration).setVisible(true);
                }
            }
        });

    }


    //method to get the type of room wanted
    public String getRoomTypeWanted(JComboBox<String> typeOfRoomComboBox){
        return (String) typeOfRoomComboBox.getSelectedItem();
    }

    // Method to get the checkin date
    public String getCheckInDate(JDatePickerImpl checkInDatePicker) {
        Object checkInDateObj = checkInDatePicker.getModel().getValue();
        if (checkInDateObj != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            return sdf.format(checkInDateObj);
        }
        return "";
    }

    // Method to get the checkout date
    public String getCheckOutDate(JDatePickerImpl checkOutDatePicker) {
        Object checkOutDateObj = checkOutDatePicker.getModel().getValue();
        if (checkOutDateObj != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            return sdf.format(checkOutDateObj);
        }
        return "";
    }

    // Get stay duration for guest
    public long getStayDuration(JDatePickerImpl checkInDatePicker, JDatePickerImpl checkOutDatePicker) {
        String checkInDateStr = getCheckInDate(checkInDatePicker);
        String checkOutDateStr = getCheckOutDate(checkOutDatePicker);

        if (checkInDateStr.isEmpty() || checkOutDateStr.isEmpty()) {
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date checkInDate = sdf.parse(checkInDateStr);
            Date checkOutDate = sdf.parse(checkOutDateStr);

            long stayDuration = checkOutDate.getTime() - checkInDate.getTime();
            return stayDuration / (1000 * 60 * 60 * 24); // convert to days
        } catch (ParseException e) {
            System.out.println("Error parsing dates: " + e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIGuestHomePage app = new GUIGuestHomePage(HotelManagementSystem.availableRooms);
            app.setVisible(true);
        });
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "MM-dd-yyy";
    private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) {
        if (value != null) {
            java.util.Calendar cal = (java.util.Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }

}

// Custom JPanel for Background Image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}