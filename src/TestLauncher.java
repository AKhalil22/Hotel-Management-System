import javax.swing.*;
import java.util.List;

public class TestLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                List<Room> availableRooms = Database.getAvailableRooms();
                new LoginPageGUI(availableRooms).setVisible(true);
//                new GuestHomePageGUI(availableRooms).setVisible(true);
//                new AdminPageGUI().setVisible(true);
            }
        });
    }
}
