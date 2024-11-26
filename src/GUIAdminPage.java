import javax.swing.*;
import java.awt.*;

public class GUIAdminPage extends JFrame {
    public GUIAdminPage() {
        super("Admin Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setLayout(new BorderLayout());
        addGuiComponents();
    }

    private void addGuiComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Customer Table", createCustomerTablePanel());
        tabbedPane.add("Rooms Table", createRoomsTablePanel());
        tabbedPane.add("Bookings Table", createBookingsTablePanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBookingsTablePanel() {
        JPanel panel = new JPanel();
        JLabel bookingsTableLabel = new JLabel("<html><b>Bookings Table</b></html>");
        bookingsTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        panel.add(bookingsTableLabel);
        return panel;
    }

    private JPanel createRoomsTablePanel() {
        JPanel panel = new JPanel();
        JLabel roomsTableLabel = new JLabel("<html><b>Rooms Table</b></html>");
        roomsTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        panel.add(roomsTableLabel);
        return panel;
    }

    private JPanel createCustomerTablePanel() {
        JPanel panel = new JPanel();
        JLabel customerTableLabel = new JLabel("<html><b>Customer Table</b></html>");
        customerTableLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        panel.add(customerTableLabel);
        return panel;
    }

}

/*

Customer Table
for customer table be able to sort the whole table via their ID and loyalty
and have search bar to find a certain name in the database

Room Table
Sort by room type and room number
search bar for certain room number


 */