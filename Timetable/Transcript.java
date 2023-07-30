package db;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Transcript extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, jp_label, jp_btn, jp_outer;
	private JTable table;
	private JButton btnDisplay, btnClear;
	private JScrollPane sp;
	private JLabel jl_1, jl_2, jl_3, jl_4, jl_5;

	
	   public Transcript() {
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
		}) ;

		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial", Font.BOLD, 11));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clear();
			}
		}) ;
		
		jp_btn= new JPanel(new FlowLayout());
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
        
        jp_label = new JPanel(new GridLayout(2,3));
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
		
		
		
	}
	   
	   //Display Button Handler
	   private void Display() {
		  try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
			    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_ms","root","1234");
				
			    //private string username= username.getLogin();

				String query = "select student_id, first_name, last_name, courses.course_name, courses.course_code from student, courses where student.course_id= courses.course_id and username = \"john_doe123\";";
				
				Statement st = con.prepareStatement(query);
				ResultSet rs = st.executeQuery(query);
				
				if (rs.next()) {
					
					String stdID = "Student ID: " + rs.getString(1);
					String stdF = "First Name: " + rs.getString(2);
					String stdL = "Last Name: " + rs.getString(3);
					String stdCN = "Course Name: " + rs.getString(4);
					String stdCC = "Course Code: " + rs.getString(5);
				
					jl_1.setText(stdID);
					jl_2.setText(stdF);
					jl_3.setText(stdL);
					jl_4.setText(stdCN);
					jl_5.setText(stdCC);
					
					
				}
				
				
				
				String sql = "SELECT marks.module_id, modules.module_name, marks.coursework_marks, marks.exam_marks\r\n"
						+ "from marks, modules \r\n"
						+ "left join  student\r\n"
						+ "on student.course_id = modules.course_id\r\n"
						+ "where marks.module_id = modules.module_id\r\n"
						+ "and marks.enrollment_id= student.student_id\r\n"
						+ "and student.username= \"john_doe123\";";
				
				
				/* PREPARED STATEMENT (EDIT TO ACCOMMODATE LOGGED IN USER)
				java.sql.Statement st = con.createStatement();
			    PreparedStatement st = con.prepareStatement(query);
                statement.setString(1, username); */
            
           
				Statement stmt = con.prepareStatement(sql);
				ResultSet rs2 = st.executeQuery(sql);
				ResultSetMetaData rsmd = rs2.getMetaData();
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				//fetch column info
				int cols = rsmd.getColumnCount();
				Object[] colName = {"Module ID", "Module Name","Coursework Marks","Exam Marks"};
				for(int i =0; i<cols; i++){
					model.setColumnIdentifiers(colName); 
				
					String modId, modName,courseworkM,examM ;
					while(rs2.next()) {
						modId = rs2.getString(1);
						modName = rs2.getString(2);
						courseworkM = rs2.getString(3);
						examM = rs2.getString(4);
						String[] row = {modId, modName,courseworkM,examM};
						model.addRow(row);
					}
					st.close();
					stmt.close();
					con.close();
		}


			}  catch(ClassNotFoundException | SQLException  e1){
	              e1.printStackTrace();}
			
			}
	   
	   //Clear Button Handler
	   private void Clear() {
		   table.setModel(new DefaultTableModel());
	   }
	   }


	

