import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    // Existing variables...


    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> userTypeComboBox;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private DatabaseConnectionManager connectionManager;

    public Login() {
        super("Student Management System");
        initializeUI();
        // Create an instance of DatabaseConnectionManager
        connectionManager = DatabaseConnectionManager.getInstance();
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

        String[] userTypes = { "student", "staff", "admin" };
        userTypeComboBox = new JComboBox<>(userTypes);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(userTypeComboBox, constraints); // Add the userTypeComboBox to the panel

        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
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

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeComboBox.getSelectedItem();

        // Insert the user details into the database using the DatabaseConnectionManager
        try (Connection connection = connectionManager.getConnection()) {
            try {
                String query = "INSERT INTO users (username, password, userType) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, userType); // Use the selected user type

                statement.executeUpdate();

                statement.close();
            } catch (SQLException e) {
                // Handle duplicate username, etc.
                JOptionPane.showMessageDialog(this, "Registration failed. Please try a different username.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rest of the code remains the same...

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new Runnable() {
    // @Override
    // public void run() {
    // StudentManagementSystem frame = new StudentManagementSystem();
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.pack();
    // frame.setLocationRelativeTo(null);
    // frame.setVisible(true);
    // }
    // });
    // }
}
