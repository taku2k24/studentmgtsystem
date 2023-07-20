package mgtsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame {
	private String username;

    public StudentDashboard(String username) {
        super("Student Dashboard");
        this.username = username;
        initializeUI(username);
    }

    private void initializeUI(String username) {
    	
    	// Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // Create the menu bar
        JMenu profileMenu = new JMenu("Profile");
        JMenu modulesMenu = new JMenu("Modules");
        JMenu timetableMenu = new JMenu("Timetable");
        JMenu transcriptMenu = new JMenu("Transcript");
        JMenu paymentsMenu = new JMenu("Payments");

        // Create menu items for each menu
        JMenuItem viewProfileItem = new JMenuItem("View");
        JMenuItem statusModulesItem = new JMenuItem("Status");
        JMenuItem enrollModulesItem = new JMenuItem("Enroll");
        JMenuItem viewTimetableItem = new JMenuItem("View");
        JMenuItem viewTranscriptItem = new JMenuItem("View");

        // Add menu items to menus
        profileMenu.add(viewProfileItem);
        modulesMenu.add(statusModulesItem);
        modulesMenu.add(enrollModulesItem);
        timetableMenu.add(viewTimetableItem);
        transcriptMenu.add(viewTranscriptItem);

        // Add menus to the menu bar
        menuBar.add(profileMenu);
        menuBar.add(modulesMenu);
        menuBar.add(timetableMenu);
        menuBar.add(transcriptMenu);
        menuBar.add(paymentsMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Create the content panel with a background picture
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background picture and draw it on the panel
                Image background = new ImageIcon("C:\\Users\\Admin\\Desktop\\OBJECT ORIENTED").getImage();
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(new BorderLayout());

     // Display the welcome message with the logged-in username
        JLabel welcomeLabel = new JLabel("Welcome, @" + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
        // Add the content panel to the frame
        add(contentPanel);

        // Set frame properties
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showLoginFrame() {
        // Code to show the login frame, similar to the main method in StudentManagementSystem
        // You can create an instance of StudentManagementSystem and show it again
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentDashboard dashboard = new StudentDashboard("john_doe"); // Replace "john_doe" with the logged-in username
            }
        });
    }
}

