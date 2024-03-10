package com.emailsender.sendemail;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/atm1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "peterparker17";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
