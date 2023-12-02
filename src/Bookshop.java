import javax.lang.model.element.Name;
import java.math.BigDecimal;
import java.sql.*;


public class Bookshop {

    private static final String URL = "jdbc:postgresql://localhost:5432/bookStore";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection connect() {
        Connection conn = null;
        try {

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to the PostgreSQL server failed.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void insertBook(int authorId, String title, String isbn, BigDecimal price, int stock){
        String sql = "INSERT INTO Books (AuthorId, Title, ISBN, Price, Stock) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, authorId);
            pstm.setString(2, title);
            pstm.setString(3, isbn);
            pstm.setBigDecimal(4, price);
            pstm.setInt(5,stock);

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                System.out.println("A new book has been inserted");
            }
        }catch (SQLException e){
            System.out.println("Error while inserting the book");
            e.printStackTrace();
        }


    }

    public static void insertAuthors(String name, String bio){
        String sql = "INSERT INTO Authors (Name, Bio) VALUES(?, ?)";

        try(Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, name);
            pstm.setString(2,bio);
       if(pstm.executeUpdate()>0){
           System.out.println("A new author has been inserted");
       }
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

       try (Connection conn = connect();
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

        try(Connection conn = connect();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1,newAuthorID);
            pstm.setString(2, newTitle);
            pstm.setString(3, newISBN);
            pstm.setBigDecimal(4, newPrice);
            pstm.setInt(5, newStock);
            pstm.setInt(6,bookId);

             if(pstm.executeUpdate()>0){
                 System.out.println("Book details updated successfully.");
             } else {
                 System.out.println("No book was updated. Please check the Book ID.");
             }


        }catch (SQLException e){
            System.out.println("Error while updating book details");
            e.printStackTrace();
        }


   }















}