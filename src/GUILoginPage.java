import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUILoginPage extends JFrame {

    private List<Room> availableRooms;

    public GUILoginPage(List<Room> availableRooms) {
        super("Login Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.availableRooms = availableRooms;

        setSize(1000, 800);
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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String passwordStr = new String(passwordField.getPassword());

                if (Database.isValidUsername(usernameTextField.getText()) && passwordStr.equals("guest")) {
                    dispose();
                    new GUIGuestHomePage(availableRooms).setVisible(true);
                }

                else if (usernameTextField.getText().equalsIgnoreCase("admin") && passwordStr.equals("admin")) {
                    new GUIAdminPage().setVisible(true);
                }

                else {
                    JOptionPane.showMessageDialog(GUILoginPage.this,"Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}