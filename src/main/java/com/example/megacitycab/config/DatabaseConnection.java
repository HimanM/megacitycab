package com.example.megacitycab.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String SERVER_NAME = "Himan\\SQLEXPRESS";
    private static final String DATABASE_NAME = "MegaCityCab";
    private static final String USERNAME = "MegaCity";
    private static final String PASSWORD = "test";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            String url = "jdbc:sqlserver://" + SERVER_NAME + ";databaseName=" + DATABASE_NAME +
                    ";encrypt=true;trustServerCertificate=true;keepAlive=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            this.connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!");
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null || instance.connection == null || isConnectionClosed(instance.connection)) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        if (isConnectionClosed(getInstance().connection)) {
            instance = new DatabaseConnection();  // Force reconnection
        }
        return instance.connection;
    }

    private static boolean isConnectionClosed(Connection conn) {
        try {
            return conn == null || conn.isClosed() || !conn.isValid(2);  // Check validity within 2 seconds
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}
