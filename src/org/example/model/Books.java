package org.example.model;

public class Books {
    private int bookID;
    private int authorID;
    private String title;
    private String isbn;
    private double price;
    private int stock;


    public Books() {
    }

    public Books(int bookID, int authorID, String title, String isbn, double price, int stock) {
        this.bookID = bookID;
        this.authorID = authorID;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    @Override
    public String toString() {
        return "Books{" +
                "bookID=" + bookID +
                ", authorID=" + authorID +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
