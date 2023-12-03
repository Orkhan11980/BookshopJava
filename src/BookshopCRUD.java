import java.math.BigDecimal;
import java.sql.*;

import static java.sql.DriverManager.getConnection;


public class BookshopCRUD {



    public static void insertBook(int authorId, String title, String isbn, BigDecimal price, int stock){
        String sql = "INSERT INTO Books (AuthorId, Title, ISBN, Price, Stock) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, authorId);
            pstm.setString(2, title);
            pstm.setString(3, isbn);
            pstm.setBigDecimal(4, price);
            pstm.setInt(5,stock);

            executeUpdate(pstm,"inserting the book");

        }catch (SQLException e){
            System.out.println("Error while inserting the book");
            e.printStackTrace();
        }


    }

    public static void insertAuthors(String name, String bio){
        String sql = "INSERT INTO Authors (Name, Bio) VALUES(?, ?)";

        try(Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, name);
            pstm.setString(2,bio);

            executeUpdate(pstm, "inserting an Author");

        }catch (SQLException e){
            System.out.println("Error while inserting the Author");
            e.printStackTrace();
        }

    }
   public static void getAllBookDeatils() {
       String sql = "SELECT b.*, a.Name AS AuthorName, od.OrderID, od.Quantity, o.Status " +
               "FROM Books b " +
               "JOIN Authors a oN b.AuthorID = a.AuthorID " +
               "LEFT JOIN OrderDetails od ON b.BookID = od.BookID " +
               "LEFT JOIN Orders o ON od.OrderID = o.OrderID";

       try (Connection conn = DatabaseConnectionManager.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql)) {


           while (rs.next()){

               int bookId = rs.getInt("BookID");
               int authorId = rs.getInt("AuthorID");
               String title = rs.getString("Title");
               String isbn = rs.getString("ISBN");
               BigDecimal price = rs.getBigDecimal("Price");
               int stock = rs.getInt("Stock");
               String authorName = rs.getString("AuthorName");
               int orderId = rs.getInt("OrderID");
               int quantity = rs.getInt("Quantity");
               String status = rs.getString("Status");

               String output = String.format("Book ID: %d, Author ID: %d, Title: '%s', ISBN: '%s', Price: %s, Stock: %d, Author: '%s', Order ID: %d, Quantity: %d, Status: %s",
                       bookId, authorId, title, isbn, price, stock, authorName, orderId, quantity, status);
               System.out.println(output);
           }

       } catch (SQLException e) {
           System.out.println("Error while fetching book details.");
           e.printStackTrace();
       }

   }


   public static void updateBookDetails(int bookId, int newAuthorID,String newTitle, String newISBN, BigDecimal newPrice, int newStock){

        String sql = "UPDATE Books SET AuthorId = ? ,Title = ?, ISBN=?, Price=?, Stock=? WHERE BookID= ?";

        try(Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1,newAuthorID);
            pstm.setString(2, newTitle);
            pstm.setString(3, newISBN);
            pstm.setBigDecimal(4, newPrice);
            pstm.setInt(5, newStock);
            pstm.setInt(6,bookId);

            executeUpdate(pstm, "updating the book");


        }catch (SQLException e){
            System.out.println("Error while updating book details");
            e.printStackTrace();
        }


   }

public static void removeBook(int bookId){
        String sql = "DELETE FROM Books WHERE BookID=?";

        try(Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1,bookId);

           executeUpdate(pstm,"removing the book");

        }catch (SQLException e) {
        System.out.println("Error while removing book: " + e.getMessage());
        e.printStackTrace();
    }
 }

    private static void executeUpdate(PreparedStatement pstm, String action) throws SQLException {
        int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Operation of " + action + " was successful.");
        } else {
            System.out.println("No rows were affected during " + action + ". Please check your inputs.");
        }
    }
}