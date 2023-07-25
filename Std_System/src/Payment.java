import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Payment {
    private JFrame frame;
    
    public Payment(){
        frame = new JFrame("Payment");

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new Payment();
    }
}