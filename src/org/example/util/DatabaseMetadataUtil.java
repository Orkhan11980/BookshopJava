package org.example.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMetadataUtil {

    private Connection connection;

    public DatabaseMetadataUtil(Connection connection) {
        this.connection = connection;
    }

    public void listAllTables() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (tables.next()) {
            System.out.println("Table: " + tables.getString("TABLE_NAME"));
            System.out.println("Type: " + tables.getString("TABLE_TYPE"));
        }
        tables.close();
    }

    public void listTableColumns(String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);
        while (columns.next()) {
            System.out.println("Column: " + columns.getString("COLUMN_NAME"));
            System.out.println("Type: " + columns.getString("TYPE_NAME"));
            System.out.println("Size: " + columns.getString("COLUMN_SIZE"));
            // Other column details can be retrieved here
        }
        columns.close();
    }

    public void listPrimaryKeys(String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
        while (primaryKeys.next()) {
            System.out.println("Primary Key: " + primaryKeys.getString("COLUMN_NAME"));
        }
        primaryKeys.close();
    }

    public void listForeignKeys(String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
        while (foreignKeys.next()) {
            System.out.println("Foreign Key: " + foreignKeys.getString("FKCOLUMN_NAME"));
            System.out.println("References Table: " + foreignKeys.getString("PKTABLE_NAME"));
            System.out.println("References Column: " + foreignKeys.getString("PKCOLUMN_NAME"));
        }
        foreignKeys.close();
    }
}
