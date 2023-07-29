import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;

public class Enrollment {
    private JFrame frame;
    private JPanel statusEnrollment, courseEnrollment, moduleEnrollment;
    private JCheckBox[] moduleBoxes;
    private JButton courseBtn, moduleBtn;
    private JComboBox<String> courseSelectionList;

    private JLabel courseLabel, creditLabel;
    private JLabel[] moduleLabels, credsLabels;

    private Connection connection;

    public Enrollment() {
        initializeDatabaseConnection();

        // UI initialization and setup
        frame = new JFrame("Enrollment");
        frame.setLayout(new GridLayout(2, 1, 0, 10));

        // ENROLLMENT STATUS SECTION
        statusEnrollment = new JPanel(new GridLayout(2, 1));
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Enrollment Status");
        statusEnrollment.setBorder(titledBorder1);
        JPanel statusInnerDiv = new JPanel(new GridLayout(3, 2));
        statusInnerDiv.add(new JLabel("Course:"));
        courseLabel = new JLabel("N/A");
        statusInnerDiv.add(courseLabel);
        statusInnerDiv.add(new JLabel("Credits:"));
        creditLabel = new JLabel("N/A");
        statusInnerDiv.add(creditLabel);
        statusInnerDiv.add(new JLabel("Modules:"));
        statusInnerDiv.add(new JLabel(""));
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
        frame.add(statusEnrollment);

        // MAIN INNER DIV
        JPanel mainInnerDiv = new JPanel(new GridLayout(2, 1, 0, 10));

        // COURSE ENROLLMENT SECTION
        courseEnrollment = new JPanel(new GridLayout(3, 4));
        TitledBorder titledBorder2 = BorderFactory.createTitledBorder("Course Enrollment");
        courseEnrollment.setBorder(titledBorder2);
        courseEnrollment.add(new JLabel("Select course: "));
        courseSelectionList = new JComboBox<>();
        courseEnrollment.add(courseSelectionList);
        courseBtn = new JButton("Enroll");
        courseEnrollment.add(courseBtn);
        mainInnerDiv.add(courseEnrollment);

        // MODULE ENROLLMENT SECTION
        moduleEnrollment = new JPanel(new GridLayout(6, 4));
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
        mainInnerDiv.add(moduleEnrollment);

        frame.add(mainInnerDiv);

        courseSelectionList.addActionListener(e -> {
            String selectedCourse = (String) courseSelectionList.getSelectedItem();
            if (selectedCourse != null) {
                loadModules(selectedCourse);
                loadEnrollmentStatus(selectedCourse); // Display enrollment status
            }
        });

        moduleBtn.addActionListener(e -> {
            String selectedCourse = (String) courseSelectionList.getSelectedItem();
            if (selectedCourse != null) {
                enrollModules(selectedCourse);
            }
        });

        loadCourses();

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

        // Check if the connection is successful
        if (connection != null) {
            // Display a message indicating successful connection (optional)
            JOptionPane.showMessageDialog(null, "Connected to the database successfully.");
        } else {
            // Display an error message if the connection fails
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    }

    private void loadCourses() {
        // Fetch courses from the database and populate the courseSelectionList
        // Database query to fetch courses...
    	// Fetch courses from the database and populate the courseSelectionList
        String query = "SELECT course_name FROM courses";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            courseSelectionList.removeAllItems();
            while (rs.next()) {
                courseSelectionList.addItem(rs.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load courses.");
        }
    }

    private void loadModules(String selectedCourse) {
        // Fetch modules related to the selectedCourse from the database and populate moduleLabels and credsLabels
        // Database query to fetch modules...
    	// Fetch modules related to the selectedCourse from the database and populate moduleLabels and credsLabels
        String query = "SELECT module_name, credits FROM modules WHERE course_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, selectedCourse);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            while (rs.next()) {
                moduleLabels[index].setText(rs.getString("module_name"));
                credsLabels[index].setText(rs.getString("credits"));
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
    
    private void loadEnrollmentStatus(String selectedCourse) {
        String username = "your-logged-in-username"; // Replace with the logged-in username

        // First, retrieve the course_id for the selected course from the courses table
        int courseId;
        try {
            courseId = getCourseId(selectedCourse);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve course ID.");
            return;
        }

        // Check if the student is enrolled in the selected course
        String checkCourseEnrollmentQuery = "SELECT COUNT(*) FROM student WHERE username = ? AND course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(checkCourseEnrollmentQuery)) {
            stmt.setString(1, username);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int enrollmentCount = rs.getInt(1);

            if (enrollmentCount > 0) {
                // If enrolled, display the enrollment status for the selected course and load enrolled modules
                courseLabel.setText(selectedCourse);
                creditLabel.setText(getCourseCredits(selectedCourse) + " credits");

                // Load and display enrolled modules
                loadEnrolledModules(username, courseId);
            } else {
                // If not enrolled, display a message indicating no enrollment for the selected course
                courseLabel.setText("N/A");
                creditLabel.setText("N/A");

                // Clear the module enrollment status since there are no enrolled modules
                clearModuleEnrollmentStatus();

                // Allow the student to enroll modules for the selected course
                enableModuleEnrollment(username, courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to check course enrollment status.");
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
        String username = "your-logged-in-username"; // Replace with the logged-in username

        // Check which modules the student selected for enrollment
        for (int i = 0; i < moduleBoxes.length; i++) {
            if (moduleBoxes[i].isSelected()) {
                String selectedModule = moduleLabels[i].getText();

                // First, check if the student is already enrolled in the selected module
                String checkModuleEnrollmentQuery = "SELECT COUNT(*) FROM student_modules WHERE username = ? AND module_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(checkModuleEnrollmentQuery)) {
                    stmt.setString(1, username);
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
                            enrollModuleStmt.setString(1, username);
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

    // Helper method to get the credits for a given course_name from the courses table
    private int getCourseCredits(String courseName) throws SQLException {
        String query = "SELECT credits FROM courses WHERE course_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("credits");
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

    private void enableModuleEnrollment(String username, int courseId) {
        // Fetch available modules for the selected course from the modules table
        String query = "SELECT module_name, credits FROM modules WHERE course_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            // Clear the module enrollment status first
            clearModuleEnrollmentStatus();

            // Enable module checkboxes in the UI to allow student enrollment
            int moduleIndex = 0;
            while (rs.next()) {
                String moduleName = rs.getString("module_name");
                int credits = rs.getInt("credits");

                // Enable the checkboxes and display module names and credits in the UI
                moduleLabels[moduleIndex].setText(moduleName);
                credsLabels[moduleIndex].setText(credits + " credits");
                moduleBoxes[moduleIndex].setSelected(false);
                moduleBoxes[moduleIndex].setEnabled(true);
                moduleIndex++;
            }

            // Enable the "Enroll" button for module enrollment
            moduleBtn.setEnabled(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load available modules.");
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

    // Rest of the code for UI initialization and setup
    // ...

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Enrollment());
    }
}
