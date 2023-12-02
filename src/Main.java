import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
//        Connection connection = Bookshop.connect();
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

        Bookshop.updateBookDetails(1,1, "Updated Title", "12345678923", new BigDecimal("39.99"), 99);

//       Bookshop.getAllBookDeatils();
//        Bookshop.insertAuthors("New Author", "New Bio");
//        Bookshop.insertBook(1, "New Book", "1235790123", new BigDecimal("19.99"), 50);
    }
}