import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class GuestHomePageGUI extends JFrame {
    public GuestHomePageGUI() {
        super("Home Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {

        //Guest Page Header
        JLabel guestPageHeader = new JLabel("<html><b>Home Page</b></html>", SwingConstants.CENTER);
        guestPageHeader.setFont(new Font("Dialog", Font.PLAIN, 36));
        guestPageHeader.setBounds(300, 50, 400, 50);
        add(guestPageHeader);

        //Calender Setup
        UtilDateModel checkInModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        //Check In Label
        JLabel checkInLabel = new JLabel("<html><b>Check In Date</b></html>");
        checkInLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        checkInLabel.setBounds(30, 250, 400, 50);
        add(checkInLabel);


        //Check In Calendar
        JDatePanelImpl checkInDatePanel = new JDatePanelImpl(checkInModel, properties);
        JDatePickerImpl checkInDatePicker = new JDatePickerImpl(checkInDatePanel, new DateLabelFormatter());
        checkInDatePicker.setBounds(30, 300, 200, 30);
        add(checkInDatePicker);


        //Check Out Label
        JLabel checkOutLabel = new JLabel("<html><b>Check Out Date</b></html>");
        checkOutLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        checkOutLabel.setBounds(250, 250, 400, 50);
        add(checkOutLabel);

        //Check Out Calendar
        UtilDateModel checkOutModel = new UtilDateModel();
        JDatePanelImpl checkOutDatePanel = new JDatePanelImpl(checkOutModel, properties);
        JDatePickerImpl checkOutDatePicker = new JDatePickerImpl(checkOutDatePanel, new DateLabelFormatter());
        checkOutDatePicker.setBounds(250, 300, 200, 30); // Positioning check-out date picker
        add(checkOutDatePicker);

        //Room Type Label
        JLabel numOfBedsLabel = new JLabel("<html><b>Room Type</b></html>");
        numOfBedsLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        numOfBedsLabel.setBounds(700, 250, 400, 50);
        add(numOfBedsLabel);

        //Number of Beds Combo Box
        String[] numOfBeds = {"Single", "Double", "Suite"};
        JComboBox<String> numOfBedsComboBox = new JComboBox<>(numOfBeds);
        numOfBedsComboBox.setBounds(700, 290, 125, 50);
        add(numOfBedsComboBox);

        //Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        searchButton.setBounds(550, 350, 75, 50);
        add(searchButton);


    }


}
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "MM-dd-yyy";
    private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) {
        if (value != null) {
            java.util.Calendar cal = (java.util.Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
