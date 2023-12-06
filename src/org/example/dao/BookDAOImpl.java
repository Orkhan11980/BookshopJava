package org.example.dao;

import org.example.model.BookInfo;
import org.example.model.Books;
import org.example.util.DatabaseConnection;
import org.example.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDAOImpl implements BookDAO {

    private Connection connection;

    public BookDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }


    @Override
    public void addBook(Books book) {
        String sql = "INSERT INTO Books (AuthorID, Title, ISBN, Price, Stock) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, book.getAuthorID());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getStock());

            DatabaseUtils.executeUpdate(preparedStatement, "adding a book");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when adding a book: ", e);
        }
    }

    @Override
    public Books getBookById(int id) {
        String sql = "SELECT * FROM Books WHERE BookID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Books book = new Books();
                book.setBookID(rs.getInt("BookID"));
                book.setAuthorID(rs.getInt("AuthorID"));
                book.setTitle(rs.getString("Title"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPrice(rs.getDouble("Price"));
                book.setStock(rs.getInt("Stock"));
                return book;
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting a book by id: ", e);

        }
        return null;
    }




    @Override
    public List<Books> getAllBooks() {
        List<Books> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Books book = new Books();
                book.setBookID(rs.getInt("BookID"));
                book.setAuthorID(rs.getInt("AuthorID"));
                book.setTitle(rs.getString("Title"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPrice(rs.getDouble("Price"));
                book.setStock(rs.getInt("Stock"));
                books.add(book);
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting all books: ", e);

        }
        return books;
    }


    public List<BookInfo> getAllBooksWithAuthorsAndOrders() {
        List<BookInfo> bookInfos = new ArrayList<>();
        String sql = "SELECT b.*, a.AuthorID, a.Name AS AuthorName, SUM(od.Quantity) AS TotalQuantity, MAX(o.Status) AS LatestStatus " +
                "FROM Books b " +
                "JOIN Authors a ON b.AuthorID = a.AuthorID " +
                "LEFT JOIN OrderDetails od ON b.BookID = od.BookID " +
                "LEFT JOIN Orders o ON od.OrderID = o.OrderID " +
                "GROUP BY b.BookID, a.Name, a.AuthorID " +
                "ORDER BY b.BookID ASC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setBookID(rs.getInt("BookID"));
                bookInfo.setTitle(rs.getString("Title"));
                bookInfo.setPrice(rs.getDouble("Price"));
                bookInfo.setStock(rs.getInt("Stock"));
                bookInfo.setBookID(rs.getInt("AuthorID"));
                bookInfo.setAuthorName(rs.getString("AuthorName"));
                bookInfo.setTotalQuantity(rs.getInt("TotalQuantity"));
                bookInfo.setLatestStatus(rs.getString("LatestStatus"));
                bookInfos.add(bookInfo);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bookInfos;
    }

    @Override
    public void updateBook(Books book) {
        String sql = "UPDATE Books SET AuthorID = ?, Title = ?, ISBN = ?, Price = ?, Stock = ? WHERE BookID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, book.getAuthorID());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getStock());
            preparedStatement.setInt(6, book.getBookID());
            preparedStatement.executeUpdate();

            DatabaseUtils.executeUpdate(preparedStatement, "updating a book");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when updating a book: ", e);

        }
    }


    @Override
    public void deleteBook(int id) {
        String sql = "DELETE FROM Books WHERE BookID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);


            DatabaseUtils.executeUpdate(preparedStatement, "deleting a book");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when deleting a book: ", e);
        }
    }



}


