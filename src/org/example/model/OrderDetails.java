package org.example.model;

public class OrderDetails {
    private int orderDetailID;
    private int orderID;
    private int bookID;
    private int quantity;

    public OrderDetails() {}

    public OrderDetails(int orderDetailID, int orderID, int bookID, int quantity) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderDetailID=" + orderDetailID +
                ", orderID=" + orderID +
                ", bookID=" + bookID +
                ", quantity=" + quantity +
                '}';
    }
}