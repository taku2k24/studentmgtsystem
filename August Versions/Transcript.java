import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Transcript extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane, jp_label, jp_btn, jp_outer;
    private JTable table;
    private JButton btnDisplay, btnClear;
    private JScrollPane sp;
    private JLabel jl_1, jl_2, jl_3, jl_4, jl_5;

    private String username;

    public Transcript(String username) {
        this.username = username;
        initializeUI();
    }

    public void initializeUI() {
        setTitle("Transcript");
        setLayout((LayoutManager) new BorderLayout());

        JButton btnDisplay = new JButton("View Transcript");
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
        btnClear.setFont(new Font("Arial", Font.BOLD, 11));
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

        jl_1 = new JLabel();
        jl_2 = new JLabel();
        jl_3 = new JLabel();
        jl_4 = new JLabel();
        jl_5 = new JLabel();

        jl_1.setText("Student ID: ");
        jl_2.setText("First Name: ");
        jl_3.setText("Last Name: ");
        jl_4.setText("Course Name: ");
        jl_5.setText("Course Code: ");

        jp_label = new JPanel(new GridLayout(2, 3));
        jp_label.add(jl_1);
        jp_label.add(jl_2);
        jp_label.add(jl_3);
        jp_label.add(jl_4);
        jp_label.add(jl_5);

        contentPane = new JPanel(new BorderLayout());
        contentPane.add(jp_label, BorderLayout.NORTH);
        contentPane.add(sp, BorderLayout.CENTER);

        jp_outer = new JPanel(new BorderLayout());
        jp_outer.add(contentPane, BorderLayout.CENTER);
        jp_outer.add(jp_btn, BorderLayout.SOUTH);

        getContentPane().add(jp_outer, BorderLayout.CENTER);
        
        setSize(800, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Display Button Handler
    private void Display() {
        try {
            DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
            Connection con = connectionManager.getConnection();

            String studentQuery = "SELECT student_id, first_name, last_name, courses.course_name, courses.course_code " +
                    "FROM student, courses " +
                    "WHERE student.course_id = courses.course_id AND username = ?;";

            PreparedStatement studentStmt = con.prepareStatement(studentQuery);
            studentStmt.setString(1, username);
            ResultSet studentRs = studentStmt.executeQuery();

            if (studentRs.next()) {
                String stdID = "Student ID: " + studentRs.getString(1);
                String stdF = "First Name: " + studentRs.getString(2);
                String stdL = "Last Name: " + studentRs.getString(3);
                String stdCN = "Course Name: " + studentRs.getString(4);
                String stdCC = "Course Code: " + studentRs.getString(5);

                jl_1.setText(stdID);
                jl_2.setText(stdF);
                jl_3.setText(stdL);
                jl_4.setText(stdCN);
                jl_5.setText(stdCC);
            }

            String marksQuery = "SELECT m.module_id, m.module_name, mk.coursework_marks, mk.exam_marks " + 
                    "FROM student s " + 
                    "INNER JOIN courses c ON s.course_id = c.course_id " +
                    "INNER JOIN marks mk ON s.student_id = mk.enrollment_id " + 
                    "INNER JOIN modules m ON mk.module_id = m.module_id " +
                    "WHERE s.username = ?;";


            PreparedStatement marksStmt = con.prepareStatement(marksQuery);
            marksStmt.setString(1, username);
            ResultSet rs2 = marksStmt.executeQuery();
            ResultSetMetaData rsmd = rs2.getMetaData();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the existing table data.

            // fetch column info
            int cols = rsmd.getColumnCount();
            Object[] colName = { "Module ID", "Module Name", "Coursework Marks", "Exam Marks" };
            model.setColumnIdentifiers(colName);

            String modId, modName, courseworkM, examM;
            while (rs2.next()) {
                modId = rs2.getString(1);
                modName = rs2.getString(2);
                courseworkM = rs2.getString(3);
                examM = rs2.getString(4);
                String[] row = { modId, modName, courseworkM, examM };
                model.addRow(row);
            }

            // Close the resources
            studentRs.close();
            studentStmt.close();
            rs2.close();
            marksStmt.close();
            connectionManager.closeConnection(); // Close the connection

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    // Clear Button Handler
    private void Clear() {
        table.setModel(new DefaultTableModel());
    }

    public static void main(String[] args){
        new Transcript("john_doe123");
    }
}