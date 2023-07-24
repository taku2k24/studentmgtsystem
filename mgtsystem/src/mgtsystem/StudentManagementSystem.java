package mgtsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentManagementSystem extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> userTypeComboBox;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_ms";
    private static final String DB_USER = "ashley";
    private static final String DB_PASSWORD = "ash00ley";

    public StudentManagementSystem() {
        super("Student Management System");
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
    	// Create and arrange UI components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(passwordField, constraints);
        
        String[] userTypes = {"student", "staff", "admin"};
        userTypeComboBox = new JComboBox<>(userTypes);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(userTypeComboBox, constraints); // Add the userTypeComboBox to the panel

        loginButton = new JButton("Login");
        constraints.gridx = 0;
        constraints.gridy = 2;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton, constraints);

        registerButton = new JButton("Register");
        constraints.gridx = 1;
        constraints.gridy = 2;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        panel.add(registerButton, constraints);

        add(panel, BorderLayout.CENTER);
        

    }

    private void connectToDatabase() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle login functionality
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            String query = "SELECT userType FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userType = resultSet.getString("userType");
                if (userType.equals("student")) {
                    // If the user is a student, open the StudentDashboard
                    StudentDashboard dashboard = new StudentDashboard(username);
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


    // Method to handle registration functionality
 // Method to handle registration functionality
private void register() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    String userType = (String) userTypeComboBox.getSelectedItem(); // Get the selected user type from the JComboBox

    try {
        String query = "INSERT INTO users (username, password, userType) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, userType); // Use the selected user type

        int rowsInserted = statement.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Registration successful. You can now login.");

            // Open the StudentRegistrationFrame after successful registration
            StudentRegistrationFrame registrationFrame = new StudentRegistrationFrame(username);
            registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            registrationFrame.setSize(400, 400);
            registrationFrame.setVisible(true);

            // Close the current registration frame
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
        }
        statement.close();
    } catch (SQLException e) {
        // Handle duplicate username, etc.
        JOptionPane.showMessageDialog(this, "Registration failed. Please try a different username.");
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
        	@Override
        	public void run() {
            StudentManagementSystem sms = new StudentManagementSystem();
            sms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sms.setSize(400, 150);
            sms.setVisible(true);
        	} });
    }
}
