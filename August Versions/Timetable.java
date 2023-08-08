import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Timetable extends JFrame {

    private JPanel contentPane, jp_btn, jp_outer;
    private JTable table;
    private JButton btnDisplay, btnClear;
    private JScrollPane sp;

    private String username;

    public Timetable(String username) {
        this.username = username;
        initializeUI();
    }

    public void initializeUI() {
        setTitle("TimeTable");
        getContentPane().setLayout(new BorderLayout());

        btnDisplay = new JButton("View Timetable");
        btnDisplay.setFont(new Font("Arial", Font.BOLD, 11));
        btnDisplay.setBackground(Color.ORANGE);
        btnDisplay.setForeground(Color.WHITE);
        btnDisplay.setFocusPainted(false); // Remove the focus border

        table = new JTable();
        table.setBorder(null);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Display();
            }
        });

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Clear();
            }
        });

        btnClear.setFont(new Font("Arial", Font.BOLD, 11));
        btnClear.setBackground(Color.GRAY);
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false); // Remove the focus border

        jp_btn = new JPanel(new FlowLayout());
        jp_btn.add(btnDisplay);
        jp_btn.add(btnClear);

        // adding it to JScrollPane
        sp = new JScrollPane(table);

        contentPane = new JPanel(new GridLayout(1, 1));
        contentPane.add(sp);

        jp_outer = new JPanel(new BorderLayout());
        jp_outer.add(contentPane, BorderLayout.CENTER);
        jp_outer.add(jp_btn, BorderLayout.SOUTH);

        getContentPane().add(jp_outer, BorderLayout.CENTER);

        // Hover effect for buttons
        btnDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisplay.setBackground(Color.WHITE);
                btnDisplay.setForeground(Color.ORANGE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisplay.setBackground(Color.ORANGE);
                btnDisplay.setForeground(Color.WHITE);
            }
        });

        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(Color.WHITE);
                btnClear.setForeground(Color.GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(Color.GRAY);
                btnClear.setForeground(Color.WHITE);
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setVisible(true);
    }

 // Display Button Handler
    private void Display() {
        try {
            DatabaseConnectionManager dbManager = DatabaseConnectionManager.getInstance();
            Connection con = dbManager.getConnection();

            // Query to get the course name for the logged-in user based on their course_id
            String courseNameQuery = "SELECT course_name FROM courses WHERE course_id IN (SELECT course_id FROM student WHERE username = ?)";

            try (PreparedStatement courseNameStatement = con.prepareStatement(courseNameQuery);) {
                courseNameStatement.setString(1, username);

                ResultSet courseNameResult = courseNameStatement.executeQuery();
                if (courseNameResult.next()) {
                    String courseName = courseNameResult.getString("course_name");

                    // Now that we have the course name, we can use it in the main query
                    String query = "SELECT m.module_id, m.module_name, m.day, m.time_slot " +
                            "FROM modules AS m " +
                            "JOIN courses AS c ON m.course_name = c.course_name " +
                            "JOIN student AS s ON c.course_id = s.course_id " +
                            "WHERE s.username = ? AND c.course_name = ?";

                    try (PreparedStatement preparedStatement = con.prepareStatement(query);) {
                        preparedStatement.setString(1, username); // Use the class variable for username
                        preparedStatement.setString(2, courseName); // Use the retrieved course name

                        // Rest of the code remains the same...

                        // Execute the query and get the ResultSet
                        ResultSet rs = preparedStatement.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();

                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0); // Clear the existing table data

                        int cols = rsmd.getColumnCount();
                        Object[] colName = { "Module ID", "Module Name", "Day", "Time" };
                        model.setColumnIdentifiers(colName);

                        String modId, modName, modDay, modTime;
                        while (rs.next()) {
                            modId = rs.getString(1).toString();
                            modName = rs.getString(2);
                            modDay = rs.getString(3);
                            modTime = (rs.getString(4).toString()).substring(0, 5);
                            String[] row = { modId, modName, modDay, modTime };
                            model.addRow(row);
                        }

                        // Close the PreparedStatement and the connection after use
                        preparedStatement.close();
                        dbManager.closeConnection();

                        // Style the table
                        table.setRowHeight(30);
                        table.setFont(new Font("Arial", Font.PLAIN, 14));
                        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
                        table.getTableHeader().setBackground(Color.ORANGE);
                        table.getTableHeader().setForeground(Color.WHITE);
                        table.setSelectionBackground(Color.LIGHT_GRAY);
                        table.setSelectionForeground(Color.BLACK);
                        table.setGridColor(Color.GRAY);
                        table.setShowVerticalLines(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Course name not found for the logged-in user.");
                }

                // Close the ResultSet and the PreparedStatement for course name query
                courseNameResult.close();
                courseNameStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to retrieve course name for the logged-in user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    }


 // Clear Button Handler
    private void Clear() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table data
    }

    public static void main(String[] args) {
        new Timetable("jessica_miller567");
    }
}
