// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.sql.*;

// public class StudentRegistrationFrame extends JFrame {
//     private JTextField firstNameField;
//     private JTextField lastNameField;
//     private JTextField dateOfBirthField;
//     private JTextField genderField;
//     private JTextField addressField;
//     private JTextField contactNumberField;
//     private JTextField emailField;
//     private JTextField admissionDateField;
//     private JTextField batchField;
//     private JComboBox<String> courseNameComboBox;
//     private JTextField usernameField;
//     private static final String DB_URL = "jdbc:mysql://localhost:3306/student_ms";
//     private static final String DB_USER = "root";
//     private static final String DB_PASSWORD = "Password123#@!";

//     private String username; // Store the username

//     // Rest of the code remains the same...
//     private JButton submitButton;
//     private Connection connection;

//     public StudentRegistrationFrame(String username) {
//         super("Student Registration");
//        this.username = username;
//         initializeUI();
//         connectToDatabase();
//     }


//     private void initializeUI() {
//         // Create and arrange UI components
//         JPanel panel = new JPanel(new GridBagLayout());
//         GridBagConstraints constraints = new GridBagConstraints();
//         constraints.fill = GridBagConstraints.HORIZONTAL;
//         constraints.insets = new Insets(10, 10, 10, 10);

//         JLabel firstNameLabel = new JLabel("First Name:");
//         constraints.gridx = 0;
//         constraints.gridy = 0;
//         panel.add(firstNameLabel, constraints);

//         firstNameField = new JTextField(20);
//         constraints.gridx = 1;
//         constraints.gridy = 0;
//         panel.add(firstNameField, constraints);

//         // Rest of the fields for other student details...

//         JLabel lastNameLabel = new JLabel("Last Name:");
//         constraints.gridx = 0;
//         constraints.gridy = 1;
//         panel.add(lastNameLabel, constraints);

//         lastNameField = new JTextField(20);
//         constraints.gridx = 1;
//         constraints.gridy = 1;
//         panel.add(lastNameField, constraints);

//         JLabel dateOfBirthLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
//         constraints.gridx = 0;
//         constraints.gridy = 2;
//         panel.add(dateOfBirthLabel, constraints);

//         dateOfBirthField = new JTextField(20);
//         constraints.gridx = 1;
//         constraints.gridy = 2;
//         panel.add(dateOfBirthField, constraints);

//         JLabel genderLabel = new JLabel("Gender:");
//         constraints.gridx = 0;
//         constraints.gridy = 3;
//         panel.add(genderLabel, constraints);

//         genderField = new JTextField(10);
//         constraints.gridx = 1;
//         constraints.gridy = 3;
//         panel.add(genderField, constraints);

//         JLabel addressLabel = new JLabel("Address:");
//         constraints.gridx = 0;
//         constraints.gridy = 4;
//         panel.add(addressLabel, constraints);

//         addressField = new JTextField(50);
//         constraints.gridx = 1;
//         constraints.gridy = 4;
//         panel.add(addressField, constraints);

//         JLabel contactNumberLabel = new JLabel("Contact Number:");
//         constraints.gridx = 0;
//         constraints.gridy = 5;
//         panel.add(contactNumberLabel, constraints);

//         contactNumberField = new JTextField(15);
//         constraints.gridx = 1;
//         constraints.gridy = 5;
//         panel.add(contactNumberField, constraints);

//         JLabel emailLabel = new JLabel("Email:");
//         constraints.gridx = 0;
//         constraints.gridy = 6;
//         panel.add(emailLabel, constraints);

//         emailField = new JTextField(30);
//         constraints.gridx = 1;
//         constraints.gridy = 6;
//         panel.add(emailField, constraints);

//         JLabel admissionDateLabel = new JLabel("Admission Date (YYYY-MM-DD):");
//         constraints.gridx = 0;
//         constraints.gridy = 7;
//         panel.add(admissionDateLabel, constraints);

//         admissionDateField = new JTextField(20);
//         constraints.gridx = 1;
//         constraints.gridy = 7;
//         panel.add(admissionDateField, constraints);

//         JLabel batchLabel = new JLabel("Batch:");
//         constraints.gridx = 0;
//         constraints.gridy = 8;
//         panel.add(batchLabel, constraints);

//         batchField = new JTextField(10);
//         constraints.gridx = 1;
//         constraints.gridy = 8;
//         panel.add(batchField, constraints);

