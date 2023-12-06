package org.example.dao;

import org.example.model.BookInfo;
import org.example.model.Books;

import java.util.List;

public interface BookDAO {
    void addBook(Books book);
    Books getBookById(int id);
    List<Books> getAllBooks();
    List<BookInfo> getAllBooksWithAuthorsAndOrders();
    void updateBook(Books book);
    void deleteBook(int id);

}


