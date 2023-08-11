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

    private Color orangeShade = new Color(255, 140, 0); 
    private Color greyShade = new Color(100, 100, 100); 
    private Color lightorangeShade = new Color(255, 237, 227); // For bg color

    public Enrollment(String username) {
        this.username = username;
        initializeDatabaseConnection();
        selectedModules = new ArrayList<>();
        initializeUI();
        loadEnrollmentStatus(username);
        setCheckboxes();
    }

    public void initializeUI() {
        frame = new JFrame("Enrollment");
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(lightorangeShade); // Set the background color

        //ICON
        ImageIcon img = new ImageIcon("orange_icon.png");
        frame.setIconImage(img.getImage());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Initialize the moduleBoxes array
        moduleBoxes = new JCheckBox[6];

        // Initialize the courseModuleLabel array
        courseModuleLabel = new JLabel[6];
        // Status Enrollment Panel
        statusEnrollment = new JPanel(new GridBagLayout());
        statusEnrollment.setBackground(lightorangeShade);
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Enrollment Status");
        titledBorder1.setTitleColor(orangeShade);
        titledBorder1.setTitleFont(new Font("Arial", Font.BOLD, 18)); // Change the font for the titled border
        statusEnrollment.setBorder(titledBorder1);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel c = new JLabel("Your course:");
        c.setFont(new Font("Arial", Font.PLAIN, 14));
        statusEnrollment.add(c, gbc);

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
        modulePanel.setBackground(lightorangeShade);
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
        moduleEnrollment.setBackground(lightorangeShade);
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Module Enrollment");
        titledBorder3.setTitleColor(orangeShade);
        titledBorder3.setTitleFont(new Font("Arial", Font.BOLD, 18)); // Change the font for the titled border
        moduleEnrollment.setBorder(titledBorder3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 40, 10, 10); // Add padding for the module enrollment panel
        for (int i = 0; i < 6; i++) {
            gbc.anchor = GridBagConstraints.WEST;
            moduleBoxes[i] = new JCheckBox();
            moduleBoxes[i].setEnabled(false);
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
        moduleBtn.setBackground(Color.WHITE);
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
                        String selectedModule = courseModuleLabel[i].getText();
                        enrollModule(username, selectedModule);
                    } else if(!(courseModuleLabel[i].getText().equals("N/A - Placeholder"))){
                        String unSelectedModule = courseModuleLabel[i].getText();
                        unenrollModule(username, unSelectedModule);
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

    private void enrollModule(String username, String module_name) {
        // Check if already enrolled first
        String isEnrolled = "SELECT module_id FROM " +
                "student_modules " +
                "WHERE username = ? AND module_name = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(isEnrolled)) {
            stmt.setString(1, username);
            stmt.setString(2, module_name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // Module already enrolled
                JOptionPane.showMessageDialog(null, "Already enrolled in " + module_name);
            } else {
                // Enroll the student in that course
                String retrieveModuleID = "SELECT module_id FROM modules WHERE module_name = ? ;";
                try (PreparedStatement retrieveStmt = connection.prepareStatement(retrieveModuleID)) {
                    retrieveStmt.setString(1, module_name);
                    ResultSet rs2 = retrieveStmt.executeQuery();
                    rs2.next();
                    int module_id = rs2.getInt("module_id");
                    String enrollModuleQuery = "INSERT INTO student_modules (username, module_id, module_name) VALUES (?, ?, ?)";
                    try (PreparedStatement enrollModuleStmt = connection.prepareStatement(enrollModuleQuery)) {
                        enrollModuleStmt.setString(1, username);
                        enrollModuleStmt.setInt(2, module_id);
                        enrollModuleStmt.setString(3, module_name);
                        enrollModuleStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null,
                                "You have successfully enrolled in the module: " + module_name);
                        clearEnrollmentStatus();
                        loadEnrollmentStatus(username);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Enrollment Failure For " + module_name);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failure to retrieve module_id for " + module_name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Enrollment Failure For " + module_name);
        }
    }

    private void unenrollModule(String username, String module_name) {
        // Check if already enrolled first
        String isEnrolled = "SELECT module_id FROM " +
                "student_modules " +
                "WHERE username = ? AND module_name = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(isEnrolled)) {
            stmt.setString(1, username);
            stmt.setString(2, module_name);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) { // Module not enrolled yet
                JOptionPane.showMessageDialog(null, "Not enrolled yet in " + module_name);
            } else {
                // Enroll the student in that course
                String retrieveModuleID = "SELECT module_id FROM modules WHERE module_name = ? ;";
                try (PreparedStatement retrieveStmt = connection.prepareStatement(retrieveModuleID)) {
                    retrieveStmt.setString(1, module_name);
                    ResultSet rs2 = retrieveStmt.executeQuery();
                    rs2.next();
                    String unenrollModuleQuery = "DELETE FROM student_modules WHERE username = ? AND module_name = ?";
                    try (PreparedStatement unenrollModuleStmt = connection.prepareStatement(unenrollModuleQuery)) {
                        unenrollModuleStmt.setString(1, username);
                        unenrollModuleStmt.setString(2, module_name);
                        unenrollModuleStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null,
                                "You have successfully unenrolled from the module: " + module_name);

                        clearEnrollmentStatus();
                        loadEnrollmentStatus(username);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to unenroll from the module: " + module_name);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failure to retrieve module_id for " + module_name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unenrollment Failure For " + module_name);
        }
    }

    private void initializeDatabaseConnection() {
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        connection = connectionManager.getConnection();
    }

    private void loadEnrollmentStatus(String username) { // IN USE
        String fetchCourseQuery = "SELECT c.course_id, c.course_name " +
                "FROM student s " +
                "JOIN courses c ON s.course_id = c.course_id " +
                "WHERE s.username = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(fetchCourseQuery)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String courseName = rs.getString("course_name");
            courseLabel.setText(courseName);
            loadEnrolledModules(username);
            loadAllModules4Course(courseName); // Loading all modules available in the enrollment panel
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve course data");
        }
    }

    private void clearEnrollmentStatus() {
        for (int i = 0; i < 6; i++) {
            moduleLabels[i].setText("N/A");
            credsLabels[i].setText("N/A");
        }
    }

    private void loadEnrolledModules(String username) { // IN USE
        String fetchEnrolledQuery = "SELECT sm.module_id, m.module_name, m.credits " +
                "FROM student_modules sm " +
                "JOIN modules m ON sm.module_id = m.module_id " +
                "WHERE sm.username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(fetchEnrolledQuery)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            int moduleIndex = 0;
            while (rs.next() && moduleIndex < 6) {
                int moduleId = rs.getInt("module_id");
                String moduleName = rs.getString("module_name");
                int credits = rs.getInt("credits");

                moduleLabels[moduleIndex].setText(moduleName);
                credsLabels[moduleIndex].setText(credits + " credits");
                moduleIndex++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load available modules.");
        }

    }

    private void setCheckboxes() { // IN USE
        int i, j;
        i = 0;
        while (moduleLabels[i].getText() != "N/A" && i <= 5) {
            j = 0;
            while (courseModuleLabel[j].getText() != "N/A - Placeholder" && j <= 5) {
                if (moduleLabels[i].getText().equals(courseModuleLabel[j].getText())) {
                    moduleBoxes[j].setSelected(true);
                }
                j++;
            }
            i++;
        }
    }

    private void loadAllModules4Course(String courseName) { // IN USE
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


    public static void main(String[] args) {
        new Enrollment("jessica_miller567");
    }
}
