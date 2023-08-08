import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Enrollment {
    private JFrame frame;
    private JPanel statusEnrollment, moduleEnrollment;
    private JCheckBox[] moduleBoxes;
    private JButton moduleBtn;

    private JLabel courseLabel;
    private JLabel[] moduleLabels, credsLabels, courseModuleLabel;
    private List<String> selectedModules;
    private Connection connection;
    private String selectedCourse;
    private String username;

    public Enrollment(String username) {
        this.username = username;
        initializeDatabaseConnection();
        selectedModules = new ArrayList<>();
        initializeUI();
        loadEnrollmentStatus();
    }

    public void initializeUI() {
        frame = new JFrame("Enrollment");
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE); // Set the background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Initialize the moduleBoxes array
        moduleBoxes = new JCheckBox[6];
        
     // Initialize the courseModuleLabel array
        courseModuleLabel = new JLabel[6];
        // Status Enrollment Panel
        statusEnrollment = new JPanel(new GridBagLayout());
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Enrollment Status");
        titledBorder1.setTitleFont(new Font("Arial", Font.BOLD, 18)); // Change the font for the titled border
        statusEnrollment.setBorder(titledBorder1);
        statusEnrollment.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        statusEnrollment.add(new JLabel("Your course:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        courseLabel = new JLabel("N/A");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Change the font for the course label
        statusEnrollment.add(courseLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel modulePanel = new JPanel(new GridLayout(6, 2, 10, 5));
        modulePanel.setBackground(Color.WHITE); // Set background color for the module panel
        moduleLabels = new JLabel[6];
        credsLabels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            modulePanel.add(new JLabel("Module " + (i + 1) + " - "));
            moduleLabels[i] = new JLabel("N/A");
            moduleLabels[i].setFont(new Font("Arial", Font.PLAIN, 14)); // Change the font for module labels
            modulePanel.add(moduleLabels[i]);
            credsLabels[i] = new JLabel("N/A");
            credsLabels[i].setFont(new Font("Arial", Font.PLAIN, 14)); // Change the font for credits labels
            modulePanel.add(credsLabels[i]);
        }
        statusEnrollment.add(modulePanel, gbc);

        frame.add(statusEnrollment, gbc);

        // Module Enrollment Panel
        moduleEnrollment = new JPanel(new GridBagLayout());
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Module Enrollment");
        titledBorder3.setTitleFont(new Font("Arial", Font.BOLD, 18)); // Change the font for the titled border
        moduleEnrollment.setBorder(titledBorder3);
        moduleEnrollment.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 40, 10, 10); // Add padding for the module enrollment panel
        for (int i = 0; i < 6; i++) {
            gbc.anchor = GridBagConstraints.WEST;
            moduleBoxes[i] = new JCheckBox();
            moduleEnrollment.add(moduleBoxes[i], gbc);

            courseModuleLabel[i] = new JLabel("N/A - Placeholder");
            courseModuleLabel[i].setFont(new Font("Arial", Font.PLAIN, 14)); // Change the font for courseModuleLabel
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            moduleEnrollment.add(courseModuleLabel[i], gbc);

            gbc.gridx = 0;
            gbc.gridy++;
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10); // Add padding for the module enrollment panel
        JPanel buttonContainer = new JPanel(new FlowLayout());
        moduleBtn = new JButton("Enroll");
        moduleBtn.setFont(new Font("Arial", Font.BOLD, 16)); // Change the font for the enroll button
        buttonContainer.add(moduleBtn);
        moduleEnrollment.add(buttonContainer, gbc);

        frame.add(moduleEnrollment, gbc);

        // Resize checkboxes
        Dimension checkBoxSize = new Dimension(18, 18);
        for (JCheckBox moduleBox : moduleBoxes) {
            moduleBox.setPreferredSize(checkBoxSize);
            moduleBox.setMaximumSize(checkBoxSize);
            moduleBox.setMinimumSize(checkBoxSize);
        }
        
        moduleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < moduleBoxes.length; i++) {
                    if (moduleBoxes[i].isSelected()) {
                        String selectedModule = moduleLabels[i].getText();
                        if (!selectedModules.contains(selectedModule)) {
                            try {
                                int moduleId = getModuleId(selectedModule);
                                enrollModuleForStudent(username, moduleId, selectedModule,
                                        getCreditsForModule(selectedModule));
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null,
                                        "Failed to enroll in the module: " + selectedModule);
                            }
                        }
                    } else {
                        String selectedModule = moduleLabels[i].getText();
                        if (selectedModules.contains(selectedModule)) {
                            try {
                                int moduleId = getModuleId(selectedModule);
                                unenrollModuleForStudent(username, moduleId, selectedModule,
                                        getCreditsForModule(selectedModule));
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null,
                                        "Failed to unenroll from the module: " + selectedModule);
                            }
                        }
                    }
                }
            }
        });
        
        // Hover effect for module checkboxes
        for (JCheckBox moduleBox : moduleBoxes) {
            moduleBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moduleBox.setBackground(Color.ORANGE);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moduleBox.setBackground(Color.WHITE);
                }
            });
        }

        // Hover effect for the enroll button
        moduleBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                moduleBtn.setBackground(Color.ORANGE);
                moduleBtn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                moduleBtn.setBackground(Color.WHITE);
                moduleBtn.setForeground(Color.BLACK);
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeDatabaseConnection() {
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        connection = connectionManager.getConnection();
    }

    private void loadAllModules4Course(String courseName) {
        String query = "SELECT module_name FROM modules WHERE course_name = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            while (rs.next() && index <= 6) {
                moduleBoxes[index].setEnabled(true);
                courseModuleLabel[index].setText(rs.getString("module_name"));
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load modules for the selected course.");
        }
    }

    private void enrollModuleForStudent(String username, int moduleId, String moduleName, int credits) {
        String enrollModuleQuery = "INSERT INTO student_modules (username, module_id, module_name) VALUES (?, ?, ?)";
        try (PreparedStatement enrollModuleStmt = connection.prepareStatement(enrollModuleQuery)) {
            enrollModuleStmt.setString(1, username);
            enrollModuleStmt.setInt(2, moduleId);
            enrollModuleStmt.setString(3, moduleName);
            enrollModuleStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully enrolled in the module: " + moduleName);

            selectedModules.add(moduleName);
            updateEnrollmentStatusPanel();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to enroll in the module: " + moduleName);
        }
    }

    private void unenrollModuleForStudent(String username, int moduleId, String moduleName, int credits) {
        String unenrollModuleQuery = "DELETE FROM student_modules WHERE username = ? AND module_id = ?";
        try (PreparedStatement unenrollModuleStmt = connection.prepareStatement(unenrollModuleQuery)) {
            unenrollModuleStmt.setString(1, username);
            unenrollModuleStmt.setInt(2, moduleId);
            unenrollModuleStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully unenrolled from the module: " + moduleName);

            selectedModules.remove(moduleName);
            updateEnrollmentStatusPanel();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to unenroll from the module: " + moduleName);
        }
    }

    private List<String> loadEnrolledModules(String username, int courseId) {
        List<String> enrolledModules = new ArrayList<>();

        // Fetch enrolled modules from the database for the selected course and student
        String query = "SELECT module_name FROM modules WHERE module_name IN " +
                "(SELECT module_name FROM student_modules WHERE username = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
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

    private void loadEnrollmentStatus() {
        int courseId = getCourseIdForStudent(username);
        if (courseId != -1) {
            String courseName = getCourseName(courseId);
            if (courseName != null) {
                courseLabel.setText(courseName);
                enableModuleEnrollment(username, courseName);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to retrieve course information for the student.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to retrieve the course ID for the student.");
        }

        loadEnrolledModules(username, courseId);
        loadAllModules4Course(selectedCourse);
    }

    private void updateEnrollmentStatusPanel() {
        courseLabel.setText(getCourseName(getCourseIdForStudent(username)));

        for (int i = 0; i < moduleLabels.length; i++) {
            moduleLabels[i].setText("N/A");
            credsLabels[i].setText("N/A");
        }

        int moduleIndex = 0;
        for (String selectedModule : selectedModules) {
            moduleLabels[moduleIndex].setText(selectedModule);
            credsLabels[moduleIndex].setText(getCreditsForModule(selectedModule) + " credits");
            moduleIndex++;
        }
    }

    private int getModuleId(String moduleName) throws SQLException {
        String query = "SELECT module_id FROM modules WHERE module_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, moduleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("module_id");
            } else {
                throw new SQLException("Module not found: " + moduleName);
            }
        }
    }

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

    private void enableModuleEnrollment(String username, String courseName) {
        String query = "SELECT module_id, module_name, credits FROM modules WHERE course_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            int moduleIndex = 0;
            while (rs.next() && moduleIndex < 6) {
                int moduleId = rs.getInt("module_id");
                String moduleName = rs.getString("module_name");
                int credits = rs.getInt("credits");

                moduleLabels[moduleIndex].setText(moduleName);
                credsLabels[moduleIndex].setText(credits + " credits");
                moduleBoxes[moduleIndex].setEnabled(true);
                moduleBoxes[moduleIndex].setSelected(selectedModules.contains(moduleName));
                moduleIndex++;
            }
            for (int i = moduleIndex; i < 6; i++) {
                moduleLabels[i].setText("N/A");
                credsLabels[i].setText("N/A");
                moduleBoxes[i].setEnabled(false);
                moduleBoxes[i].setSelected(false);
            }
            moduleBtn.setEnabled(true);
            selectedCourse = courseName;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load available modules.");
        }

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
        return -1;
    }

    private int getCourseIdForStudent(String username) {
        String query = "SELECT course_id FROM student WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("course_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        // String username = "jessica_miller567"; // Replace with the actual username
        // Enrollment enrollment = new Enrollment(username);

        new Enrollment("jessica_miller567");
    }
}
