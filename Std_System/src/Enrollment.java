import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

public class Enrollment {
    private JFrame frame;
    private JPanel statusEnrollment, moduleEnrollment;
    private JCheckBox[] moduleBoxes;
    private JButton moduleBtn;

    private JLabel courseLabel;
    private JLabel[] moduleLabels, credsLabels;
    private List<String> selectedModules;
    private Connection connection;
    private String selectedCourse; // Declare selectedCourse as a member variable

    private String username;

    public Enrollment(String username) {
        this.username = username;
        initializeDatabaseConnection();
        selectedModules = new ArrayList<>();
        // Call the method that initializes the UI and other components
        initializeUI();
    }

    public void initializeUI() {
        // UI initialization and setup
        frame = new JFrame("Enrollment");
        frame.setLayout(new GridLayout(2, 1, 0, 10));

        // ENROLLMENT STATUS SECTION
        statusEnrollment = new JPanel(new GridLayout(2, 2));
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Enrollment Status");
        statusEnrollment.setBorder(titledBorder1);
        JPanel statusInnerDiv = new JPanel(new GridLayout(2, 2));
        statusInnerDiv.add(new JLabel("Your course:"));
        courseLabel = new JLabel("N/A");
        statusInnerDiv.add(courseLabel);
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
        moduleEnrollment = new JPanel(new GridLayout(6, 2));
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Module Enrollment");
        moduleEnrollment.setBorder(titledBorder3);
        moduleBoxes = new JCheckBox[6];
        for (int i = 0; i < 6; i++) {
            moduleBoxes[i] = new JCheckBox();
            moduleEnrollment.add(moduleBoxes[i]);
            moduleEnrollment.add(new JLabel(""));
        }
        moduleBtn = new JButton("Enroll");
        moduleEnrollment.add(moduleBtn);
        frame.add(moduleEnrollment, BorderLayout.CENTER);

        // Load enrollment status first to display enrolled modules
        loadEnrollmentStatus();

        // Call loadModules to populate the checkboxes with module names for the initial course
        enableModuleEnrollment(username, courseLabel.getText());

        moduleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < moduleBoxes.length; i++) {
                    if (moduleBoxes[i].isSelected()) {
                        String selectedModule = moduleLabels[i].getText();
                        if (!selectedModules.contains(selectedModule)) {
                            try {
                                int moduleId = getModuleId(selectedModule);
                                enrollModuleForStudent(username, moduleId, selectedModule, getCreditsForModule(selectedModule));
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to enroll in the module: " + selectedModule);
                            }
                        }
                    } else {
                        String selectedModule = moduleLabels[i].getText();
                        if (selectedModules.contains(selectedModule)) {
                            try {
                                int moduleId = getModuleId(selectedModule);
                                unenrollModuleForStudent(username, moduleId, selectedModule, getCreditsForModule(selectedModule));
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to unenroll from the module: " + selectedModule);
                            }
                        }
                    }
                }
            }
        });
        // ... Rest of the code

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

    // ... (previous code)

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
    
    private void enrollModuleForStudent(String username, int moduleId, String moduleName, int credits) {
        // Insert the enrollment into the student_modules table
        String enrollModuleQuery = "INSERT INTO student_modules (username, module_id, module_name) VALUES (?, ?, ?)";
        try (PreparedStatement enrollModuleStmt = connection.prepareStatement(enrollModuleQuery)) {
            enrollModuleStmt.setString(1, username);
            enrollModuleStmt.setInt(2, moduleId);
            enrollModuleStmt.setString(3, moduleName);
            enrollModuleStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully enrolled in the module: " + moduleName);

            // Fetch the enrolled modules for the student after enrollment
            List<String> enrolledModules = loadEnrolledModules(username, getCourseId(selectedCourse));
            updateEnrollmentStatusPanel(enrolledModules); // Pass the enrolledModules list as an argument
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to enroll in the module: " + moduleName);
        }
    }


    private List<String> loadEnrolledModules(String username, int courseId) {
        List<String> enrolledModules = new ArrayList<>();

        // Fetch enrolled modules from the database for the selected course and student
        String query = "SELECT module_name FROM modules WHERE course_id = ? AND module_id IN " +
                "(SELECT module_id FROM student_modules WHERE username = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and add enrolled modules to the list
            while (rs.next()) {
                enrolledModules.add(rs.getString("module_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load enrolled modules.");
        }

        return enrolledModules;
    }

    
    private void unenrollModuleForStudent(String username, int moduleId, String moduleName, int credits) {
        // Delete the enrollment from the student_modules table
        String unenrollModuleQuery = "DELETE FROM student_modules WHERE username = ? AND module_id = ?";
        try (PreparedStatement unenrollModuleStmt = connection.prepareStatement(unenrollModuleQuery)) {
            unenrollModuleStmt.setString(1, username);
            unenrollModuleStmt.setInt(2, moduleId);
            unenrollModuleStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully unenrolled from the module: " + moduleName);

            // Update the selectedModules list by removing the unenrolled module
            selectedModules.remove(moduleName);

            // Update the enrollment status panel with the updated list of enrolled modules
            updateEnrollmentStatusPanel(selectedModules);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to unenroll from the module: " + moduleName);
        }
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

    private void updateEnrollmentStatus() {
        // Fetch the enrolled modules for the student from the database
        String query = "SELECT module_name FROM student_modules WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            // Clear the selected modules list first
            selectedModules.clear();

            // Loop through the result set and add enrolled modules to the list
            while (rs.next()) {
                selectedModules.add(rs.getString("module_name"));
            }

            updateEnrollmentStatusPanel(selectedModules); // Pass the enrolledModules list as an argument

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load enrolled modules.");
        }
    }

    private void updateEnrollmentStatusPanel(List<String> selectedModules) {
        // Update the enrollment status panel with the enrolled modules from the selectedModules list
        courseLabel.setText(getCourseName(getCourseIdForStudent(username)));

        // Clear the module enrollment status first
        clearModuleEnrollmentStatus();

        int moduleIndex = 0;
        for (String selectedModule : selectedModules) {
            moduleLabels[moduleIndex].setText(selectedModule);
            credsLabels[moduleIndex].setText(getCreditsForModule(selectedModule) + " credits");
            moduleIndex++;
        }
    }

    private void clearModuleEnrollmentStatus() {
        // Clear the module enrollment status panel
        for (int i = 0; i < moduleLabels.length; i++) {
            moduleLabels[i].setText("N/A");
            credsLabels[i].setText("N/A");
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
    
    private int getModuleId(String moduleName) throws SQLException {
        String query = "SELECT module_id FROM modules WHERE module_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, moduleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("module_id");
            } else {
                // Handle the case when the module name is not found in the database
                throw new SQLException("Module not found: " + moduleName);
            }
        }
    }


    // Add a method to get the credits for a module
    private int getCreditsForModule(String moduleName) {
        String query = "SELECT credits FROM modules WHERE module_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, moduleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("credits");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Define the ModuleCheckBoxListener class inside the Enrollment class
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
            selectedCourse = courseName;
            // Call loadModules to populate the checkboxes with module names
            loadModules(selectedCourse); // Pass the selected course as an argument

            // ...
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load available modules.");
        }

        // If no modules were found for the course, display a message
        if (moduleLabels[0].getText().equals("N/A")) {
            JOptionPane.showMessageDialog(null, "No modules available for the selected course.");
        }
    }

    private int getCourseId(String courseName) {
        String query = "SELECT course_id FROM courses WHERE course_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("course_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the course_id is not found or an error occurs
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

 

    // ... (previous code)

    public static void main(String[] args) {
        // Sample usage to test the Enrollment class
        String username = "JohnDoe"; // Replace with the actual username
        Enrollment enrollment = new Enrollment(username);
    }
}
