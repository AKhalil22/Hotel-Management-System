import javax.swing.*;

public class TestLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            new MainLoginPageGUI().setVisible(true);
            //new AdminLoginPageGUI().setVisible(true);
            //new GuestLoginPageGUI().setVisible(true);
            //new GuestHomePageGUI().setVisible(true);
            //new ListOfAvailableRoomsGUI().setVisible(true);
            //new CheckOutGUI().setVisible(true);
            }
        });
    }
}
