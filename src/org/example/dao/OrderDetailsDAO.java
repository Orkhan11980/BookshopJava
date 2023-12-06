package org.example.dao;

import org.example.model.OrderDetails;

import java.util.List;

public interface OrderDetailsDAO {
    void addOrderDetails(OrderDetails orderDetails);
    OrderDetails getOrderDetailsById(int id);
    List<OrderDetails> getAllOrderDetails();
    void updateOrderDetails(OrderDetails orderDetails);
    void deleteOrderDetails(int id);
}
