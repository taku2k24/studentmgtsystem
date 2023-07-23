// Class that allows student to view his/her profile and update name ONLY
// Runs separately for now

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentProfile extends JFrame {
    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel courseEnrolledLabel;
    private JLabel paymentStatusLabel;
    private JButton editProfileButton;

    private DatabaseConnectionManager connectionManager;

    // Replace 'student_id' with the actual ID of the current student
    private int student_id = 62521; 

    public StudentProfile() {
        nameLabel = new JLabel();
        idLabel = new JLabel();
        courseEnrolledLabel = new JLabel();
        paymentStatusLabel = new JLabel();
        editProfileButton = new JButton("Edit Profile");

        // Create an instance of DatabaseConnectionManager
        connectionManager = DatabaseConnectionManager.getInstance();

        // Set up the main window
        setTitle("Student Profile");
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the preferred size for the content pane
        Dimension preferredSize = new Dimension(600, 600);
        getContentPane().setPreferredSize(preferredSize);
        pack(); // Adjust the frame size to fit its contents
        setLocationRelativeTo(null);

        // Set up the profile information panel
        add(new JLabel("Name: "));
        add(nameLabel);
        add(new JLabel("Student ID: "));
        add(idLabel);
        add(new JLabel("Course Enrolled: "));
        add(courseEnrolledLabel);
        add(new JLabel("Payment Status: "));
        add(paymentStatusLabel);
        add(new JLabel()); // Empty label to fill the grid
        add(editProfileButton);

        // Add action listener to the editProfileButton
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog to edit the profile details
                showEditProfileDialog();
            }
        });

        // Load and display the profile information for the current student
        loadProfileInformation(student_id); // Replace 'student_id' with the actual ID of the current student
        pack();
        setLocationRelativeTo(null);
    }

    private void loadProfileInformation(int studentId) {
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT * FROM students " +
                           "LEFT JOIN courses ON students.course_id = courses.course_ID " +
                           "WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String studentName = resultSet.getString("student_Name");
                        int studentIdValue = resultSet.getInt("student_id");
                        String courseEnrolled = resultSet.getString("course_Name");
                        String paymentStatus = resultSet.getString("payment_status");

                        nameLabel.setText(studentName);
                        idLabel.setText(String.valueOf(studentIdValue));
                        courseEnrolledLabel.setText(courseEnrolled != null ? courseEnrolled : "Not enrolled yet.");
                        paymentStatusLabel.setText(paymentStatus);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateProfileInformation(String studentName, int studentId) {
        try (Connection connection = connectionManager.getConnection()) {
            String updateQuery = "UPDATE students SET student_Name = ? WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, studentName);
                preparedStatement.setInt(2, studentId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showEditProfileDialog() {
        String studentName = JOptionPane.showInputDialog(this, "Enter your name:", nameLabel.getText());
        if (studentName != null && !studentName.isEmpty()) {
            nameLabel.setText(studentName);
            // Replace 'student_id' with the actual ID of the current student
            updateProfileInformation(studentName, student_id);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentProfile profileInterface = new StudentProfile();
                profileInterface.setVisible(true);
            }
        });
    }
}
