import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Profile {
    private JFrame frame;
    private JPanel personalDetails, courseDetails, otherDetails;
    private JButton editBtn;

    private JLabel idLabel, lnLabel, fnLabel, adLabel, mnLabel, pmailLabel, umailLabel;
    private JLabel courseLabel, creditsJLabel, paymentLabel, transcriptLabel;
    private JLabel yearLabel, gradLabel;

    private String username;

    public Profile(String username) {
        super();
        this.username = username;
        initializeUI(username);
    }

    private void initializeUI(String username) {
        // PLACEHOLDERS
        idLabel = new JLabel("826726");
        lnLabel = new JLabel("Aliana");
        fnLabel = new JLabel("Thompson");
        adLabel = new JLabel("Southpark, Cali");
        mnLabel = new JLabel("762-165-716");
        pmailLabel = new JLabel("liapark@gmail.com");
        umailLabel = new JLabel("aliana.thompson@umail.uoc.ac.us");
        courseLabel = new JLabel("BSc(Hons) Marine Biology");
        creditsJLabel = new JLabel("69");
        paymentLabel = new JLabel("All Paid");
        transcriptLabel = new JLabel("N/A");
        yearLabel = new JLabel("June 2020");
        gradLabel = new JLabel("December 2025");

        frame = new JFrame("Profile");
        frame.setLayout(new GridLayout(3, 1, 0, 30));

        // PERSONAL DETAILS SECTION
        personalDetails = new JPanel(new GridLayout(8, 2));
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Personal details");
        personalDetails.setBorder(titledBorder1);
        frame.add(personalDetails);
        personalDetails.add(new JLabel("Student ID:"));
        personalDetails.add(idLabel);
        personalDetails.add(new JLabel("Last Name:"));
        personalDetails.add(lnLabel);
        personalDetails.add(new JLabel("First Name:"));
        personalDetails.add(fnLabel);
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

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void updateDatabase(String address, String mobileNumber, String personalEmail) {
        // Get the connection instance from the DatabaseConnectionManager
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();

        // Prepare the update statement
        String updateQuery = "UPDATE students SET address = ?, mobile_number = ?, personal_email = ? WHERE student_id = ?";
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

