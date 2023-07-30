import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.*;

import db.ViewTimeTable;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.event.*;

public class Timetable extends JFrame {

    private JPanel contentPane, jp_btn, jp_outer;
    private JTable table;
    private JButton btnDisplay, btnClear;
    private JScrollPane sp;

    public Timetable() {
        setTitle("TimeTable");
        getContentPane().setLayout((LayoutManager) new BorderLayout());

        JButton btnDisplay = new JButton("View Timetable");
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
        try {
            DatabaseConnectionManager dbManager = DatabaseConnectionManager.getInstance();
            Connection con = dbManager.getConnection();

            /*
             * SELECT modules.module_name, modules.day, modules.time_slot
             * FROM modules
             * RIGHT JOIN student ON student.course_name = modules.course_name
             * WHERE student.username
             * IN(SELECT username FROM student WHERE username = "john_doe123")
             * ORDER BY modules.day;
             */

            String query = "select modules.module_id, modules.module_name, modules.day, modules.time_slot from modules RIGHT JOIN student ON student.course_name = modules.course_name WHERE student.username IN(SELECT username FROM student WHERE username = \"john_doe123\") ORDER BY modules.day, modules.time_slot;";
            java.sql.Statement st = con.createStatement();

            /*
             * PREPARED STATEMENT (EDIT TO ACCOMMODATE LOGGED IN USER)
             * java.sql.Statement st = con.createStatement();
             * PreparedStatement st = con.prepareStatement(query);
             * statement.setString(1, username);
             */

            // EXECUTE QUERY
            ResultSet rs = ((java.sql.Statement) st).executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // fetch column info
            int cols = rsmd.getColumnCount();
            Object[] colName = { "Module ID", "Module Name", "Day", "Time" };
            for (int i = 0; i < cols; i++) {
                model.setColumnIdentifiers(colName);

                String modId, modName, modDay, modTime;
                while (rs.next()) {
                    modId = rs.getString(1);
                    modName = rs.getString(2);
                    modDay = rs.getString(3);
                    modTime = (rs.getString(4)).substring(0, 5);
                    String[] row = { modId, modName, modDay, modTime };
                    model.addRow(row);
                }
                dbManager.closeConnection();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Clear Button Handler
    private void Clear() {
        table.setModel(new DefaultTableModel());
    }

    public static void main(String[] args) {
        new Timetable();
    }
}
