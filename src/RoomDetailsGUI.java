import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoomDetailsGUI extends JFrame {

    public RoomDetailsGUI() {
        // Frame Settings
        setTitle("Room Details");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Root Panel
        JPanel rootPanel = new RoundedPanel(30); // Rounded corners with 30px radius
        rootPanel.setBackground(new Color(234, 234, 234)); // Light gray background
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        rootPanel.setLayout(new BorderLayout(20, 20));

        // Title Label
        JLabel titleLabel = new JLabel("Currently Viewing Room: 101", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);

        // Load and scale the image
        ImageIcon roomImageIcon = new ImageIcon("src/images/DefaultSuite.jpg"); // Replace with your image path (MAKE SURE THE IMAGE IS OF TYPE .jpg!)
        Image roomImage = roomImageIcon.getImage().getScaledInstance(roomImageIcon.getIconWidth(), roomImageIcon.getIconHeight(), Image.SCALE_SMOOTH);

        // Create the RoundedImagePanel
        RoundedImagePanel imagePanel = new RoundedImagePanel(30, roomImage); // 30px corner radius
        imagePanel.setPreferredSize(new Dimension(600, 300)); // Match image dimensions

        // Room Features Panel
        JTextArea roomDetails = new JTextArea();
        roomDetails.setText("""
                Room 101 Features:
                
                • Bedroom: A Plush King-Size Bed With Premium Linens For A Restful Night's Sleep.
                • Living Area: A Separate Living Space With Stylish Furnishings, Including A Cozy Sofa And A Dedicated Workspace.
                • Bathroom: An En-Suite Spa-Like Bathroom With A Deep Soaking Tub, Walk-In Rainfall Shower, And Luxurious Amenities.
                • Modern Amenities: High-Speed Wi-Fi, A Flat-Screen TV, Minibar, And Coffee Maker.
                • Views: Large Windows Offering Panoramic Cityscapes Or Serene Garden Views.
                """);
        roomDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        roomDetails.setWrapStyleWord(true);
        roomDetails.setLineWrap(true);
        roomDetails.setOpaque(false);
        roomDetails.setEditable(false); // read-only
        roomDetails.setFocusable(false);
        roomDetails.setMargin(new Insets(10, 10, 10, 10));

        // Wrap JTextArea in JScrollPane
        JScrollPane scrollPane = new JScrollPane(roomDetails);
        scrollPane.setPreferredSize(new Dimension(400, 100)); // Set a preferred size
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);

        JPanel detailsPanel = new RoundedPanel(30); // Rounded corners for the details
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        // Proceed Button with Rounded Corners
        JButton proceedButton = new JButton("Proceed To Checkout") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 255, 200)); // Light green
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        proceedButton.setFont(new Font("Arial", Font.BOLD, 18));
        proceedButton.setFocusPainted(false);
        proceedButton.setBorder(BorderFactory.createEmptyBorder());
        proceedButton.setContentAreaFilled(false);

        // Add Components to Root Panel
        rootPanel.add(titleLabel, BorderLayout.NORTH);
        rootPanel.add(imagePanel, BorderLayout.CENTER);
        rootPanel.add(detailsPanel, BorderLayout.SOUTH);
        rootPanel.add(proceedButton, BorderLayout.PAGE_END);

        // Set Root Panel as Content Pane
        setContentPane(rootPanel);
    }

    // Rounded Panel Class
    static class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false); // Make the panel transparent for rounded corners
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
        }
    }

    // Rounded Image Panel
    static class RoundedImagePanel extends JPanel {
        private int cornerRadius;
        private Image image;

        public RoundedImagePanel(int radius, Image image) {
            this.cornerRadius = radius;
            this.image = image;
            setOpaque(false); // Ensures the panel is transparent except for the painted region
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create a rounded clip shape for the panel
            Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.setClip(clip);

            // Draw the image inside the rounded bounds
            if (image != null) {
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

            // Optional: Add a border to the rounded image
            g2.setClip(null); // Reset the clip to draw outside the rounded area
            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.draw(clip);

            g2.dispose();
        }
    }

    public static void main(String[] args) {
        // Set FlatLaf Look and Feel for modern design
        SwingUtilities.invokeLater(() -> {
            RoomDetailsGUI frame = new RoomDetailsGUI();
            frame.setVisible(true);
        });
    }
}
