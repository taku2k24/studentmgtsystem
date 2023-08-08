import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {
    private JFrame frame;
    private JPanel personalDetails, courseDetails, otherDetails;
    private JButton editBtn;

    private JLabel idLabel, lnLabel, fnLabel, dobLabel, adLabel, mnLabel, pmailLabel, umailLabel;
    private JLabel courseLabel, creditsJLabel, paymentLabel, transcriptLabel;
    private JLabel yearLabel, gradLabel;

    private String username;

    public Profile(String username) {
        super();
        this.username = username;
        initializeUI(username);
    }

    private void initializeUI(String username) {
        // EMPTY PLACEHOLDERS
        idLabel = new JLabel("");
        lnLabel = new JLabel("");
        fnLabel = new JLabel("");
        dobLabel = new JLabel("");
        adLabel = new JLabel("");
        mnLabel = new JLabel("");
        pmailLabel = new JLabel("");
        umailLabel = new JLabel("");
        courseLabel = new JLabel("");
        creditsJLabel = new JLabel("");
        paymentLabel = new JLabel("");
        transcriptLabel = new JLabel("");
        yearLabel = new JLabel("");
        gradLabel = new JLabel("");

        frame = new JFrame("Profile");
        frame.setLayout(new GridLayout(3, 1, 0, 30));

        // PERSONAL DETAILS SECTION
        personalDetails = new JPanel(new GridLayout(9, 2));
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Personal details");
        personalDetails.setBorder(titledBorder1);
        frame.add(personalDetails);
        personalDetails.add(new JLabel("Student ID:"));
        personalDetails.add(idLabel);
        personalDetails.add(new JLabel("Last Name:"));
        personalDetails.add(lnLabel);
        personalDetails.add(new JLabel("First Name:"));
        personalDetails.add(fnLabel);
        personalDetails.add(new JLabel("Date of Birth:"));
        personalDetails.add(dobLabel);
        personalDetails.add(new JLabel("Address:"));
        personalDetails.add(adLabel);
        personalDetails.add(new JLabel("Mobile Number:"));
        personalDetails.add(mnLabel);
        personalDetails.add(new JLabel("Personal Email:"));
        personalDetails.add(pmailLabel);
        personalDetails.add(new JLabel("University Mail:"));
        personalDetails.add(umailLabel);
        personalDetails.add(new JLabel("")); // EMPTY: to fill grid space
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        editBtn = new JButton("Edit");
        buttonPanel.add(new JLabel("")); // EMPTY: to fill grid space
        buttonPanel.add(new JLabel("")); // EMPTY: to fill grid space
        buttonPanel.add(new JLabel("")); // EMPTY: to fill grid space
        buttonPanel.add(editBtn);
        personalDetails.add(buttonPanel);

        // COURSE DETAILS SECTION
        courseDetails = new JPanel(new GridLayout(8, 2));
        TitledBorder titledBorder2 = BorderFactory.createTitledBorder("Course details");
        courseDetails.setBorder(titledBorder2);
        frame.add(courseDetails);
        courseDetails.add(new JLabel("Course:"));
        courseDetails.add(courseLabel);
        courseDetails.add(new JLabel("Credits:"));
        courseDetails.add(creditsJLabel);
        courseDetails.add(new JLabel("Payment Status:"));
        courseDetails.add(paymentLabel);
        courseDetails.add(new JLabel("Transcript:"));
        courseDetails.add(transcriptLabel);
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space

        // OTHER DETAILS SECTION
        otherDetails = new JPanel(new GridLayout(8, 2));
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Other details");
        otherDetails.setBorder(titledBorder3);
        frame.add(otherDetails);
        otherDetails.add(new JLabel("Year enrolled:"));
        otherDetails.add(yearLabel);
        otherDetails.add(new JLabel("Expected graduation:"));
        otherDetails.add(gradLabel);
        otherDetails.add(new JLabel("")); // EMPTY: to fill grid space
        otherDetails.add(new JLabel("")); // EMPTY: to fill grid space
        otherDetails.add(new JLabel("")); // EMPTY: to fill grid space
        otherDetails.add(new JLabel("")); // EMPTY: to fill grid space
        otherDetails.add(new JLabel("")); // EMPTY: to fill grid space
        otherDetails.add(new JLabel("")); // EMPTY: to fill grid space

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show an input dialog to get the updated values from the user
                String newAddress = JOptionPane.showInputDialog(frame, "Enter new address:");
                String newMobileNumber = JOptionPane.showInputDialog(frame, "Enter new mobile number:");
                String newPersonalEmail = JOptionPane.showInputDialog(frame, "Enter new personal email:");

                // Update the UI labels with the new values
                adLabel.setText(newAddress);
                mnLabel.setText(newMobileNumber);
                pmailLabel.setText(newPersonalEmail);

                // Update the database with the new values
                updateDatabase(newAddress, newMobileNumber, newPersonalEmail);
            }
        });

        // Fetch user data from the database and populate the labels
        fetchUserData(username);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void fetchUserData(String username) {
        // Get the connection instance from the DatabaseConnectionManager
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();

        // Prepare the select statement to retrieve user data
        String selectQuery = "SELECT * FROM student WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a record exists for the given username
            if (resultSet.next()) {
                // Retrieve data from the result set
                String studentID = resultSet.getString("student_id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                String dob = resultSet.getString("date_of_birth");
                String address = resultSet.getString("address");
                String mobileNumber = resultSet.getString("contact_number");
                String personalEmail = resultSet.getString("email");
                // String universityMail = resultSet.getString("university_mail");

                // Set the retrieved data to the corresponding labels
                idLabel.setText(studentID);
                lnLabel.setText(lastName);
                fnLabel.setText(firstName);
                dobLabel.setText(dob);
                adLabel.setText(address);
                mnLabel.setText(mobileNumber);
                pmailLabel.setText(personalEmail);
                // umailLabel.setText(universityMail);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching user data. Please try again.");
        }

        String selectQuery2 = "SELECT c.course_name, c.course_duration, sc.enrollment_date " +
                     "FROM student s " +
                     "INNER JOIN student_courses sc ON s.student_id = sc.student_id " +
                     "INNER JOIN courses c ON s.course_id = c.course_id " +
                     "WHERE s.username = ?";
        try {
            PreparedStatement preparedStatement2 = connection.prepareStatement(selectQuery2);
            preparedStatement2.setString(1, username);
            ResultSet resultSet = preparedStatement2.executeQuery();

            // Check if a record exists for the given username
            if (resultSet.next()) {
                // Retrieve data from the result set
                String course = resultSet.getString("course_name");
                String courseDuration = resultSet.getString("course_duration");
                String dateEnrolled = resultSet.getString("enrollment_date");
                // Calculate expected graduation year based on year enrolled and course duration
                int yearEnrolled = Integer.parseInt(dateEnrolled.substring(0, 4));
                int expectedGraduationYear = yearEnrolled + Integer.parseInt(courseDuration);

                // Set the retrieved data to the corresponding labels
                courseLabel.setText(course);
                creditsJLabel.setText(""); // YET TO BE IMPLEMENTED
                yearLabel.setText(String.valueOf(yearEnrolled));
                gradLabel.setText(String.valueOf(expectedGraduationYear));
            }

            resultSet.close();
            preparedStatement2.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching course data. Please try again.");
        }

        // Should be query that will fetch and fill values for payment status and display
        // whether a transcript is available or not
        // String selectQuery3 = "SELECT * FROM student WHERE username = ?";
        // try {
        // PreparedStatement preparedStatement3 =
        // connection.prepareStatement(selectQuery3);
        // preparedStatement3.setString(1, username);
        // ResultSet resultSet = preparedStatement3.executeQuery();

        // // Check if a record exists for the given username
        // if (resultSet.next()) {
        // // Retrieve data from the result set
        // String paymentStatus = resultSet.getString("payment_status");
        // String transcript = resultSet.getString("transcript");

        // // Set the retrieved data to the corresponding labels
        // paymentLabel.setText(paymentStatus);
        // transcriptLabel.setText(transcript);
        // }

        // resultSet.close();
        // preparedStatement3.close();
        // } catch (SQLException ex) {
        // ex.printStackTrace();
        // JOptionPane.showMessageDialog(frame, "Error fetching status data. Please try
        // again.");
        // }
    }

    private void updateDatabase(String address, String mobileNumber, String personalEmail) {
        // Get the connection instance from the DatabaseConnectionManager
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();

        // Prepare the update statement
        String updateQuery = "UPDATE student SET address = ?, contact_number = ?, email = ? WHERE student_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, mobileNumber);
            preparedStatement.setString(3, personalEmail);
            preparedStatement.setInt(4, Integer.parseInt(idLabel.getText())); // Assuming student ID is an integer
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Optionally, you may add a success message to notify the user that the update
            // was successful.
            JOptionPane.showMessageDialog(frame, "Profile updated successfully!");
        } catch (SQLException ex) {
            // Handle any errors that occur during the database update.
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating profile. Please try again.");
            
        }
    }

    // public static void main(String[] args){
    // new Profile();
    // }
}
