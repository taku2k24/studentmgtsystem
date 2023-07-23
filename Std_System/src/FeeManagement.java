// Class that allows student to pay their fees
// Runs separately for now

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FeeManagement extends JFrame {
    private JLabel paymentStatusLabel;
    private JButton payNowButton;
    private JPanel paymentPanel;
    private JLabel amountLabel;
    private JTextField accountNumberField;
    private JButton confirmButton;

    // Replace 'student_id' with the actual ID of the current student
    private int student_id = 62521; 

    private DatabaseConnectionManager connectionManager;

    public FeeManagement() {
        paymentStatusLabel = new JLabel();
        payNowButton = new JButton("Pay Now");
        paymentPanel = new JPanel();
        amountLabel = new JLabel();
        accountNumberField = new JTextField(10);
        confirmButton = new JButton("Confirm");

        // Create an instance of DatabaseConnectionManager
        connectionManager = DatabaseConnectionManager.getInstance();

        // Set up the main window
        setTitle("Fee Management");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the preferred size for the content pane
        Dimension preferredSize = new Dimension(600, 600);
        getContentPane().setPreferredSize(preferredSize);
        pack(); // Adjust the frame size to fit its contents
        setLocationRelativeTo(null);

        // Set up the payment status panel
        JPanel statusPanel = new JPanel();
        statusPanel.add(new JLabel("Payment Status: "));
        statusPanel.add(paymentStatusLabel);
        statusPanel.add(payNowButton);

        // Set up the payment panel
        paymentPanel.setLayout(new FlowLayout());
        paymentPanel.add(amountLabel);
        paymentPanel.add(new JLabel("Enter Account Number: "));
        paymentPanel.add(accountNumberField);
        paymentPanel.add(confirmButton);
        paymentPanel.setVisible(false);

        // Add action listener to the payNowButton
        payNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentPanel.setVisible(true);
            }
        });

        // Add action listener to the confirmButton
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountLabel.getText();
                String accountNumber = accountNumberField.getText();

                int choice = JOptionPane.showConfirmDialog(
                        FeeManagement.this,
                        "Do you confirm that " + amount + " will be deducted on your account " + accountNumber + " for this payment?",
                        "Confirm Payment",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    updatePaymentStatus("Paid");
                    paymentStatusLabel.setText("Paid");
                    paymentPanel.setVisible(false);
                }
            }
        });

        // Add components to the main window
        add(statusPanel, BorderLayout.NORTH);
        add(paymentPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void updatePaymentStatus(String status) {
        // Update the payment status for the current student in the database
        try (Connection connection = connectionManager.getConnection()) {
            String updateQuery = "UPDATE students SET payment_status = ? WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, status);
                // Replace 'student_id' with the actual ID of the current student
                preparedStatement.setInt(2, student_id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getPaymentStatus() {
        // Get the payment status for the current student from the database
        String paymentStatus = "Not Paid";
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT payment_status FROM students WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Replace 'student_id' with the actual ID of the current student
                preparedStatement.setInt(1, student_id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        paymentStatus = resultSet.getString("payment_status");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentStatus;
    }

    private double getCourseFeeForStudent() {
        double courseFee = 0;
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT course_Fee FROM courses " +
                           "JOIN students ON courses.course_ID = students.course_id " +
                           "WHERE students.student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, student_id); // Replace 'student_id' with the actual ID of the current student
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        courseFee = resultSet.getDouble("course_Fee");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseFee;
    }
    

    public void setAmountToBePaid() {
        double courseFee = getCourseFeeForStudent();
        amountLabel.setText("Amount to be paid: $" + courseFee);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FeeManagement feeManagementInterface = new FeeManagement();
                feeManagementInterface.setVisible(true);
    
                // Set the payment status and amount to be paid for the current student
                String paymentStatus = feeManagementInterface.getPaymentStatus();
                feeManagementInterface.paymentStatusLabel.setText(paymentStatus);
    
                feeManagementInterface.setAmountToBePaid(); // Call setAmountToBePaid() without any argument
            }
        });
    }    
}
