import javax.swing.*;
import java.awt.*;

public class MainLoginPageGUI extends JFrame {

    public MainLoginPageGUI(){
        super("Login Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setSize(1000,800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {

        //Login Page Header
        JLabel loginPageHeader = new JLabel("<html><b>Login Page</b></html>", SwingConstants.CENTER);
        loginPageHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        loginPageHeader.setBounds(300, 50, 400, 50);
        add(loginPageHeader);

        //Guest Login Button
        JButton guestLoginButton = new JButton("Guest Login");
        guestLoginButton.setBounds(150, 300, 200, 200);
        add(guestLoginButton);

        //Admin Login Button
        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setBounds(650, 300, 200, 200);
        add(adminLoginButton);
    }

}
