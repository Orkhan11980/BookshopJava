package org.example.dao;

import org.example.model.Authors;
import org.example.util.DatabaseConnection;
import org.example.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {
    private Connection connection;

    public AuthorDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Database connection could not be established.");
        }
    }

    @Override
    public void addAuthor(Authors author) {

        String sql = "INSERT INTO Authors (Name, Bio) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getBio());

            DatabaseUtils.executeUpdate(preparedStatement, "adding an author");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when adding an author: ", e);
        }
    }


    @Override
    public Authors getAuthorById(int id) {
        String sql = "SELECT * FROM Authors WHERE AuthorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Authors author = new Authors();
                author.setAuthorID(rs.getInt("AuthorID"));
                author.setName(rs.getString("Name"));
                author.setBio(rs.getString("Bio"));
                return author;
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting an author by id: ", e);
        }
        return null;
    }


    @Override
    public List<Authors> getAllAuthors() {
        List<Authors> authors = new ArrayList<>();
        String sql = "SELECT * FROM Authors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Authors author = new Authors();
                author.setAuthorID(rs.getInt("AuthorID"));
                author.setName(rs.getString("Name"));
                author.setBio(rs.getString("Bio"));
                authors.add(author);
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting all authors: ", e);
        }
        return authors;
    }

    @Override
    public void updateAuthor(Authors author) {
        String sql = "UPDATE Authors SET Name = ?, Bio = ? WHERE AuthorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getBio());
            preparedStatement.setInt(3, author.getAuthorID());

            DatabaseUtils.executeUpdate(preparedStatement, "updating an author");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when updating an author: ", e);
        }
    }


    @Override
    public void deleteAuthor(int id) {
        String sql = "DELETE FROM Authors WHERE AuthorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            DatabaseUtils.executeUpdate(preparedStatement, "deleting an author");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when deleting an author: ", e);
        }
    }

}
