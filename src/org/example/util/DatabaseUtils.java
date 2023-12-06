package org.example.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtils {

    public static void executeUpdate(PreparedStatement pstm, String action) throws SQLException {
        try {
            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Operation of " + action + " was successful.");
            } else {
                System.out.println("No rows were affected during " + action + ". Please check your inputs.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error during " + action + ": " + e.getMessage(), e);
        }
    }

    public static void logError(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
