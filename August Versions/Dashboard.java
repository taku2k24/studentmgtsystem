import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Dashboard extends JFrame {
    private String username;

    private Color orangeShade = new Color(255, 140, 0); 
    private Color greyShade = new Color(100, 100, 100); 
    private Color lightorangeShade = new Color(255, 237, 227); // For bg color

    private Color whiteShade = Color.WHITE;
    private int i = 0;

    public Dashboard(String username) {
        super("Student Dashboard");
        this.username = username;
        initializeUI(username);
    }

    private void initializeUI(String username) {
        // Create the content panel with a grey background
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(lightorangeShade);
        contentPanel.setLayout(new GridLayout(6, 1));

        // Display the welcome message with the logged-in username
        JLabel welcomeLabel = new JLabel("Welcome, @" + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(greyShade); // Set the welcome message color to white
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Creating the menu panel with buttons
        JButton[] menuBtn = new JButton[5];
        menuBtn[0] = new JButton("Profile");
        menuBtn[1] = new JButton("Modules");
        menuBtn[2] = new JButton("Payment");
        menuBtn[3] = new JButton("Timetable");
        menuBtn[4] = new JButton("Transcript");

        //Adding the buttons to content panel
        JPanel[] btnContainer = new JPanel[5];
        for (i = 0; i <= 4; i++){
            btnContainer[i] = new JPanel(new FlowLayout());
            btnContainer[i].setBackground(lightorangeShade);
            menuBtn[i].setFont(new Font("Arial", Font.PLAIN, 16));
            menuBtn[i].setBackground(Color.WHITE);
            menuBtn[i].setForeground(Color.BLACK);
            btnContainer[i].add(menuBtn[i]);
            contentPanel.add(btnContainer[i]);
        }

        // Adding functionality to the buttons
        menuBtn[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProfileWindow();
            }
        });
        menuBtn[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEnrollWindow();
            }
        });
        menuBtn[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPayWindow();
            }
        });
        menuBtn[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTimetableWindow();
            }
        });
        menuBtn[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTranscriptWindow();
            }
        });

        // Hover effect for the buttons
        menuBtn[0].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuBtn[0].setBackground(orangeShade);
                menuBtn[0].setForeground(whiteShade);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuBtn[0].setBackground(Color.WHITE);
                menuBtn[0].setForeground(Color.BLACK);
            }
        });
        menuBtn[1].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuBtn[1].setBackground(orangeShade);
                menuBtn[1].setForeground(whiteShade);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuBtn[1].setBackground(Color.WHITE);
                menuBtn[1].setForeground(Color.BLACK);
            }
        });
        menuBtn[2].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuBtn[2].setBackground(orangeShade);
                menuBtn[2].setForeground(whiteShade);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuBtn[2].setBackground(Color.WHITE);
                menuBtn[2].setForeground(Color.BLACK);
            }
        });
        menuBtn[3].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuBtn[3].setBackground(orangeShade);
                menuBtn[3].setForeground(whiteShade);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuBtn[3].setBackground(Color.WHITE);
                menuBtn[3].setForeground(Color.BLACK);
            }
        });
        menuBtn[4].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuBtn[4].setBackground(orangeShade);
                menuBtn[4].setForeground(whiteShade);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuBtn[4].setBackground(Color.WHITE);
                menuBtn[4].setForeground(Color.BLACK);
            }
        });

        // Add the content panel to the frame
        add(contentPanel);

        // Set frame properties
        setSize(600, 600);
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

    public static void main(String[] args) {
        new Dashboard("jessica_miller567");
    }
}
