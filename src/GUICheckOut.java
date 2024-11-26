import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GUICheckOut extends JFrame {
    private double totalPrice;
    private JLabel totalPriceLabel;

    //CHANGE AMENTIES TO BE STORED INTO  HASHED SET

    public GUICheckOut(int roomNumber, double roomPrice, String roomType, String checkInDate, String checkOutDate) {
        super("Checkout");
        this.totalPrice = roomPrice;
        int selectedRoomNumber = roomNumber;
        String selectedRoomType = roomType;
        String selectedCheckInDate = checkInDate;
        String selectedCheckOutDate = checkOutDate;
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Checkout Header
        JLabel checkOutHeader = new JLabel("<html><b>Checkout</b></html>", SwingConstants.CENTER);
        checkOutHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        checkOutHeader.setBounds(300, 50, 400, 50);
        add(checkOutHeader);

        // Horizontal line (JSeparator)
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(50, 120, 900, 2); // Position and width of the line
        add(separator);

        // Room Number Label
        JLabel roomNumberCheckout = new JLabel("Room " + roomNumber, SwingConstants.CENTER);
        roomNumberCheckout.setFont(new Font("Dialog", Font.PLAIN, 20));
        roomNumberCheckout.setBounds(150, 200, 200, 50);
        add(roomNumberCheckout);

        // Room Price Label
        JLabel roomPriceCheckout = new JLabel("$" + roomPrice, SwingConstants.CENTER);
        roomPriceCheckout.setFont(new Font("Dialog", Font.PLAIN, 20));
        roomPriceCheckout.setBounds(400, 200, 200, 50);
        add(roomPriceCheckout);

        // Total Price Label
        totalPriceLabel = new JLabel("Total: $" + totalPrice, SwingConstants.CENTER);
        totalPriceLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        totalPriceLabel.setBounds(650, 200, 200, 50);
        add(totalPriceLabel);

        // Amenities Panel
        JPanel amenitiesPanel = new JPanel();
        amenitiesPanel.setLayout(new GridLayout(3, 1, 10, 10));
        amenitiesPanel.setBounds(150, 300, 200, 150);

        // Checkboxes for amenities
        JCheckBox parkingCheckbox = new JCheckBox("Priority Parking (+$75)");
        JCheckBox mealCardCheckbox = new JCheckBox("Meal Card (+$250)");
        JCheckBox spaCheckbox = new JCheckBox("Spa (+$100)");
        JCheckBox breakfastCheckbox = new JCheckBox("Breakfast Buffet (+50)");

        // Add checkboxes to panel
        amenitiesPanel.add(breakfastCheckbox);
        amenitiesPanel.add(parkingCheckbox);
        amenitiesPanel.add(spaCheckbox);
        amenitiesPanel.add(mealCardCheckbox);
        add(amenitiesPanel);

        // Listener to update the total price in real-time
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                JCheckBox source = (JCheckBox) e.getSource();

                // Update total based on the checkbox state
                if (source == spaCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 100 : -100;
                } else if (source == parkingCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 75 : -75;
                } else if (source == mealCardCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 250 : -250;
                }
                else if (source == breakfastCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 50 : -50;
                }

                // Update the total price label
                totalPriceLabel.setText("Total: $" + totalPrice);
            }
        };

        // Amenity Checkboxes w/Event Listeners
        spaCheckbox.addItemListener(itemListener);
        parkingCheckbox.addItemListener(itemListener);
        mealCardCheckbox.addItemListener(itemListener);
        breakfastCheckbox.addItemListener(itemListener);

        // Customer Information Panel
        JPanel customerInfoPanel = new JPanel();
        customerInfoPanel.setLayout(new GridLayout(3, 2, 10, 10));
        customerInfoPanel.setBounds(150, 500, 700, 150);

        // Name Label and Text Field
        JLabel nameLabel = new JLabel("*Name:");
        JTextField nameField = new JTextField();
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(nameField);

        // Phone # Label and Text Field
        JLabel phoneNumberLabel = new JLabel("*Phone Number:");
        JTextField phoneNumberField = new JTextField();
        customerInfoPanel.add(phoneNumberLabel);
        customerInfoPanel.add(phoneNumberField);

        // Credit Card Label and Text Field
        JLabel creditCardLabel = new JLabel("*Credit Card Number:");
        JTextField creditCardField = new JTextField();
        customerInfoPanel.add(creditCardLabel);
        customerInfoPanel.add(creditCardField);

        //Pay Button
        JButton payButton = new JButton("Pay");
        payButton.setBounds(500, 675, 100, 50);
        add(payButton);

        //Action Button for the pay button
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameField.getText().isEmpty()||creditCardField.getText().isEmpty()||phoneNumberField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Fill Out All Parts");
                }

                else {
                    Customer customer = new Customer(nameField.getText(), creditCardField.getText(), phoneNumberField.getText(), true);
                    Database.insertCustomer(customer.getName(), customer.getCardNumber(), customer.getPhoneNumber(), customer.getLoyaltyMember());


                    HashSet<String> listOfChosenAmenities = new HashSet<>();

                    if (spaCheckbox.isSelected()){
                        listOfChosenAmenities.add("Spa");
                    }
                    if (parkingCheckbox.isSelected()){
                        listOfChosenAmenities.add("Parking");
                    }
                    if (mealCardCheckbox.isSelected()){
                        listOfChosenAmenities.add("MealCard");
                    }
                    if (breakfastCheckbox.isSelected()){
                        listOfChosenAmenities.add("Breakfast");
                    }

                    //Admin admin = new Admin();
                    System.out.println(roomNumber);
                    try {
                        Database.insertBooking(nameField.getText(), roomNumber, checkInDate, checkOutDate, String.join(",", listOfChosenAmenities));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Thank You For Your Purchase");
                    dispose();
                }


            }
        });
        add(customerInfoPanel);
    }
}

