import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class GuestHomePageGUI extends JFrame {

    private final List<Room> availableRooms;

    public GuestHomePageGUI(List<Room> availableRooms) {
        super("Home Page");
        this.availableRooms =  availableRooms;
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

        //Type of Room Combo Box
        String[] typeOfRoom = {"Single", "Double", "Suite"};
        JComboBox<String> typeOfRoomComboBox = new JComboBox<>(typeOfRoom);
        typeOfRoomComboBox.setBounds(700, 290, 125, 50);
        add(typeOfRoomComboBox);

        //Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        searchButton.setBounds(550, 350, 75, 50);
        add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get Check-In Date
                Object checkInDateObj = checkInDatePicker.getModel().getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String checkInDate = "";
                if (checkInDateObj != null) {
                    checkInDate = sdf.format(checkInDateObj);//!!checkin date stored in this variable!!
                }

                // Get Check-Out Date
                Object checkOutDateObj = checkOutDatePicker.getModel().getValue();
                String checkOutDate = "";
                if (checkOutDateObj != null) {
                    checkOutDate = sdf.format(checkOutDateObj);//!!checkout date stored in this variable!!
                }


                //if both dates aren't selected
                if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                    JOptionPane.showMessageDialog(GuestHomePageGUI.this,
                            "Please select both check-in and check-out dates.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    dispose();
                    new ListOfAvailableRoomsGUI(availableRooms).setVisible(true);
                }


            }
        });


    }

    //method to get the type of room wanted
    public String getRoomTypeWanted(JComboBox<String> typeOfRoomComboBox){
        return (String) typeOfRoomComboBox.getSelectedItem();
    }

    //method to get the check in date
    public String getCheckInDate(JDatePickerImpl checkInDatePicker) {
        Object checkInDateObj = checkInDatePicker.getModel().getValue();
        if (checkInDateObj != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            return sdf.format(checkInDateObj);
        }
        return "";
    }

    //method to get the check out date
    public String getCheckOutDate(JDatePickerImpl checkOutDatePicker) {
        Object checkOutDateObj = checkOutDatePicker.getModel().getValue();
        if (checkOutDateObj != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            return sdf.format(checkOutDateObj);
        }
        return "";
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
