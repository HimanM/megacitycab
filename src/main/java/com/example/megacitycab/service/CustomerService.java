package com.example.megacitycab.service;

import com.example.megacitycab.dao.UserDAO;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.exceptions.UserException;
import com.example.megacitycab.model.User;

import java.util.List;

public class CustomerService {
    private final UserDAO UserDAO;

    public CustomerService() {
        this.UserDAO = new UserDAOImpl(); // Use dependency injection if needed
    }

    public CustomerService(UserDAO UserDAO) {
        this.UserDAO = UserDAO;
    }

    // Register a new customer
    public boolean registerCustomer(User customer) throws UserException {
        boolean b = false;
        try {
            if (customer == null) {
                throw new UserException("Customer data cannot be null");
            }
            if (UserDAO.getUserByEmail(customer.getEmail()) != null) {
                throw new UserException("Email is already registered");
            }
            UserDAO.addUser(customer);
            b = true;
        } catch (Exception e) {
            throw new UserException("Error while registering customer", e);
        }
        return b;
    }

    // Update customer details
    public void updateCustomer(User updatedCustomer) throws UserException {
        try {
            if (updatedCustomer == null) {
                throw new UserException("Updated customer data cannot be null");
            }

            UserDAO.updateUser(updatedCustomer);
        } catch (Exception e) {
            throw new UserException("Error while updating customer details", e);
        }
    }

    // Fetch a customer by ID
    public User getCustomerById(int customerId) throws UserException {
        try {
            User customer = UserDAO.getUserById(customerId);
            if (customer == null) {
                throw new UserException("Customer not found");
            }
            return customer;
        } catch (Exception e) {
            throw new UserException("Error while fetching customer details", e);
        }
    }

    // Fetch a customer by email
    public User getCustomerByEmail(String email) throws UserException {
        try {
            User customer = UserDAO.getUserByEmail(email);
            if (customer == null) {
                throw new UserException("Customer not found");
            }
            return customer;
        } catch (Exception e) {
            throw new UserException("Error while fetching customer details by email", e);
        }
    }

    // Get all customers
    public List<User> getAllCustomers() throws UserException {
        try {
            return UserDAO.getAllUsers();
        } catch (Exception e) {
            throw new UserException("Error while fetching all customers", e);
        }
    }

    // Delete a customer
    public void deleteCustomer(int customerId) throws UserException {
        try {
            UserDAO.deleteUser(customerId);
        } catch (Exception e) {
            throw new UserException("Error while deleting customer", e);
        }
    }

    public boolean isEmailAlreadyRegistered(String email) {
        return UserDAO.getUserByEmail(email) != null;
    }
}
