import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Timetable extends JFrame {

    private JPanel contentPane, jp_btn, jp_outer;
    private JTable table;
    private JButton btnDisplay, btnClear;
    private JScrollPane sp;

    public Timetable() {
        setTitle("TimeTable");
        getContentPane().setLayout((LayoutManager) new BorderLayout());

        btnDisplay = new JButton("View Timetable");
        btnDisplay.setFont(new Font("Arial", Font.BOLD, 11));

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

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setVisible(true);
    }

    // Display Button Handler
    private void Display() {
        // try {
            DatabaseConnectionManager dbManager = DatabaseConnectionManager.getInstance();
            Connection con = dbManager.getConnection();

            String query = "SELECT m.module_id, m.module_name, m.day, m.time_slot " +
            "FROM modules AS m " + 
            "JOIN student_modules AS sm ON m.module_id = sm.module_id AND m.module_name = sm.module_name " +
            "WHERE sm.username = ?; " ;

            try (PreparedStatement preparedStatement = con.prepareStatement(query);) {
                preparedStatement.setString(1, "jessica_miller567"); // Set the parameter for username

                // Execute the query and get the ResultSet
                ResultSet rs = preparedStatement.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                System.out.println(rsmd);

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

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to load timetable data.");}
            }

    // Clear Button Handler
    private void Clear() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table data
    }

    public static void main(String[] args) {
        new Timetable();
    }
}
