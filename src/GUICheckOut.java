import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GUICheckOut extends JFrame {
    private double totalPrice;
    private JLabel totalPriceLabel;

    public GUICheckOut(int roomNumber, double roomPrice, String roomType, String checkInDate, String checkOutDate, int stayDuration) {
        super("Checkout");
        this.totalPrice = roomPrice * stayDuration;

        // Frame setup
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout for flexibility
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Header
        JLabel checkOutHeader = new JLabel("<html><b>Checkout</b></html>", SwingConstants.CENTER);
        checkOutHeader.setFont(new Font("Dialog", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        mainPanel.add(checkOutHeader, gbc);

        // Room Details Panel
        JPanel roomDetailsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        roomDetailsPanel.add(new JLabel("Room: " + roomNumber, SwingConstants.CENTER));
        roomDetailsPanel.add(new JLabel("Price: $" + roomPrice, SwingConstants.CENTER));
        totalPriceLabel = new JLabel("Total: $" + totalPrice, SwingConstants.CENTER);
        roomDetailsPanel.add(totalPriceLabel);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        mainPanel.add(roomDetailsPanel, gbc);

        // Amenities Panel
        JPanel amenitiesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        amenitiesPanel.setBorder(BorderFactory.createTitledBorder("Select Amenities"));
        JCheckBox parkingCheckbox = new JCheckBox("Priority Parking (+$75)");
        JCheckBox mealCardCheckbox = new JCheckBox("Meal Card (+$250)");
        JCheckBox spaCheckbox = new JCheckBox("Spa (+$100)");
        JCheckBox breakfastCheckbox = new JCheckBox("Breakfast Buffet (+$50)");
        amenitiesPanel.add(breakfastCheckbox);
        amenitiesPanel.add(parkingCheckbox);
        amenitiesPanel.add(spaCheckbox);
        amenitiesPanel.add(mealCardCheckbox);
        gbc.gridy = 2;
        gbc.weighty = 0.3;
        mainPanel.add(amenitiesPanel, gbc);

        // Amenities Listener
        ItemListener itemListener = e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            int change = (e.getStateChange() == ItemEvent.SELECTED) ? 1 : -1;

            if (source == spaCheckbox) totalPrice += change * 100;
            else if (source == parkingCheckbox) totalPrice += change * 75;
            else if (source == mealCardCheckbox) totalPrice += change * 250;
            else if (source == breakfastCheckbox) totalPrice += change * 50;

            totalPriceLabel.setText("Total: $" + totalPrice);
        };

        spaCheckbox.addItemListener(itemListener);
        parkingCheckbox.addItemListener(itemListener);
        mealCardCheckbox.addItemListener(itemListener);
        breakfastCheckbox.addItemListener(itemListener);

        // Customer Information Panel
        JPanel customerInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        customerInfoPanel.setBorder(BorderFactory.createTitledBorder("Customer Information"));
        customerInfoPanel.add(new JLabel("*Name:"));
        JTextField nameField = new JTextField();
        customerInfoPanel.add(nameField);
        customerInfoPanel.add(new JLabel("*Phone Number:"));
        JTextField phoneField = new JTextField();
        customerInfoPanel.add(phoneField);
        customerInfoPanel.add(new JLabel("*Credit Card:"));
        JTextField cardField = new JTextField();
        customerInfoPanel.add(cardField);
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        mainPanel.add(customerInfoPanel, gbc);

        // Pay Button
        JButton payButton = new JButton("Pay");
        gbc.gridy = 4;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(payButton, gbc);


        // Pay Button Action
        payButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || cardField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all required fields.");
            } else {
                Customer customer = new Customer(nameField.getText(), cardField.getText(), phoneField.getText(), true);
                Database.insertCustomer(customer.getName(), customer.getCardNumber(), customer.getPhoneNumber(), customer.getLoyaltyMember());

                HashSet<String> selectedAmenities = new HashSet<>();
                if (spaCheckbox.isSelected()) selectedAmenities.add("Spa");
                if (parkingCheckbox.isSelected()) selectedAmenities.add("Parking");
                if (mealCardCheckbox.isSelected()) selectedAmenities.add("Meal Card");
                if (breakfastCheckbox.isSelected()) selectedAmenities.add("Breakfast");

                try {
                    Database.insertBooking(customer.getName(), roomNumber, checkInDate, checkOutDate, String.join(",", selectedAmenities));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error processing booking.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Thank you for your booking!");
                dispose();
            }
        });
    }
}
