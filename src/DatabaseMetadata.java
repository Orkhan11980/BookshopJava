import java.sql.*;

public class DatabaseMetadata{

    public static void displayTableNames() {
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
                System.out.println("Table Names:");
                while (rs.next()) {
                    System.out.println(rs.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayColumnDetails(String tableName) {
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
                System.out.println("Column Details for " + tableName + ":");
                while (rs.next()) {
                    System.out.println("Column Name: " + rs.getString("COLUMN_NAME"));
                    System.out.println("Column Type: " + rs.getString("TYPE_NAME"));
                    System.out.println("Column Size: " + rs.getInt("COLUMN_SIZE"));
                    System.out.println("----------------------------------------------");


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayPrimaryKeys(String tableName) {
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getPrimaryKeys(null, null, tableName)) {
                System.out.println("Primary Keys for " + tableName + ":");
                while (rs.next()) {
                    System.out.println("Column Name: " + rs.getString("COLUMN_NAME"));
                    System.out.println("Primary Key Name: " + rs.getString("PK_NAME"));
                    System.out.println("---------------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayForeignKeys(String tableName) {
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getImportedKeys(null, null, tableName)) {
                System.out.println("Foreign Keys for " + tableName + ":");
                while (rs.next()) {
                    System.out.println("Column Name: " + rs.getString("FKCOLUMN_NAME"));
                    System.out.println("Foreign Key Name: " + rs.getString("FK_NAME"));
                    System.out.println("Referenced Table: " + rs.getString("PKTABLE_NAME"));
                    System.out.println("Referenced Column: " + rs.getString("PKCOLUMN_NAME"));
                    System.out.println("----------------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
