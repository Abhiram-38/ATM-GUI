package com.emailsender.sendemail;
// User.java
public class User {
    private int id;
    private static String username;
    private String password;
    private static String name;
    private double balance;
    private static String email;
    //private String pin;

    public User(int id, String username, String password, String name,double balance,String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.name=name;
        this.email=email;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public static String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static String getname() {
        return name;
    }
    
    public static String getemail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void setPassword(String password) {
        this.password= password;
    }
}
