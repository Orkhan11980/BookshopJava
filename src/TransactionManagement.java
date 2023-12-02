import java.sql.*;
import java.text.DateFormat;

public class TransactionManagement {

      public static void placeOrder(int customerId, int bookId, int quantity){

          Connection conn = null;

          try {
              conn = DatabaseConnectionManager.getConnection();
              conn.setAutoCommit(false);

              //to check stock
              if(!isStockAvailable(conn, bookId, quantity)){
                  throw new SQLException("Not enough stock available");
              }

              //updating Books table
              updateBookStock(conn,bookId,quantity);


              //insert the order into Orders table
              int orderId = insertOrder(conn, customerId);



              // insert into insertOrderDetails
              insertOrderDetails(conn,orderId,bookId,quantity);


              conn.commit();
              System.out.println("Order placed successfully");

          } catch (SQLException e) {
              //rollback
              if(conn!=null){
                  try {
                      conn.rollback();
                  }catch (SQLException ex){
                      ex.printStackTrace();
                  }
              }
              e.printStackTrace();
              System.out.println("Order placement failed");
          }finally {
              //close the connection
              if(conn != null){
                  try {
                      conn.setAutoCommit(true);
                      conn.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          }

      }


      private static boolean isStockAvailable(Connection conn, int bookId, int quantity) throws SQLException{
          String sql = "SELECT Stock FROM Books Where BookId=?";
          try(PreparedStatement pstm = conn.prepareStatement(sql)) {
              pstm.setInt(1,bookId);
              ResultSet rs = pstm.executeQuery();
              if(rs.next()){
                  int stock = rs.getInt("Stock");
                  return stock >=quantity;
              }
              return false;
          }

      }
    private static void updateBookStock(Connection conn, int bookId, int quantity) throws SQLException {
        String sql = "UPDATE Books SET Stock = Stock - ? WHERE BookID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, bookId);
            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Updating stock failed, no rows affected.");
            }
        }
    }
    private static int insertOrder(Connection conn, int customerId) throws SQLException {
        String sql = "INSERT INTO Orders (CustomerID, orderDate, status) VALUES (?,?,?) RETURNING OrderID";


        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String status = "Processing";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setTimestamp(2,  orderDate);
            pstmt.setString(3, status);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("OrderID");
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
    }

    private static void insertOrderDetails(Connection conn, int orderId, int bookId, int quantity) throws SQLException {
        String sql = "INSERT INTO OrderDetails (OrderID, BookID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, quantity);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting order details failed, no rows affected.");
            }
        }
    }

}


