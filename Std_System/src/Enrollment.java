import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Enrollment {
    private JFrame frame;
    private JPanel statusEnrollment, moduleEnrollment;
    private JCheckBox[] moduleBoxes;
    private JButton moduleBtn;

    private JLabel courseLabel, creditLabel;
    private JLabel[] moduleLabels, credsLabels;

    private Connection connection;

    private String username;

    public Enrollment(String username) {
        this.username = username;
        initializeDatabaseConnection();
        // Call the method that initializes the UI and other components
        initializeUI();
    }

    public void initializeUI() {
        // UI initialization and setup
        frame = new JFrame("Enrollment");
        frame.setLayout(new GridLayout(2, 1, 0, 10)); // 

        // ENROLLMENT STATUS SECTION
        statusEnrollment = new JPanel(new GridLayout(2, 2)); // Change to 2 rows, 2 columns
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Enrollment Status");
        statusEnrollment.setBorder(titledBorder1);
        JPanel statusInnerDiv = new JPanel(new GridLayout(2, 2));
        statusInnerDiv.add(new JLabel("Your course:"));
        courseLabel = new JLabel("N/A");
        statusInnerDiv.add(courseLabel);
        statusInnerDiv.add(new JLabel("Credits:"));
        creditLabel = new JLabel("N/A");
        statusInnerDiv.add(creditLabel);
        statusEnrollment.add(statusInnerDiv);
        JPanel modulePanel = new JPanel(new GridLayout(6, 2));
        moduleLabels = new JLabel[6];
        credsLabels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            modulePanel.add(new JLabel("Module " + (i + 1) + " - "));
            moduleLabels[i] = new JLabel("N/A");
            modulePanel.add(moduleLabels[i]);
            credsLabels[i] = new JLabel("N/A");
            modulePanel.add(credsLabels[i]);
        }
        statusEnrollment.add(modulePanel);
        frame.add(statusEnrollment, BorderLayout.NORTH);

        // MAIN INNER DIV (Module Enrollment Section)
        moduleEnrollment = new JPanel(new GridLayout(6, 2)); // Change to 6 rows, 2 columns
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Module Enrollment");
        moduleEnrollment.setBorder(titledBorder3);
        moduleBoxes = new JCheckBox[6];
        for (int i = 0; i < 6; i++) {
            moduleBoxes[i] = new JCheckBox();
            moduleBoxes[i].setEnabled(false);
            moduleEnrollment.add(moduleBoxes[i]);
            moduleEnrollment.add(new JLabel(""));
        }
        moduleBtn = new JButton("Enroll");
        moduleEnrollment.add(moduleBtn);
        frame.add(moduleEnrollment, BorderLayout.CENTER);

        moduleBtn.addActionListener(e -> {
            String selectedCourse = courseLabel.getText();
            if (selectedCourse != null && !selectedCourse.equals("N/A")) {
                enrollModules(selectedCourse);
            }
        });


        loadEnrollmentStatus();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeDatabaseConnection() {
        // Get the database connection instance
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        connection = connectionManager.getConnection();
    }
   

    private void loadModules(String selectedCourse) {
        // Fetch modules related to the selectedCourse from the database and populate moduleLabels and credsLabels
        String query = "SELECT module_name FROM modules WHERE course_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, selectedCourse);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            while (rs.next()) {
                moduleLabels[index].setText(rs.getString("module_name"));
                credsLabels[index].setText("N/A");
                moduleBoxes[index].setEnabled(true); // Enable the checkbox for the module
                index++;
            }
            // Disable any remaining checkboxes
            for (int i = index; i < moduleBoxes.length; i++) {
                moduleLabels[i].setText("N/A");
                credsLabels[i].setText("N/A");
                moduleBoxes[i].setEnabled(false);
                moduleBoxes[i].setSelected(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load modules for the selected course.");
        }
    }
    private int getCourseId(String courseName) throws SQLException {
        String query = "SELECT course_id FROM courses WHERE course_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("course_id");
        }
    }
    
    private String getCourseName(int courseId) {
        String query = "SELECT course_name FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("course_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void loadEnrollmentStatus() {
        // Fetch the course ID for the logged-in student from the student table
        int courseId = getCourseIdForStudent(username);

        if (courseId != -1) {
            // Fetch the course name based on the course ID from the courses table
            String courseName = getCourseName(courseId);

            if (courseName != null) {
                courseLabel.setText(courseName);

                // Enable module enrollment for the student's course
                enableModuleEnrollment(username, courseName);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to retrieve course information for the student.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to retrieve the course ID for the student.");
        }
    }





    private int getModuleId(String moduleName) throws SQLException {
        String query = "SELECT module_id FROM modules WHERE module_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, moduleName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("module_id");
        }
    }

    private void enrollModules(String selectedCourse) {
        // Check which modules the student selected for enrollment
        for (int i = 0; i < moduleBoxes.length; i++) {
            if (moduleBoxes[i].isSelected()) {
                String selectedModule = moduleLabels[i].getText();

                // First, check if the student is already enrolled in the selected module
                String checkModuleEnrollmentQuery = "SELECT COUNT(*) FROM student_modules WHERE username = ? AND module_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(checkModuleEnrollmentQuery)) {
                    stmt.setString(1, username); // Use the class variable "username"
                    stmt.setInt(2, getModuleId(selectedModule));
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    int enrollmentCount = rs.getInt(1);

                    if (enrollmentCount > 0) {
                        JOptionPane.showMessageDialog(null, "You are already enrolled in the module: " + selectedModule);
                    } else {
                        // If not enrolled, insert the enrollment into the student_modules table
                        String enrollModuleQuery = "INSERT INTO student_modules (username, module_id) VALUES (?, ?)";
                        try (PreparedStatement enrollModuleStmt = connection.prepareStatement(enrollModuleQuery)) {
                            enrollModuleStmt.setString(1, username); // Use the class variable "username"
                            enrollModuleStmt.setInt(2, getModuleId(selectedModule));
                            enrollModuleStmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "You have successfully enrolled in the module: " + selectedModule);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to enroll in the module: " + selectedModule);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to check module enrollment status.");
                }
            }
        }
    }

    

    private void loadEnrolledModules(String username, int courseId) {
        // Fetch enrolled modules from the database for the selected course and student
        String query = "SELECT module_name, credits FROM modules WHERE course_id = ? AND module_id IN " +
                "(SELECT module_id FROM modules WHERE username = ? AND course_id = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setString(2, username);
            stmt.setInt(3, courseId);
            ResultSet rs = stmt.executeQuery();

            // Clear the module enrollment status first
            clearModuleEnrollmentStatus();

            // Loop through the result set and display enrolled modules in the UI
            int moduleIndex = 0;
            while (rs.next()) {
                String moduleName = rs.getString("module_name");
                int credits = rs.getInt("credits");

                // Display the module name and credits in the UI
                moduleLabels[moduleIndex].setText(moduleName);
                credsLabels[moduleIndex].setText(credits + " credits");
                moduleBoxes[moduleIndex].setSelected(true);
                moduleBoxes[moduleIndex].setEnabled(false);
                moduleIndex++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load enrolled modules.");
        }
    }

 // ... (previous code)

    private void enableModuleEnrollment(String username, String courseName) {
        // Fetch available modules for the selected course from the modules table
        String query = "SELECT module_id, module_name, credits FROM modules WHERE course_name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();

            // Clear the module enrollment status first
            clearModuleEnrollmentStatus();

            // Enable module checkboxes in the UI to allow student enrollment
            int moduleIndex = 0;
            while (rs.next()) {
                int moduleId = rs.getInt("module_id");
                String moduleName = rs.getString("module_name");
                int credits = rs.getInt("credits");

                // Enable the checkboxes and display module names in the UI
                moduleLabels[moduleIndex].setText(moduleName);
                credsLabels[moduleIndex].setText(credits + " credits");
                moduleBoxes[moduleIndex].setSelected(false);
                moduleBoxes[moduleIndex].setEnabled(true);

                // Add the ItemListener to the checkbox
                moduleBoxes[moduleIndex].addItemListener(new ModuleCheckBoxListener(username, moduleId, moduleName, credits));

                moduleIndex++;
            }

            // Enable the "Enroll" button for module enrollment
            moduleBtn.setEnabled(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load available modules.");
        }
    }

    // ... (rest of the code)
    private class ModuleCheckBoxListener implements ItemListener {
        private final String username;
        private final int moduleId;
        private final String moduleName;
        private final int credits;

        public ModuleCheckBoxListener(String username, int moduleId, String moduleName, int credits) {
            this.username = username;
            this.moduleId = moduleId;
            this.moduleName = moduleName;
            this.credits = credits;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                enrollModuleForStudent(username, moduleId, moduleName, credits);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                unenrollModuleForStudent(username, moduleId, moduleName, credits);
            }
        }
    }


    
    private void enrollModuleForStudent(String username, int moduleId, String moduleName, int credits) {
        // Insert the enrollment into the student_modules table
        String enrollModuleQuery = "INSERT INTO student_modules (username, module_id) VALUES (?, ?)";
        try (PreparedStatement enrollModuleStmt = connection.prepareStatement(enrollModuleQuery)) {
            enrollModuleStmt.setString(1, username);
            enrollModuleStmt.setInt(2, moduleId);
            enrollModuleStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully enrolled in the module: " + moduleName);
            updateEnrollmentStatus();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to enroll in the module: " + moduleName);
        }
    }

    private void unenrollModuleForStudent(String username, int moduleId, String moduleName, int credits) {
        // Delete the enrollment from the student_modules table
        String unenrollModuleQuery = "DELETE FROM student_modules WHERE username = ? AND module_id = ?";
        try (PreparedStatement unenrollModuleStmt = connection.prepareStatement(unenrollModuleQuery)) {
            unenrollModuleStmt.setString(1, username);
            unenrollModuleStmt.setInt(2, moduleId);
            unenrollModuleStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully unenrolled from the module: " + moduleName);
            updateEnrollmentStatus();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to unenroll from the module: " + moduleName);
        }
    }

    
    private int getCourseIdForStudent(String username) {
        // Fetch the course_id for the logged-in student from the student table
        String query = "SELECT course_id FROM student WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("course_id");
            } else {
                return -1; // Return -1 if the course_id is not found for the given username
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 if there's an error fetching the course_id
        }
    }

    private void clearModuleEnrollmentStatus() {
        // Set module labels, credits, checkboxes to "N/A" and unchecked
        for (int i = 0; i < 6; i++) {
            moduleLabels[i].setText("N/A");
            credsLabels[i].setText("N/A");
            moduleBoxes[i].setEnabled(false);
            moduleBoxes[i].setSelected(false);
        }
    }
    private void updateEnrollmentStatus() {
        // This method should be responsible for updating the enrollment status based on the student's modules
        // You can call this method after enrolling or unenrolling a module to refresh the enrollment status
        loadEnrolledModules(username, getCourseIdForStudent(username));
    }

    // Rest of the code for UI initialization and setup
    // ...
}
