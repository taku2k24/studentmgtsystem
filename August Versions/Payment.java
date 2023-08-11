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
    private JButton payNowBtn,viewStatusBtn, totalBtn, confirmPaymentBtn;
    private JCheckBox adminfBOX, tuitionfBOX;
    private JRadioButton cashRBTN, creditRBTN, debitRBTN;

    private JLabel paymentstatusLabel, adminStatusLabel, tuitionStatusLabel, otherStatusLabel;
    private JLabel totalFee;

    private Color orangeShade = new Color(255, 140, 0); 
    private Color greyShade = new Color(100, 100, 100); 
    private Color lightorangeShade = new Color(255, 237, 227); // For bg color

    private int userID;
    private PaymentManager paymentManager;
    private JComboBox<String> Other;
    private final int ADMIN_FEES = 6000;

    public Payment(String username) {
        this.userID = getUserId(username);
        this.paymentManager = new PaymentManager(DatabaseConnectionManager.getInstance());
        initializeUI(username);
    }

    public void initializeUI(String username) {

        // PLACEHOLDERS
        paymentstatusLabel = new JLabel("N/A");
        paymentstatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adminStatusLabel = new JLabel("N/A");
        adminStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tuitionStatusLabel = new JLabel("N/A");
        tuitionStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        otherStatusLabel = new JLabel("N/A");
        otherStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalFee = new JLabel("Rs 0");
        totalFee.setFont(new Font("Arial", Font.BOLD, 16));

        frame = new JFrame("Payment");
        frame.setLayout(new GridLayout(2, 1));

        //ICON
        ImageIcon img = new ImageIcon("orange_icon.png");
        frame.setIconImage(img.getImage());

        // The Status Panel found in the first half of the page
        statusPanel = new JPanel(new GridLayout(5, 2)); // Reduce the grid layout rows since "Other" is in the JComboBox
        JLabel p = new JLabel("Payment Status:");
        p.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(p);
        statusPanel.add(paymentstatusLabel);
        JLabel a = new JLabel("Admin fees:");
        a.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(a);
        statusPanel.add(adminStatusLabel);
        JLabel t = new JLabel("Tuition fees:");
        statusPanel.add(t);
        t.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(tuitionStatusLabel);
        JLabel o = new JLabel("Other:");
        o.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(o);
        statusPanel.add(otherStatusLabel);
        JPanel innerStatusPanel = new JPanel(new FlowLayout());
        payNowBtn = new JButton("Pay Now");
        payNowBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        innerStatusPanel.add(payNowBtn);
        statusPanel.add(innerStatusPanel);
        frame.add(statusPanel);
     // ... (previous code remains the same)

        viewStatusBtn = new JButton("View Current Status");
        viewStatusBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        innerStatusPanel.add(viewStatusBtn);
        statusPanel.add(innerStatusPanel);
        frame.add(statusPanel);

        // ... (rest of the code remains the same)


        Border blackline = BorderFactory.createLineBorder(Color.black);
        // The Payment Panel found in the second half of the page
        paymentPanel = new JPanel(new GridLayout(1, 2));

        // Contains stuff being paid for
        subPayPanel1 = new JPanel(new GridLayout(5, 1)); // Decrease the grid layout rows since "Other" is in the JComboBox
        JLabel s1 = new JLabel("1. Select modules being paid for: ");
        s1.setFont(new Font("Arial", Font.BOLD, 16));
        s1.setForeground(orangeShade);
        subPayPanel1.add(s1);
        JPanel innerSub1Panel1 = new JPanel(new GridLayout(1, 2)); // Admin
        adminfBOX = new JCheckBox("Admin fees");
        adminfBOX.setFont(new Font("Arial", Font.PLAIN, 14));
        innerSub1Panel1.add(adminfBOX);
        subPayPanel1.add(innerSub1Panel1);

        JPanel innerSub1Panel2 = new JPanel(new GridLayout(1, 2)); // Tuition
        tuitionfBOX = new JCheckBox("Tuition fees");
        tuitionfBOX.setFont(new Font("Arial", Font.PLAIN, 14));
        innerSub1Panel2.add(tuitionfBOX);
        subPayPanel1.add(innerSub1Panel2);

        JPanel innerSub1Panel3 = new JPanel(new FlowLayout()); // Other options (dropdown)
        JLabel otherLabel = new JLabel("Other:");
        otherLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        String[] otherOptions = {"Module Registration", "Metro Card", "Bus Pass"};
        Other = new JComboBox<>(otherOptions);
        innerSub1Panel3.add(otherLabel);
        innerSub1Panel3.add(Other);
        subPayPanel1.add(innerSub1Panel3);

        JPanel innerSub1Panel4 = new JPanel(new FlowLayout()); // Total
        totalBtn = new JButton("Calculate Total");
        totalBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        innerSub1Panel4.add(totalBtn);
        innerSub1Panel4.add(totalFee);
        subPayPanel1.add(innerSub1Panel4);
        paymentPanel.add(subPayPanel1);

        // Contains the payment methods
        subPayPanel2 = new JPanel(new GridLayout(5, 1));
        JLabel s2 = new JLabel("2. Select payment method:");
        s2.setFont(new Font("Arial", Font.BOLD, 16));
        s2.setForeground(orangeShade);
        subPayPanel2.add(s2);
        JPanel innerSub2Panel1 = new JPanel(new FlowLayout());
        cashRBTN = new JRadioButton("Cash");
        cashRBTN.setFont(new Font("Arial", Font.PLAIN, 14));
        creditRBTN = new JRadioButton("Credit");
        creditRBTN.setFont(new Font("Arial", Font.PLAIN, 14));
        debitRBTN = new JRadioButton("Debit");
        debitRBTN.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonGroup rBTNgroup = new ButtonGroup(); // Adding Radio buttons to a group
        rBTNgroup.add(cashRBTN);
        rBTNgroup.add(creditRBTN);
        rBTNgroup.add(debitRBTN);
        innerSub2Panel1.add(cashRBTN);
        innerSub2Panel1.add(creditRBTN);
        innerSub2Panel1.add(debitRBTN);
        subPayPanel2.add(innerSub2Panel1);
        JPanel innerSub2Panel2 = new JPanel(new FlowLayout());
        confirmPaymentBtn = new JButton("Confirm Payment");
        confirmPaymentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        innerSub2Panel2.add(confirmPaymentBtn);
        subPayPanel2.add(innerSub2Panel2);
        paymentPanel.add(subPayPanel2);
        frame.add(paymentPanel);

        subPayPanel1.setBorder(blackline);
        subPayPanel2.setBorder(blackline);

        viewStatusBtn.setBackground(Color.WHITE);
        payNowBtn.setBackground(Color.WHITE);
        totalBtn.setBackground(Color.WHITE);
        confirmPaymentBtn.setBackground(Color.WHITE);
        innerStatusPanel.setBackground(lightorangeShade);
        frame.setBackground(lightorangeShade);
        statusPanel.setBackground(lightorangeShade);
        paymentPanel.setBackground(lightorangeShade);


        paymentPanel.setVisible(false);

        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        paymentstatusLabel.setText("N/A");
        adminStatusLabel.setText("N/A");
        tuitionStatusLabel.setText("N/A");
        otherStatusLabel.setText("N/A");
        payNowBtn.setVisible(true);
        
        totalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int total = 0;
                if (adminfBOX.isSelected()) {
                    total += ADMIN_FEES;
                }
                if (tuitionfBOX.isSelected()) {
                    // Get the tuition fees based on the selected course_id of the student
                    String selectedCourse = getSelectedCourse(username);
                    if (selectedCourse != null) {
                        int tuitionFees = getTuitionFees(selectedCourse);
                        total += tuitionFees;
                    }
                }
                if (Other.getSelectedItem() != null) {
                    String otherOption = Other.getSelectedItem().toString();
                    int flatFee = getFlatFeeForOtherOption(otherOption);
                    total += flatFee;
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
                    String otherOption = Other.getSelectedItem().toString(); // Get the selected "Other" option from the dropdown

                 // Store the selected "Other" option in a variable
                    String selectedOtherOption = otherOption;
                    
                    String paymentStatus;
                    if (adminFeePaid && tuitionFeePaid && selectedOtherOption != null) {
                        paymentStatus = "Complete Payment Done";
                    } else if (adminFeePaid || tuitionFeePaid || selectedOtherOption != null) {
                        paymentStatus = "Incomplete Payment Done";
                    } else {
                        paymentStatus = "No Payment Done";
                    }

                    // Call the insertPaymentStatus method to insert the payment data into the database
                    boolean insertSuccessful = paymentManager.insertPaymentStatus(userID, paymentStatus, paymentMethod, adminFeePaid, tuitionFeePaid, selectedOtherOption);
                    if (insertSuccessful) {
                        // Display a success message to the user
                        JOptionPane.showMessageDialog(frame, "Payment confirmed successfully!");

                        // After confirming the payment, refresh the UI to update the payment status
                        updateStatusLabels(username);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Payment update failed!");
                    }
                }
            }
        });

        payNowBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the status panel's visibility to true
                paymentPanel.setVisible(true);
            }
        });
        
        viewStatusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCurrentStatus();
            }
        });

        // Hover effects
        payNowBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                payNowBtn.setBackground(orangeShade);
                payNowBtn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                payNowBtn.setBackground(Color.WHITE);
                payNowBtn.setForeground(Color.BLACK);
            }
        });
        viewStatusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewStatusBtn.setBackground(orangeShade);
                viewStatusBtn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewStatusBtn.setBackground(Color.WHITE);
               viewStatusBtn.setForeground(Color.BLACK);
            }
        });
        confirmPaymentBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirmPaymentBtn.setBackground(orangeShade);
                confirmPaymentBtn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmPaymentBtn.setBackground(Color.WHITE);
               confirmPaymentBtn.setForeground(Color.BLACK);
            }
        });
        totalBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                totalBtn.setBackground(orangeShade);
                totalBtn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                totalBtn.setBackground(Color.WHITE);
                totalBtn.setForeground(Color.BLACK);
            }
        });

    }

    private void updateStatusLabels(String username) {
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT payment_status, admin_fee, tuition_fee, Other " +
                    "FROM payment " +
                    "WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, this.userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String paymentStatus = resultSet.getString("payment_status");
                boolean adminFee = resultSet.getBoolean("admin_fee");
                boolean tuitionFee = resultSet.getBoolean("tuition_fee");
                String otherOption = resultSet.getString("Other");

                paymentstatusLabel.setText(paymentStatus);
                adminStatusLabel.setText(adminFee ? "Paid" : "Not Paid");
                tuitionStatusLabel.setText(tuitionFee ? "Paid" : "Not Paid");
                otherStatusLabel.setText(otherOption);

                // Show or hide Pay Now button based on payment status
                if (!"Complete Payment Done".equals(paymentStatus)) {
                    payNowBtn.setVisible(true);
                } else {
                    payNowBtn.setVisible(false);
                }
            } else {
                // If no payment data found for the user, set default values and make Pay Now button visible
                paymentstatusLabel.setText("N/A");
                adminStatusLabel.setText("N/A");
                tuitionStatusLabel.setText("N/A");
                otherStatusLabel.setText("N/A");
                payNowBtn.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserId(String username) {
        int userID = -1;
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT student_id FROM student WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userID = resultSet.getInt("student_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }

    // This method gets the selected course_id of the student based on the username
    private String getSelectedCourse(String username) {
        String selectedCourse = null;
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT course_id FROM student WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                selectedCourse = resultSet.getString("course_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCourse;
    }
    
    private void viewCurrentStatus() {
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT payment_status, admin_fee, tuition_fee, Other " +
                    "FROM payment " +
                    "WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, this.userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String paymentStatus = resultSet.getString("payment_status");
                boolean adminFee = resultSet.getBoolean("admin_fee");
                boolean tuitionFee = resultSet.getBoolean("tuition_fee");
                String otherOption = resultSet.getString("Other");

                paymentstatusLabel.setText(paymentStatus);
                adminStatusLabel.setText(adminFee ? "Paid" : "Not Paid");
                tuitionStatusLabel.setText(tuitionFee ? "Paid" : "Not Paid");
                otherStatusLabel.setText(otherOption);
            } else {
                // If no payment data found for the user, display a message
                paymentstatusLabel.setText("No Payment Status Available");
                adminStatusLabel.setText("N/A");
                tuitionStatusLabel.setText("N/A");
                otherStatusLabel.setText("N/A");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        } finally {
            // Always set the "Pay Now" button visible after retrieving the payment status
            payNowBtn.setVisible(true);
        }
    }


    // This method gets the tuition fees based on the selected course_id
    private int getTuitionFees(String selectedCourse) {
        int tuitionFees = 0;
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT tuition FROM courses WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedCourse);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tuitionFees = resultSet.getInt("tuition");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuitionFees;
    }
    private int getFlatFeeForOtherOption(String selectedOption) {
        int flatFee = 0;

        // Replace these hardcoded values with actual fees for the "Other" options
        if (selectedOption.equals("ModuleR")) {
            flatFee = 4000;
        } else if (selectedOption.equals("Bus Pass")) {
            flatFee = 100;
        } else if (selectedOption.equals("Metro Card")) {
            flatFee = 200;
        }
        else
        {   flatFee=0;
        }

        return flatFee;
    }

    public static void main(String[] args){
        new Payment("jessical_miller567");
    }
}
