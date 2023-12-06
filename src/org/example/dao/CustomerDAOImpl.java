package org.example.dao;

import org.example.model.Customers;
import org.example.util.DatabaseConnection;
import org.example.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private Connection connection;

    public CustomerDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }
    @Override
    public void addCustomer(Customers customer) {
        String sql = "INSERT INTO Customers (FirstName, LastName, Email, Address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getAddress());

            DatabaseUtils.executeUpdate(preparedStatement, "adding a customer");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when adding a customer: ", e);
        }
    }

    @Override
    public Customers getCustomerById(int id) {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                return customer;
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting a customer by id: ", e);
        }
        return null;
    }


    @Override
    public List<Customers> getAllCustomers() {
        List<Customers> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when getting all customers: ", e);
        }
        return customers;
    }

    @Override
    public void updateCustomer(Customers customer) {
        String sql = "UPDATE Customers SET FirstName = ?, LastName = ?, Email = ?, Address = ? WHERE CustomerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setInt(5, customer.getCustomerID());

            DatabaseUtils.executeUpdate(preparedStatement, "updating a customer");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when updating a customer: ", e);
        }
    }


    @Override
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM Customers WHERE CustomerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            DatabaseUtils.executeUpdate(preparedStatement, "deleting a customer");
        } catch (SQLException e) {
            DatabaseUtils.logError("Error when deleting a customer: ", e);
        }
    }

}
