import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    private JButton loginButton;
    private JComboBox<String> userTypeComboBox;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private DatabaseConnectionManager connectionManager;

    private Color orangeShade = new Color(255, 140, 0); // Shade of orange
    private Color greyShade = new Color(100, 100, 100); // Shade of grey
    private Color lightorangeShade = new Color(255, 237, 227); // For bg color

    public Login() {
        super("Student Management System");
        initializeUI();
        // Create an instance of DatabaseConnectionManager
        connectionManager = DatabaseConnectionManager.getInstance();
    }

    private void initializeUI() {
        // Create and arrange UI components
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(lightorangeShade);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(greyShade);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(loginLabel, constraints);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordField, constraints);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(categoryLabel, constraints);

        String[] userTypes = { "student", "staff", "admin" };
        userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(userTypeComboBox, constraints); // Add the userTypeComboBox to the panel

        loginButton = new JButton("Confirm");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 4;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton, constraints);


        add(panel, BorderLayout.CENTER);

        // Hover effect for the buttons
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(orangeShade);
                loginButton.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.WHITE);
                loginButton.setForeground(Color.BLACK);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Insert the student details into the database using the
        // DatabaseConnectionManager
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT userType FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userType = resultSet.getString("userType");
                if (userType.equals("student")) {
                    // If the user is a student, open the StudentDashboard
                    Dashboard dashboard = new Dashboard(username);
                    dashboard.setVisible(true);
                    dispose(); // Close the login frame
                } else {
                    // Handle login success for staff or admin
                    JOptionPane.showMessageDialog(this, "Login successful as " + userType);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Login();
    }
}
