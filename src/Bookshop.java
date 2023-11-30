import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Bookshop {

    private static final String URL = "jdbc:postgresql://localhost:5432/bookStore";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static Connection connect() {
        Connection conn = null;
        try {

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to the PostgreSQL server failed.");
            e.printStackTrace();
        }
        return conn;
    }

}