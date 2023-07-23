// Class that allows student to enroll into a course ONLY
// Runs separately for now

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;

public class CourseEnrollment extends JFrame {
    private JLabel enrollmentStatusLabel;
    private JButton editButton;
    private JPanel selectionPanel;
    private JLabel selectCourseLabel;
    private JComboBox<String> courseComboBox;
    private JButton cancelButton;
    private JButton doneButton;

    private DatabaseConnectionManager connectionManager;

    // Replace 'student_id' with the actual ID of the current student
    private int student_id = 62521; 

    public CourseEnrollment() {
        enrollmentStatusLabel = new JLabel();
        editButton = new JButton("Edit");
        selectionPanel = new JPanel();
        selectCourseLabel = new JLabel("Select course:");
        courseComboBox = new JComboBox<>();
        cancelButton = new JButton("Cancel");
        doneButton = new JButton("Done");

        // Create an instance of DatabaseConnectionManager
        connectionManager = DatabaseConnectionManager.getInstance();

        // Retrieve courses from the database and initialize the courseComboBox
        ArrayList<String> courses = getCoursesFromDatabase();
        for (String course : courses) {
            courseComboBox.addItem(course);
        }

        // Set up the main window
        setSize(600, 1200);
        setTitle("Course Enrollment");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the preferred size for the content pane
        Dimension preferredSize = new Dimension(600, 600);
        getContentPane().setPreferredSize(preferredSize);
        pack(); // Adjust the frame size to fit its contents
        setLocationRelativeTo(null);

        // Set up the enrollment status panel
        JPanel statusPanel = new JPanel();
        statusPanel.add(new JLabel("Enrollment Status: "));
        statusPanel.add(enrollmentStatusLabel);
        statusPanel.add(editButton);

        // Set up the course selection panel
        selectionPanel.setLayout(new FlowLayout());
        selectionPanel.add(selectCourseLabel);
        selectionPanel.add(courseComboBox);
        selectionPanel.add(cancelButton);
        selectionPanel.add(doneButton);
        selectionPanel.setVisible(false);

        // Add action listener to the editButton
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionPanel.setVisible(true);
            }
        });

        // Add action listener to the cancelButton
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionPanel.setVisible(false);
            }
        });

        // Add action listener to the doneButton
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                int choice = JOptionPane.showConfirmDialog(
                        CourseEnrollment.this,
                        "Are you sure you want to enroll in " + selectedCourse + "?",
                        "Confirm Enrollment",
                        JOptionPane.YES_NO_OPTION
                );
                if (choice == JOptionPane.YES_OPTION) {
                    enrollmentStatusLabel.setText(selectedCourse);
                    updateEnrollmentStatus(selectedCourse);
                }
                selectionPanel.setVisible(false);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connectionManager.closeConnection();
            }
        });

        // Add components to the main window
        add(statusPanel, BorderLayout.NORTH);
        add(selectionPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private ArrayList<String> getCoursesFromDatabase() {
        ArrayList<String> courses = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT course_Name FROM courses";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        courses.add(resultSet.getString("course_Name"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private void updateEnrollmentStatus(String courseName) {
        // Get the selected course ID from the database based on the course name
        int courseId = 0;
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT course_ID FROM courses WHERE course_Name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, courseName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        courseId = resultSet.getInt("course_ID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Update the enrollment status for the current student
        try (Connection connection = connectionManager.getConnection()) {
            String updateQuery = "UPDATE students SET course_ID = ? WHERE student_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, courseId);
                // Replace 'your_student_id' with the actual ID of the current student
                preparedStatement.setInt(2, student_id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CourseEnrollment().setVisible(true);
            }
        });
    }
}