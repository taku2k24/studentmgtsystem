import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class StudentDashboard extends JFrame {
    private String username;
    private Color orangeShade = new Color(255, 140, 0); // Shade of orange
    private Color greyShade = new Color(100, 100, 100); // Shade of grey
    private Color whiteShade = Color.WHITE;

    public StudentDashboard(String username) {
        super("Student Dashboard");
        this.username = username;
        initializeUI(username);
    }

    private void initializeUI(String username) {
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the menus
        JMenu profileMenu = new JMenu("Profile");
        JMenu modulesMenu = new JMenu("Modules");
        JMenu paymentsMenu = new JMenu("Payments");
        JMenu timetableMenu = new JMenu("Timetable");
        JMenu transcriptMenu = new JMenu("Transcript");

        // Add menus to the menu bar
        menuBar.add(profileMenu);
        menuBar.add(modulesMenu);
        menuBar.add(paymentsMenu);
        menuBar.add(timetableMenu);
        menuBar.add(transcriptMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Adding MenuItem for functionality
        JMenuItem profileMenuItem = new JMenuItem("View Profile");
        profileMenuItem.addActionListener(e -> openProfileWindow());
        profileMenuItem.setForeground(orangeShade);
        profileMenu.add(profileMenuItem);

        JMenuItem enrollMenuItem = new JMenuItem("View Enroll");
        enrollMenuItem.addActionListener(e -> openEnrollWindow());
        enrollMenuItem.setForeground(orangeShade);
        modulesMenu.add(enrollMenuItem);

        JMenuItem payMenuItem = new JMenuItem("View Payment");
        payMenuItem.addActionListener(e -> openPayWindow());
        payMenuItem.setForeground(orangeShade);
        paymentsMenu.add(payMenuItem);

        JMenuItem timetableMenuItem = new JMenuItem("View Timetable");
        timetableMenuItem.addActionListener(e -> openTimetableWindow());
        timetableMenuItem.setForeground(orangeShade);
        timetableMenu.add(timetableMenuItem);

        JMenuItem transcriptMenuItem = new JMenuItem("View Transcript");
        transcriptMenuItem.addActionListener(e -> openTranscriptWindow());
        transcriptMenuItem.setForeground(orangeShade);
        transcriptMenu.add(transcriptMenuItem);

        // Create the content panel with a grey background
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(greyShade);
        contentPanel.setLayout(new BorderLayout());

        // Display the welcome message with the logged-in username
        JLabel welcomeLabel = new JLabel("Welcome, @" + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(whiteShade); // Set the welcome message color to white
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Add the content panel to the frame
        add(contentPanel);

        // Set frame properties
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openProfileWindow() {
        Profile profileFrame = new Profile(username);
        // profile.setVisible(true);
    }

    private void openEnrollWindow() {
        Enrollment enrollFrame = new Enrollment(username);
        // profile.setVisible(true);
    }

    private void openPayWindow() {
        Payment payFrame = new Payment(username);
        // profile.setVisible(true);
    }

    private void openTimetableWindow() {
        Timetable timetableFrame = new Timetable(username);
        // profile.setVisible(true);
    }

    private void openTranscriptWindow() {
        Transcript transcriptFrame = new Transcript(username);
        // profile.setVisible(true);
    }
}

