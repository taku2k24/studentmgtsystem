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

    private JLabel idLabel, lnLabel, fnLabel, dobLabel, adLabel, mnLabel, pmailLabel;
    private JLabel courseLabel, paymentLabel, transcriptLabel;
    private JLabel yearLabel, gradLabel;

    private Color orangeShade = new Color(255, 140, 0); 
    private Color greyShade = new Color(100, 100, 100); 
    private Color lightorangeShade = new Color(255, 237, 227); // For bg color

    private String username;

    public Profile(String username) {
        super();
        this.username = username;
        initializeUI(username);
    }

    private void initializeUI(String username) {

        // EMPTY PLACEHOLDERS
        idLabel = new JLabel("");
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lnLabel = new JLabel("");
        lnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        fnLabel = new JLabel("");
        fnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dobLabel = new JLabel("");
        dobLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adLabel = new JLabel("");
        adLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mnLabel = new JLabel("");
        mnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pmailLabel = new JLabel("");
        pmailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        courseLabel = new JLabel("");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        paymentLabel = new JLabel("");
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        transcriptLabel = new JLabel("");
        transcriptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        yearLabel = new JLabel("");
        yearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gradLabel = new JLabel("");
        gradLabel.setFont(new Font("Arial", Font.BOLD, 16));

        frame = new JFrame("Profile");
        frame.setLayout(new GridLayout(3, 1));
        frame.setBackground(lightorangeShade);

        //ICON
        ImageIcon img = new ImageIcon("orange_icon.png");
        frame.setIconImage(img.getImage());

        // PERSONAL DETAILS SECTION
        personalDetails = new JPanel(new GridLayout(8, 2));
        personalDetails.setBackground(lightorangeShade);
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Personal details");
        titledBorder1.setTitleColor(orangeShade);
        titledBorder1.setTitleFont(new Font("Arial", Font.BOLD, 18));
        personalDetails.setBorder(titledBorder1);
        frame.add(personalDetails);
        JLabel id = new JLabel("Student ID:");
        id.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(id);
        personalDetails.add(idLabel);
        JLabel ln = new JLabel("Last Name:");
        ln.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(ln);
        personalDetails.add(lnLabel);
        JLabel fn = new JLabel("First Name:");
        fn.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(fn);
        personalDetails.add(fnLabel);
        JLabel dob = new JLabel("Date of Birth:");
        dob.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(dob);
        personalDetails.add(dobLabel);
        JLabel ad = new JLabel("Address:");
        ad.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(ad);
        personalDetails.add(adLabel);
        JLabel mn = new JLabel("Mobile Number:");
        mn.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(mn);
        personalDetails.add(mnLabel);
        JLabel pm = new JLabel("Personal Email:");
        pm.setFont(new Font("Arial", Font.PLAIN, 16));
        personalDetails.add(pm);
        personalDetails.add(pmailLabel);
        personalDetails.add(new JLabel("")); // EMPTY: to fill grid space
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.setBackground(lightorangeShade);
        editBtn = new JButton("Edit");
        editBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        editBtn.setBackground(Color.WHITE);
        buttonPanel.add(new JLabel("")); // EMPTY: to fill grid space
        buttonPanel.add(new JLabel("")); // EMPTY: to fill grid space
        buttonPanel.add(new JLabel("")); // EMPTY: to fill grid space
        buttonPanel.add(editBtn);
        personalDetails.add(buttonPanel);

        // COURSE DETAILS SECTION
        courseDetails = new JPanel(new GridLayout(7, 2));
        courseDetails.setBackground(lightorangeShade);
        TitledBorder titledBorder2 = BorderFactory.createTitledBorder("Course details");
        titledBorder2.setTitleColor(orangeShade);
        titledBorder2.setTitleFont(new Font("Arial", Font.BOLD, 18));
        courseDetails.setBorder(titledBorder2);
        frame.add(courseDetails);
        JLabel c = new JLabel("Course:");
        c.setFont(new Font("Arial", Font.PLAIN, 16));
        courseDetails.add(c);
        courseDetails.add(courseLabel);
        JLabel p = new JLabel("Payment Status:");
        p.setFont(new Font("Arial", Font.PLAIN, 16));
        courseDetails.add(p);
        courseDetails.add(paymentLabel);
        JLabel t = new JLabel("Transcript:");
        t.setFont(new Font("Arial", Font.PLAIN, 16));
        courseDetails.add(t);
        courseDetails.add(transcriptLabel);
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space
        courseDetails.add(new JLabel("")); // EMPTY: to fill grid space

        // OTHER DETAILS SECTION
        otherDetails = new JPanel(new GridLayout(8, 2));
        otherDetails.setBackground(lightorangeShade);
        TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Other details");
        titledBorder3.setTitleColor(orangeShade);
        titledBorder3.setTitleFont(new Font("Arial", Font.BOLD, 18));
        otherDetails.setBorder(titledBorder3);
        frame.add(otherDetails);
        JLabel y = new JLabel("Year enrolled:");
        y.setFont(new Font("Arial", Font.PLAIN, 16));
        otherDetails.add(y);
        otherDetails.add(yearLabel);
        JLabel g = new JLabel("Expected graduation:");
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        otherDetails.add(g);
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

        // Hover effect for module checkboxes
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editBtn.setBackground(Color.ORANGE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                editBtn.setBackground(Color.WHITE);
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
                yearLabel.setText(String.valueOf(yearEnrolled));
                gradLabel.setText(String.valueOf(expectedGraduationYear));
            }

            resultSet.close();
            preparedStatement2.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching course data. Please try again.");
        }

        // Should be query that will fetch and fill values for payment status and
        // display
        // whether a transcript is available or not
        String selectQuery3 = "SELECT payment_status FROM payment WHERE userID = ?";
        try {
            PreparedStatement preparedStatement3 = connection.prepareStatement(selectQuery3);
            preparedStatement3.setString(1, username);
            ResultSet resultSet = preparedStatement3.executeQuery();

            // Check if a record exists for the given username
            if (resultSet.next()) {
                // Retrieve data from the result set
                String paymentStatus = resultSet.getString("payment_status");

                // Set the retrieved data to the corresponding labels
                paymentLabel.setText(paymentStatus);
            } else {
                paymentLabel.setText("No Information Available");
            }

            resultSet.close();
            preparedStatement3.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching status data. Please try again.");
        }

        // Retriving transcript data
        String selectQuery4 = "SELECT mark_id FROM marks WHERE enrollment_id = ?";
        try {
            PreparedStatement preparedStatement4 = connection.prepareStatement(selectQuery4);
            preparedStatement4.setString(1, username);
            ResultSet resultSet = preparedStatement4.executeQuery();

            // Check if a record exists for the given username
            if (resultSet.next()) {
                transcriptLabel.setText("Transcript Available");
            } else {
                transcriptLabel.setText("Transcript NOT Available");
            }

            resultSet.close();
            preparedStatement4.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching credits data. Please try again.");
        }
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

    public static void main(String[] args) {
        new Profile("jessica_miller567");
    }
}
