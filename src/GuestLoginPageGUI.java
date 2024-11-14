import javax.swing.*;
import java.awt.*;

public class GuestLoginPageGUI extends JFrame {

    public GuestLoginPageGUI() {
        super("Guest Login Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {

        //Login Page Header
        JLabel loginPageHeader = new JLabel("<html><b>Guest Login Page</b></html>", SwingConstants.CENTER);
        loginPageHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        loginPageHeader.setBounds(300, 50, 400, 50);
        add(loginPageHeader);

        //Username Label
        JLabel usernameLabel = new JLabel("<html><b>Username</b></html>", SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        usernameLabel.setBounds(300, 300, 400, 50);
        add(usernameLabel);

        //Username Text Field
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(300, 350, 400, 50);
        add(usernameTextField);

        //Password Label
        JLabel passwordLabel = new JLabel("<html><b>Password</b></html>", SwingConstants.CENTER);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        passwordLabel.setBounds(300, 425, 400, 50);
        add(passwordLabel);

        //Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(300, 475, 400, 50);
        add(passwordField);

        //Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(485, 575, 50, 50);
        add(loginButton);
    }

}