import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.*;

public class Enrollment {
    private JFrame frame;
    private JPanel statusEnrollment, courseEnrollment, moduleEnrollment;
    private JCheckBox box1, box2, box3, box4, box5, box6;
    private JButton courseBtn, moduleBtn;
    private JComboBox<String> courseSelectionList;

    private JLabel courseLabel, creditLabel;
    private JLabel moduleLabel1, moduleLabel2, moduleLabel3, moduleLabel4, moduleLabel5, moduleLabel6;
    private JLabel credsLabel1, credsLabel2, credsLabel3, credsLabel4, credsLabel5, credsLabel6;

    public Enrollment(){
        //PLACEHOLDERS
        courseLabel = new JLabel("N/A");
        creditLabel = new JLabel("N/A");
        moduleLabel1 = new JLabel("N/A");
        moduleLabel2 = new JLabel("N/A");
        moduleLabel3 = new JLabel("N/A");
        moduleLabel4 = new JLabel("N/A");
        moduleLabel5 = new JLabel("N/A");
        moduleLabel6 = new JLabel("N/A");
        credsLabel1 = new JLabel("N/A");
        credsLabel2 = new JLabel("N/A");
        credsLabel3 = new JLabel("N/A");
        credsLabel4 = new JLabel("N/A");
        credsLabel5 = new JLabel("N/A");
        credsLabel6 = new JLabel("N/A");

        frame = new JFrame("Enrollment");
        frame.setLayout(new GridLayout(2, 1, 0, 10));

        //ENROLLMENT STATUS SECTION
        statusEnrollment = new JPanel(new GridLayout(2, 1));
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Enrollment Status");
        statusEnrollment.setBorder(titledBorder1);
        JPanel statusInnerDiv = new JPanel(new GridLayout(3, 2));
        statusInnerDiv.add(new JLabel("Course:"));
        statusInnerDiv.add(courseLabel);
        statusInnerDiv.add(new JLabel("Credits:"));
        statusInnerDiv.add(creditLabel);
        statusInnerDiv.add(new JLabel("Modules:"));
        statusInnerDiv.add(new JLabel(""));
        statusEnrollment.add(statusInnerDiv);
        JPanel modulePanel = new JPanel(new GridLayout(7,2));
        modulePanel.add(new JLabel("Module 1 - "));
        modulePanel.add(moduleLabel1);
        modulePanel.add(new JLabel("Module 2 - "));
        modulePanel.add(moduleLabel2);
        modulePanel.add(new JLabel("Module 3 - "));
        modulePanel.add(moduleLabel3);
        modulePanel.add(new JLabel("Module 4 - "));
        modulePanel.add(moduleLabel4);
        modulePanel.add(new JLabel("Module 5 - "));
        modulePanel.add(moduleLabel5);
        modulePanel.add(new JLabel("Module 6 - "));
        modulePanel.add(moduleLabel6);
        // modulePanel.add(new JLabel("Module 7 - "));
        // modulePanel.add(moduleLabel7);
        statusEnrollment.add(modulePanel);
        frame.add(statusEnrollment);

        JPanel mainInnerDiv = new JPanel(new GridLayout(2, 1, 0, 10));

        //COURSE ENROLLMENT SECTION
        String[] courseList = {"Course 1", "Course 2", "Course 3", "Course 4"};
        courseEnrollment = new JPanel(new GridLayout(3, 4));
        TitledBorder titledBorder2 = BorderFactory.createTitledBorder("Course Enrollment");
        courseEnrollment.setBorder(titledBorder2);
        courseEnrollment.add(new JLabel("Select course: "));
        JPanel forComboBoxLayout = new JPanel(new FlowLayout());
        courseSelectionList = new JComboBox<>(courseList);
        courseSelectionList.setPreferredSize(new Dimension(180, 30));
        forComboBoxLayout.add(courseSelectionList);
        courseEnrollment.add(forComboBoxLayout);
        courseEnrollment.add(new JLabel(""));
        courseEnrollment.add(new JLabel("Duration:"));
        courseEnrollment.add(new JLabel(""));
        //button placement-start
        courseBtn = new JButton("Enroll");
        courseBtn.setPreferredSize(new Dimension(75, 15));
        JPanel forBtnLayout = new JPanel(new FlowLayout());
        forBtnLayout.add(courseBtn);
        courseEnrollment.add(forBtnLayout);
        //button placement-end
        courseEnrollment.add(new JLabel("Tuition Fee:"));
        courseEnrollment.add(new JLabel(""));
        courseEnrollment.add(new JLabel(""));
        
        mainInnerDiv.add(courseEnrollment);

        //MODULE ENROLLMENT SECTION
        moduleEnrollment = new JPanel(new GridLayout(6, 4));
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Module Enrollment");
        moduleEnrollment.setBorder(titledBorder3);
        box1 = new JCheckBox();
        moduleEnrollment.add(box1);
        moduleEnrollment.add(moduleLabel1);
        moduleEnrollment.add(credsLabel1);
        moduleEnrollment.add(new JLabel(""));
        box2 = new JCheckBox();
        moduleEnrollment.add(box2);
        moduleEnrollment.add(moduleLabel2);
        moduleEnrollment.add(credsLabel2);
        moduleEnrollment.add(new JLabel(""));
        box3 = new JCheckBox();
        moduleEnrollment.add(box3);
        moduleEnrollment.add(moduleLabel3);
        moduleEnrollment.add(credsLabel3);
        //button placement-start
        moduleBtn = new JButton("Enroll");
        moduleBtn.setPreferredSize(new Dimension(75, 15));
        JPanel forBtnLayout2 = new JPanel(new FlowLayout());
        forBtnLayout2.add(moduleBtn);
        moduleEnrollment.add(forBtnLayout2);
        mainInnerDiv.add(moduleEnrollment);
        //button placement-end
        box4 = new JCheckBox();
        moduleEnrollment.add(box4);
        moduleEnrollment.add(moduleLabel4);
        moduleEnrollment.add(credsLabel4);
        moduleEnrollment.add(new JLabel(""));
        box5 = new JCheckBox();
        moduleEnrollment.add(box5);
        moduleEnrollment.add(moduleLabel5);
        moduleEnrollment.add(credsLabel5);
        moduleEnrollment.add(new JLabel(""));
        box6 = new JCheckBox();
        moduleEnrollment.add(box6);
        moduleEnrollment.add(moduleLabel6);
        moduleEnrollment.add(credsLabel6);

        frame.add(mainInnerDiv);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new Enrollment();
    }
}
