package org.example.model;

public class BookInfo {
    private int bookID;
    private String title;
    private double price;
    private int stock;


    private int authorID;
    private String authorName;
    private int totalQuantity;
    private String latestStatus;





    public BookInfo() {}

    public BookInfo(int bookID, String title, double price, int stock, int authorID, String authorName, int totalQuantity, String latestStatus) {
        this.bookID = bookID;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.authorID = authorID;
        this.authorName = authorName;
        this.totalQuantity = totalQuantity;
        this.latestStatus = latestStatus;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getLatestStatus() {
        return latestStatus;
    }

    public void setLatestStatus(String latestStatus) {
        this.latestStatus = latestStatus;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", authorID=" + authorID +
                ", authorName='" + authorName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", latestStatus='" + latestStatus + '\'' +
                '}';
    }
}
