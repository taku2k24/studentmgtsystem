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
        profileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProfileWindow();
            }
        });
        profileMenu.add(profileMenuItem);

        JMenuItem EnrollMenuItem = new JMenuItem("View Enroll");
        EnrollMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEnrollWindow();
            }
        });
        modulesMenu.add(EnrollMenuItem);

        JMenuItem PayMenuItem = new JMenuItem("View Payment");
        PayMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPayWindow();
            }
        });
        paymentsMenu.add(PayMenuItem);

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

    private void openProfileWindow() {
        Profile profileFrame = new Profile(username);
        // profile.setVisible(true);
    }

    private void openEnrollWindow() {
        Enrollment enrollFrame = new Enrollment();
        // profile.setVisible(true);
    }

    private void openPayWindow() {
        Payment payFrame = new Payment(username);
        // profile.setVisible(true);
    }
}

