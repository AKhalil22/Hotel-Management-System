import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckOutGUI extends JFrame {
    private double roomPrice = 200;
    private double totalPrice = roomPrice;

    private JLabel totalPriceLabel;

    public CheckOutGUI() {
        super("Checkout");
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

        // Variables
        int roomNumber = 100;

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
        JCheckBox spaCheckbox = new JCheckBox("Spa (+$20)");
        JCheckBox gymCheckbox = new JCheckBox("Gym (+$20)");
        JCheckBox poolCheckbox = new JCheckBox("Pool (+$20)");

        // Add checkboxes to panel
        amenitiesPanel.add(spaCheckbox);
        amenitiesPanel.add(gymCheckbox);
        amenitiesPanel.add(poolCheckbox);
        add(amenitiesPanel);

        // Listener to update the total price in real-time
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                JCheckBox source = (JCheckBox) e.getSource();

                // Update total based on the checkbox state
                if (source == spaCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 20 : -20;
                } else if (source == gymCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 20 : -20;
                } else if (source == poolCheckbox) {
                    totalPrice += (stateChange == ItemEvent.SELECTED) ? 20 : -20;
                }

                // Update the total price label
                totalPriceLabel.setText("Total: $" + totalPrice);
            }
        };

        spaCheckbox.addItemListener(itemListener);
        gymCheckbox.addItemListener(itemListener);
        poolCheckbox.addItemListener(itemListener);

        // Customer Information Panel
        JPanel customerInfoPanel = new JPanel();
        customerInfoPanel.setLayout(new GridLayout(3, 2, 10, 10));
        customerInfoPanel.setBounds(150, 500, 700, 150);

        // Name Label and Text Field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(nameField);

        // Address Label and Text Field
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        customerInfoPanel.add(addressLabel);
        customerInfoPanel.add(addressField);

        // Credit Card Label and Text Field
        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        JTextField creditCardField = new JTextField();
        customerInfoPanel.add(creditCardLabel);
        customerInfoPanel.add(creditCardField);

        //Pay Button
        JButton payButton = new JButton("Pay");
        payButton.setBounds(500, 675, 100, 50);
        add(payButton);


        add(customerInfoPanel);





    }
}
