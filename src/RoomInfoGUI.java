import javax.swing.*;
import java.awt.*;

public class RoomInfoGUI extends JFrame {
    public RoomInfoGUI(String roomName) {
        super("List of Available Rooms");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {
        //Room Info Header
        JLabel roomInfoHeader = new JLabel("<html><b>Login Page</b></html>", SwingConstants.CENTER);
        roomInfoHeader.setFont(new Font("Dialog",Font.PLAIN, 36));
        roomInfoHeader.setBounds(300, 50, 400, 50);
        add(roomInfoHeader);
    }

}
