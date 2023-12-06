package org.example.model;

import java.sql.Timestamp;

public class Orders {
    private int OrderID;
    private int customerID;
    private Timestamp orderDate;
    private String status;

    public Orders(){}

    public Orders(int orderID, int customerID, Timestamp orderDate, String status) {
        OrderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "OrderID=" + OrderID +
                ", customerID=" + customerID +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                '}';
    }
}
