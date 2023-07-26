import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Payment {
    private JFrame frame;
    private JPanel statusPanel, paymentPanel, subPayPanel1, subPayPanel2;
    private JButton payNowBtn, totalBtn, confirmPaymentBtn;
    private JCheckBox adminfBOX, tuitionfBOX, transportfBOX;
    private JRadioButton cashRBTN, creditRBTN, debitRBTN;

    private JLabel paymentstatusLabel, adminStatusLabel, tuitionStatusLabel, transportStatusLabel;
    private JLabel totalFee;

    private String username;
    private PaymentManager paymentManager;

    public Payment(String username) {
        super();
        this.username = username;
        this.paymentManager = new PaymentManager(DatabaseConnectionManager.getInstance());
        initializeUI(username);
    }

    public void initializeUI(String username) {
        // PLACEHOLDERS
        paymentstatusLabel = new JLabel("N/A");
        adminStatusLabel = new JLabel("N/A");
        tuitionStatusLabel = new JLabel("N/A");
        transportStatusLabel = new JLabel("N/A");
        totalFee = new JLabel("Rs 0");

        frame = new JFrame("Payment");
        frame.setLayout(new GridLayout(2, 1));

        // The Status Panel found in the first half of the page
        statusPanel = new JPanel(new GridLayout(5, 2));
        statusPanel.add(new JLabel("Payment Status:"));
        statusPanel.add(paymentstatusLabel);
        statusPanel.add(new JLabel("Admin fees:"));
        statusPanel.add(adminStatusLabel);
        statusPanel.add(new JLabel("Tuition fees:"));
        statusPanel.add(tuitionStatusLabel);
        statusPanel.add(new JLabel("Transport fees:"));
        statusPanel.add(transportStatusLabel);
        JPanel innerStatusPanel = new JPanel(new FlowLayout());
        payNowBtn = new JButton("Pay Now");
        innerStatusPanel.add(payNowBtn);
        statusPanel.add(innerStatusPanel);
        frame.add(statusPanel);

        Border blackline = BorderFactory.createLineBorder(Color.black);
        // The Payment Panel found in the second half of the page
        paymentPanel = new JPanel(new GridLayout(1, 2));

        // Contains stuff being paid for
        subPayPanel1 = new JPanel(new GridLayout(5, 1));
        subPayPanel1.add(new JLabel("1. Select modules being paid for: "));
        JPanel innerSub1Panel1 = new JPanel(new GridLayout(1, 2)); // Admin
        adminfBOX = new JCheckBox("Admin fees");
        innerSub1Panel1.add(adminfBOX);
        subPayPanel1.add(innerSub1Panel1);

        JPanel innerSub1Panel2 = new JPanel(new GridLayout(1, 2)); // Tuition
        tuitionfBOX = new JCheckBox("Tuition fees");
        innerSub1Panel2.add(tuitionfBOX);
        subPayPanel1.add(innerSub1Panel2);

        JPanel innerSub1Panel3 = new JPanel(new GridLayout(1, 2)); // Transport
        transportfBOX = new JCheckBox("Metro/Bus pass fees");
        innerSub1Panel3.add(transportfBOX);
        subPayPanel1.add(innerSub1Panel3);

        JPanel innerSub1Panel4 = new JPanel(new FlowLayout()); // Total
        totalBtn = new JButton("Calculate Total");
        innerSub1Panel4.add(totalBtn);
        innerSub1Panel4.add(totalFee);
        subPayPanel1.add(innerSub1Panel4);
        paymentPanel.add(subPayPanel1);

        // Contains the payment methods
        subPayPanel2 = new JPanel(new GridLayout(5, 1));
        subPayPanel2.add(new JLabel("2. Select payment method:"));
        JPanel innerSub2Panel1 = new JPanel(new FlowLayout());
        cashRBTN = new JRadioButton("Cash");
        creditRBTN = new JRadioButton("Credit");
        debitRBTN = new JRadioButton("Debit");
        ButtonGroup rBTNgroup = new ButtonGroup(); // Adding Radio buttons to a group
        rBTNgroup.add(cashRBTN);
        rBTNgroup.add(creditRBTN);
        rBTNgroup.add(debitRBTN);
        innerSub2Panel1.add(cashRBTN);
        innerSub2Panel1.add(creditRBTN);
        innerSub2Panel1.add(debitRBTN);
        subPayPanel2.add(innerSub2Panel1);
        JPanel innerSub2Panel2 = new JPanel(new FlowLayout());
        confirmPaymentBtn = new JButton("Confirm");
        innerSub2Panel2.add(confirmPaymentBtn);
        subPayPanel2.add(innerSub2Panel2);
        paymentPanel.add(subPayPanel2);
        frame.add(paymentPanel);

        subPayPanel1.setBorder(blackline);
        subPayPanel2.setBorder(blackline);

        paymentPanel.setVisible(false);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT payment_status, admin_fee, tuition_fee, transport_fee " +
                    "FROM payment " +
                    "WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String paymentStatus = resultSet.getString("payment_status");
                boolean adminFee = resultSet.getBoolean("admin_fee");
                boolean tuitionFee = resultSet.getBoolean("tuition_fee");
                boolean transportFee = resultSet.getBoolean("transport_fee");

                paymentstatusLabel.setText(paymentStatus);
                adminStatusLabel.setText(adminFee ? "Paid" : "Not Paid");
                tuitionStatusLabel.setText(tuitionFee ? "Paid" : "Not Paid");
                transportStatusLabel.setText(transportFee ? "Paid" : "Not Paid");

                // Show or hide Pay Now button based on payment status
                if (!"Complete Payment Done".equals(paymentStatus)) {
                    payNowBtn.setVisible(true);
                } else {
                    payNowBtn.setVisible(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        totalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int total = 0;
                if (adminfBOX.isSelected()) {
                    total += 10;
                }
                if (tuitionfBOX.isSelected()) {
                    total += 20;
                }
                if (transportfBOX.isSelected()) {
                    total += 100;
                }
                totalFee.setText("Rs " + total);
            }
        });

        confirmPaymentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String paymentMethod = cashRBTN.isSelected() ? "Cash" : creditRBTN.isSelected() ? "Credit" : "Debit";
                String amountPaidString = totalFee.getText();
                int amountPaid = Integer.parseInt(amountPaidString.split(" ")[1]); // Extract the numeric value from
                
                String confirmationMessage = "Confirm Payment:\n\n" +
                        "Amount: " + amountPaidString + "\n" +
                        "Payment Method: " + paymentMethod;

                int option = JOptionPane.showConfirmDialog(frame, confirmationMessage, "Payment Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Perform payment confirmation logic here
                    boolean adminFeePaid = adminfBOX.isSelected();
                    boolean tuitionFeePaid = tuitionfBOX.isSelected();
                    boolean transportFeePaid = transportfBOX.isSelected();

                    String paymentStatus;
                    if (adminFeePaid && tuitionFeePaid && transportFeePaid) {
                        paymentStatus = "Complete Payment Done";
                    } else if (adminFeePaid || tuitionFeePaid || transportFeePaid) {
                        paymentStatus = "Incomplete Payment Done";
                    } else {
                        paymentStatus = "No Payment Done";
                    }

                    paymentManager.updatePaymentStatus(username, paymentStatus, paymentMethod, adminFeePaid, tuitionFeePaid, transportFeePaid);

                    // Display a success message to the user
                    JOptionPane.showMessageDialog(frame, "Payment confirmed successfully!");

                    // After confirming the payment, refresh the UI to update the payment status
                    frame.dispose();
                    initializeUI(username);
                }
            }
        });

        payNowBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the status panel's visibility to true
                paymentPanel.setVisible(true);
        
                // // Repack the frame to accommodate the visible status panel
                // frame.pack();
            }
        });
    }

    public static void main(String[] args) {
        new Payment("user1");
    }
}
