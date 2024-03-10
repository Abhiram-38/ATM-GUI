package com.emailsender.sendemail;
// ATMFrame.java
import javax.swing.*;

import org.springframework.boot.SpringApplication;

import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.sql.PreparedStatement;


public class ATMFrame extends JFrame {
    private User user;
    
    static String email = User.getemail();
	static String subject="hiee";
	static String message=getOTP();
    
    public ATMFrame(User user) {
        this.user = user;
        

        setTitle("ATM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        
     // Create a JLabel to hold the background image
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\ANUP\\Downloads\\sendemail\\sendemail\\src\\main\\java\\iconsimg4.jpeg")); // Replace with the actual path to your image
        background.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        background.add(panel, BorderLayout.CENTER);
        setContentPane(background); // Set the content pane to the background label

        placeComponents(panel);

        setVisible(true);
    	
    	 
    }
	  public static String getOTP() {
	  Random rand = new Random();
	  String otp="";
	  otp += (char)(rand.nextInt(10)+'0');
	  otp += (char)(rand.nextInt(10)+'0');
	  otp += (char)(rand.nextInt(10)+'0');
	  otp += (char)(rand.nextInt(10)+'0');
	  otp += (char)(rand.nextInt(10)+'0');
	  otp += (char)(rand.nextInt(10)+'0');
	  return otp;
	}
	  public static String t1()
	  {
		  return message;
	  }
    private void handleWithdraw() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
        try {
            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive value.");
                return;
            }

            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds.");
                return;
            }

            // Update the user's balance in the database
            try (Connection connection = DatabaseHandler.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "UPDATE users SET balance = balance - ? WHERE id = ?")) {

                preparedStatement.setDouble(1, amount);
                preparedStatement.setInt(2, user.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0 ) {
                    if(user.getBalance()>=amount){
                    	
                    	SpringApplication.run(SendemailApplication.class);
                        String temp1=t1();
                        String otp1 = JOptionPane.showInputDialog(this, "Enter OTP sent to your mail id:");
                        if(otp1.compareTo(temp1)==0) {
	                        user.setBalance(user.getBalance() - amount);
	                        JOptionPane.showMessageDialog(this, "Withdrawal successful. New balance: $" + user.getBalance());
                        }
                        else {
                        	JOptionPane.showMessageDialog(this, "Incorrect OTP");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Funds are not sufficient");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Withdrawal failed. Please try again.");
                }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error processing withdrawal. Please try again.");
                }
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.");
            }
        }
        private void handleDeposit() {
            String amountStr = JOptionPane.showInputDialog(this, "Enter deposit amount:");
    
            try {
                double amount = Double.parseDouble(amountStr);
    
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive value.");
                    return;
                }
    
                // Update the user's balance in the database
                try (Connection connection = DatabaseHandler.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "UPDATE users SET balance = balance + ? WHERE id = ?")) {
    
                    preparedStatement.setDouble(1, amount);
                    preparedStatement.setInt(2, user.getId());
    
                    int rowsAffected = preparedStatement.executeUpdate();
                    
                    
                    
                    if (rowsAffected > 0) {
                        user.setBalance(user.getBalance() + amount);
                        JOptionPane.showMessageDialog(this, "Deposit successful. New balance: $" + user.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(this, "Deposit failed. Please try again.");
                    }
    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error processing deposit. Please try again.");
                }
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.");
            }
        }
        private void handleChangePin() {
            String newPin = JOptionPane.showInputDialog(this, "Enter new PIN:");
            //JOptionPane.showMessageDialog(this,newPin);
            try {
                // Update the user's PIN in the database
                try (Connection connection = DatabaseHandler.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "UPDATE users SET password = ? WHERE id = ?")) {
    
                    preparedStatement.setString(1, newPin);
                    preparedStatement.setInt(2, user.getId());
    
                    int rowsAffected = preparedStatement.executeUpdate();
    
                    if (rowsAffected > 0) {
                    	
                    	SpringApplication.run(SendemailApplication.class);
                        String temp1=t1();
                        String otp1 = JOptionPane.showInputDialog(this, "Enter OTP sent to your mail id:");
                        if(otp1.compareTo(temp1)==0) {
	                        // Directly modify the PIN without using a setPin method
	                        user.setPassword(newPin);
	                        JOptionPane.showMessageDialog(this, "PIN change successful.");
                        }else {
                        	JOptionPane.showMessageDialog(this, "Incorrect OTP");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "PIN change failed. Please try again.");
                    }
    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error processing PIN change. Please try again.");
                    JOptionPane.showMessageDialog(this, "Error processing PIN change. Details: " + ex.getMessage());
                }
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid PIN. Please enter a numeric value.");
            }
        }
    
    private void placeComponents(JPanel panel) {
  
        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getname());
        welcomeLabel.setBounds(140, 10, 300, 30); // Adjust the bounds as needed
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(20f));
        panel.add(welcomeLabel);
        //panel.add(welcomeLabel);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(60, 50, 150, 40);
        panel.add(withdrawButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(60, 125, 150, 40);
        panel.add(depositButton);

        JButton balanceButton = new JButton("Balance");
        balanceButton.setBounds(350, 50, 150, 40);
        panel.add(balanceButton);

        JButton pinChangeButton = new JButton("Pin Change");
        pinChangeButton.setBounds(350, 125, 150, 40);
        panel.add(pinChangeButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(145, 200, 250, 40);
        panel.add(exitButton);
        
        

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement withdraw functionality
                handleWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement deposit functionality
                handleDeposit();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display balance
                JOptionPane.showMessageDialog(ATMFrame.this, "Balance: $" + user.getBalance());
            }
        });

        pinChangeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement pin change functionality
                handleChangePin();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMFrame(new User(1, "user1", "pass1","abhi",1000.00,"iamvsanup@gmail.com")));
    }
}


