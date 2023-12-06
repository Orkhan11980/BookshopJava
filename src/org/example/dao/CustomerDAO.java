package org.example.dao;


import org.example.model.Customers;

import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customers customer);
    Customers getCustomerById(int id);
    List<Customers> getAllCustomers();
    void updateCustomer(Customers customer);
    void deleteCustomer(int id);
}
