package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;


    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {

                String url = "jdbc:postgresql://localhost:5432/bookStore";
                String user = "postgres";
                String password = "1234";

                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
    }
}
