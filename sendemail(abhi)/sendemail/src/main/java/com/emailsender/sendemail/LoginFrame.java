package com.emailsender.sendemail;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener{
    
    JTextField tfusername;
    private JPasswordField tfpassword;

    public LoginFrame() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);    
        
        JLabel title1 = new JLabel("LOGIN PAGE");
        title1.setBounds(125, 20, 100, 30);
        add(title1);
        
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 70, 100, 30);
        add(lblusername);
        
        tfusername = new JTextField();
        tfusername.setBounds(150, 70, 150, 30);
        add(tfusername);
        
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 120, 100, 30);
        add(lblpassword);
        
        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 120, 150, 30);
        add(tfpassword);
        
        JButton login = new JButton("LOGIN");
        login.setBounds(150, 180, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icons/img4.jpeg"));
        Image img = backgroundImage.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon imageIcon = new ImageIcon(img);

        JLabel background = new JLabel(imageIcon);
        background.setBounds(0, 0, 600, 300);
        add(background);
        
        setSize(600, 300);
        setLocation(450, 200);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String username = tfusername.getText();
            String password = tfpassword.getText();
            
            try (Connection connection = DatabaseHandler.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?")) 
            {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Successful login
                    int userId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double balance = resultSet.getDouble("balance");
                    String email=resultSet.getString("email");
                    dispose(); // Close the login frame

                    // Open the main ATM frame
                    new ATMFrame(new User(userId, username, password, name, balance,email));
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                    }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
            }
        }catch(Exception e){

        }
    }
    public static void main(String[] args) {
        new LoginFrame();
    }
}
