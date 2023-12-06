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
        PreparedStatement orderStmt = null;
        PreparedStatement bookStmt = null;
        PreparedStatement insertOrderDetailsStmt = null;
        try {
            connection.setAutoCommit(false); // Start transaction

            // Insert Order
            String orderSql = "INSERT INTO Orders (CustomerID, OrderDate, Status) VALUES (?, ?, ?)";
            orderStmt = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getCustomerID());
            orderStmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            orderStmt.setString(3, order.getStatus());
            DatabaseUtils.executeUpdate(orderStmt, "adding an order");


            // Retrieve generated order ID
            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                order.setOrderID(orderId);
            }

            // Update Book Stock and Insert Order Details
            String bookSql = "UPDATE Books SET Stock = Stock - ? WHERE BookID = ? AND Stock >= ?";
            bookStmt = connection.prepareStatement(bookSql);
            String insertOrderDetailsSql = "INSERT INTO OrderDetails (OrderID, BookID, Quantity) VALUES (?, ?, ?)";
            insertOrderDetailsStmt = connection.prepareStatement(insertOrderDetailsSql);

            for (OrderDetails od : orderDetailsList) {
                // Update Book Stock
                bookStmt.setInt(1, od.getQuantity());
                bookStmt.setInt(2, od.getBookID());
                bookStmt.setInt(3, od.getQuantity());
                DatabaseUtils.executeUpdate(bookStmt, "updating book stock for BookID: " + od.getBookID());


                // Insert into OrderDetails
                insertOrderDetailsStmt.setInt(1, order.getOrderID());
                insertOrderDetailsStmt.setInt(2, od.getBookID());
                insertOrderDetailsStmt.setInt(3, od.getQuantity());
                DatabaseUtils.executeUpdate(insertOrderDetailsStmt, "adding order detail");

            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            DatabaseUtils.logError("Error in transaction: ", e);
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    DatabaseUtils.logError("Error while rolling back transaction: ", ex);
                }
            }
        } finally {
            try {
                if (orderStmt != null) orderStmt.close();
                if (bookStmt != null) bookStmt.close();
                if (insertOrderDetailsStmt != null) insertOrderDetailsStmt.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset auto-commit
                    connection.close();
                }
            } catch (SQLException e) {
                DatabaseUtils.logError("Error closing resources: ", e);
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
