package org.example.dao;

import org.example.model.OrderDetails;
import org.example.model.Orders;
import org.example.util.DatabaseConnection;
import org.example.util.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{
    private Connection connection;


    public OrderDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Database connection was not established.");
        }
    }


    @Override
    public void addOrder(Orders order, List<OrderDetails> orderDetailsList) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert Order
            String orderSql = "INSERT INTO Orders (CustomerID, OrderDate, Status) VALUES (?, ?, ?)";
            int orderId = -1;
            try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getCustomerID());
                orderStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Use current time for order date
                orderStmt.setString(3, order.getStatus());
                orderStmt.executeUpdate();

                try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated OrderID.");
                    }
                }
            }


            for (OrderDetails od : orderDetailsList) {
                updateBookStock(conn, od.getBookID(), od.getQuantity()); // Update stock

                String detailsSql = "INSERT INTO OrderDetails (OrderID, BookID, Quantity) VALUES (?, ?, ?)";
                try (PreparedStatement detailsStmt = conn.prepareStatement(detailsSql)) {
                    detailsStmt.setInt(1, orderId);
                    detailsStmt.setInt(2, od.getBookID());
                    detailsStmt.setInt(3, od.getQuantity());
                    detailsStmt.executeUpdate();
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void updateBookStock(Connection conn, int bookId, int quantity) throws SQLException {
        // Check current stock
        String checkStockSql = "SELECT Stock FROM Books WHERE BookID = ?";
        try (PreparedStatement checkStockStmt = conn.prepareStatement(checkStockSql)) {
            checkStockStmt.setInt(1, bookId);
            try (ResultSet rs = checkStockStmt.executeQuery()) {
                if (rs.next()) {
                    int currentStock = rs.getInt("Stock");
                    if (currentStock >= quantity) {
                        // Update stock
                        String updateStockSql = "UPDATE Books SET Stock = Stock - ? WHERE BookID = ?";
                        try (PreparedStatement updateStockStmt = conn.prepareStatement(updateStockSql)) {
                            updateStockStmt.setInt(1, quantity);
                            updateStockStmt.setInt(2, bookId);
                            int affectedRows = updateStockStmt.executeUpdate();
                            if (affectedRows == 0) {
                                throw new SQLException("Updating stock failed, no rows affected.");
                            }
                        }
                    } else {
                        throw new SQLException("Insufficient stock for BookID: " + bookId);
                    }
                } else {
                    throw new SQLException("Book not found for BookID: " + bookId);
                }
            }
        }
    }





    @Override
    public Orders getOrderById(int id) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Orders order = new Orders();
                order.setOrderID(rs.getInt("OrderID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setStatus(rs.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting an order by id: ", e);
        }
        return null;
    }


    @Override
    public List<Orders> getAllOrders() {
        List<Orders> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderID(rs.getInt("OrderID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setStatus(rs.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting all orders: ", e);
        }
        return orders;
    }


    @Override
    public void updateOrder(Orders order) {
        String sql = "UPDATE Orders SET CustomerID = ?, OrderDate = ?, Status = ? WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, order.getCustomerID());
            preparedStatement.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            preparedStatement.setString(3, order.getStatus());
            preparedStatement.setInt(4, order.getOrderID());

            DatabaseUtils.executeUpdate(preparedStatement, "updating an order");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when updating an order: ", e);
        }
    }


    @Override
    public void deleteOrder(int id) {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            DatabaseUtils.executeUpdate(preparedStatement, "deleting an order");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when deleting an order: ", e);
        }
    }

}
