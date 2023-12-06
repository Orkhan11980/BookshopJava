package org.example.dao;
import org.example.model.OrderDetails;
import org.example.model.Orders;

import java.sql.SQLException;
import java.util.List;
public interface OrderDAO {
        void addOrder(Orders order, List<OrderDetails> orderDetailsList) throws SQLException;

        Orders getOrderById(int id);
        List<Orders> getAllOrders();
        void updateOrder(Orders order);
        void deleteOrder(int id);
    }