//         JLabel courseNameLabel = new JLabel("Course Name:");
//         constraints.gridx = 0;
//         constraints.gridy = 9;
//         panel.add(courseNameLabel, constraints);

//         String[] courseNames = {
//             "Bachelor of Arts (BA)",
//             "Bachelor of Science (BS)",
//             // Add other course names here...
//         };
//         courseNameComboBox = new JComboBox<>(courseNames);
//         constraints.gridx = 1;
//         constraints.gridy = 9;
//         panel.add(courseNameComboBox, constraints);

//         JLabel usernameLabel = new JLabel("Username:");
//         constraints.gridx = 0;
//         constraints.gridy = 10;
//         panel.add(usernameLabel, constraints);

//         usernameField = new JTextField(20);
//         constraints.gridx = 1;
//         constraints.gridy = 10;
//         panel.add(usernameField, constraints);

//         // Create a "Submit" button
//         submitButton = new JButton("Submit");
//         constraints.gridx = 0;
//         constraints.gridy = 11;
//         constraints.gridwidth = 2;
//         submitButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 saveStudentDetails();
//             }
//         });
//         panel.add(submitButton, constraints);

//         add(panel, BorderLayout.CENTER);
//     }

//     // Rest of the code remains the same...

//         // Set frame properties, size, and visibility.

//         // Same method to connect to the database as used in the previous code.
//         private void connectToDatabase() {
//             try {
//                 // Load the MySQL JDBC driver
//                 Class.forName("com.mysql.cj.jdbc.Driver");
                
//                 // Establish the connection
//                 connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//                 System.out.println("Connected to the database.");
//             } catch (ClassNotFoundException e) {
//                 e.printStackTrace();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//         }
    
//     private void saveStudentDetails() {
//         // Retrieve student details from text fields
//             String firstName = firstNameField.getText();
//             String lastName = lastNameField.getText();
//             String dateOfBirth = dateOfBirthField.getText();
//             String gender = genderField.getText();
//             String address = addressField.getText();
//             String contactNumber = contactNumberField.getText();
//             String email = emailField.getText();
//             String admissionDate = admissionDateField.getText();
//             String batch = batchField.getText();
//             String courseName = (String) courseNameComboBox.getSelectedItem();
//             String username = this.username; // Get the username from the text field
    
//             // Display the retrieved data in a JOptionPane
//             StringBuilder result = new StringBuilder();
//             result.append("First Name: ").append(firstName).append("\n");
//             result.append("Last Name: ").append(lastName).append("\n");
//             result.append("Date of Birth: ").append(dateOfBirth).append("\n");
//             result.append("Gender: ").append(gender).append("\n");
//             result.append("Address: ").append(address).append("\n");
//             result.append("Contact Number: ").append(contactNumber).append("\n");
//             result.append("Email: ").append(email).append("\n");
//             result.append("Admission Date: ").append(admissionDate).append("\n");
//             result.append("Batch: ").append(batch).append("\n");
//             result.append("Course Name: ").append(courseName).append("\n");
//             result.append("Username: ").append(username).append("\n");
    
//             JOptionPane.showMessageDialog(this, result.toString(), "Student Details", JOptionPane.INFORMATION_MESSAGE);
        

//         // Insert the student details into the database
//         try {
//             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "username", "password");
//             String query = "INSERT INTO student (first_name, last_name, date_of_birth, gender, address, contact_number, email, admission_date, batch, course_name, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//             PreparedStatement preparedStatement = connection.prepareStatement(query);
//             preparedStatement.setString(1, firstName);
//             preparedStatement.setString(2, lastName);
//             preparedStatement.setString(3, dateOfBirth);
//             preparedStatement.setString(4, gender);
//             preparedStatement.setString(5, address);
//             preparedStatement.setString(6, contactNumber);
//             preparedStatement.setString(7, email);
//             preparedStatement.setString(8, admissionDate);
//             preparedStatement.setString(9, batch);
//             preparedStatement.setString(10, courseName);
//             preparedStatement.setString(11, username); // Use the stored username

//             preparedStatement.executeUpdate();
//             connection.close();
//             JOptionPane.showMessageDialog(this, "Student details saved successfully!");
//         } catch (SQLException e) {
//             JOptionPane.showMessageDialog(this, "Error saving student details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//         }
//     }
//     }