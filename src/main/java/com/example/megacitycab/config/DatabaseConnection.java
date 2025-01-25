package com.example.megacitycab.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String SERVER_NAME = "Himan\\SQLEXPRESS"; // Replace with your server name or IP address if different
    private static final String DATABASE_NAME = "MegaCityCab";
    private static final String USERNAME = "MegaCity";
    private static final String PASSWORD = "test";

    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;",
                SERVER_NAME, DATABASE_NAME, USERNAME, PASSWORD);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server Driver not found!");
            e.printStackTrace();
            return null;
        }

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Connection successful!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                // Use the connection here
            } else {
                System.out.println("Failed to get connection.");
            }
        }
    }
}