package org.example.dao;

import org.example.model.OrderDetails;
import org.example.util.DatabaseConnection;
import org.example.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    private Connection connection;

    public OrderDetailsDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addOrderDetails(OrderDetails orderDetails) {
        String sql = "INSERT INTO OrderDetails (OrderID, BookID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderDetails.getOrderID());
            preparedStatement.setInt(2, orderDetails.getBookID());
            preparedStatement.setInt(3, orderDetails.getQuantity());

            DatabaseUtils.executeUpdate(preparedStatement, "adding order details");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when adding order details: ", e);
        }
    }


    @Override
    public OrderDetails getOrderDetailsById(int id) {
        String sql = "SELECT * FROM OrderDetails WHERE OrderDetailID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderDetailID(rs.getInt("OrderDetailID"));
                orderDetails.setOrderID(rs.getInt("OrderID"));
                orderDetails.setBookID(rs.getInt("BookID"));
                orderDetails.setQuantity(rs.getInt("Quantity"));
                return orderDetails;
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting order details by id: ", e);
        }
        return null;
    }


    @Override
    public List<OrderDetails> getAllOrderDetails() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderDetailID(rs.getInt("OrderDetailID"));
                orderDetails.setOrderID(rs.getInt("OrderID"));
                orderDetails.setBookID(rs.getInt("BookID"));
                orderDetails.setQuantity(rs.getInt("Quantity"));
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting all order details: ", e);
        }
        return orderDetailsList;
    }


    @Override
    public void updateOrderDetails(OrderDetails orderDetails) {
        String sql = "UPDATE OrderDetails SET OrderID = ?, BookID = ?, Quantity = ? WHERE OrderDetailID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderDetails.getOrderID());
            preparedStatement.setInt(2, orderDetails.getBookID());
            preparedStatement.setInt(3, orderDetails.getQuantity());
            preparedStatement.setInt(4, orderDetails.getOrderDetailID());

            DatabaseUtils.executeUpdate(preparedStatement, "updating order details");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when updating order details: ", e);
        }
    }


    @Override
    public void deleteOrderDetails(int id) {
        String sql = "DELETE FROM OrderDetails WHERE OrderDetailID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            DatabaseUtils.executeUpdate(preparedStatement, "deleting order details");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when deleting order details: ", e);
        }
    }

}
