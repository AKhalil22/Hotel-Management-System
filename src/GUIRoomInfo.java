import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIRoomInfo extends JFrame {
    public GUIRoomInfo(int roomNumber, double roomPrice, String roomType, String checkInDate, String checkOutDate) {
        super("Room " + roomNumber + " Info");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents(roomNumber, roomPrice, roomType, checkInDate, checkOutDate);
    }

    private void addGuiComponents(int roomNumber, double roomPrice, String roomType, String checkInDate, String checkOutDate) {
        //Room Info Header
        JLabel roomInfoHeader = new JLabel("<html><b>" + "Room: " + roomNumber + "</b></html>", SwingConstants.CENTER);
        roomInfoHeader.setFont(new Font("Dialog",Font.PLAIN, 36));
        roomInfoHeader.setBounds(300, 50, 400, 50);
        add(roomInfoHeader);

        //Checkout Button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("Dialog",Font.PLAIN, 36));
        checkoutButton.setBounds(300, 500, 400, 50);
        add(checkoutButton);

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUICheckOut(roomNumber, roomPrice, roomType, checkInDate, checkOutDate).setVisible(true);
            }
        });
    }

}
