package com.example.megacitycab.service;

import com.example.megacitycab.dao.Interfaces.UserDAO;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.exceptions.CustomerException;
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
    public int registerCustomer(User customer) throws CustomerException {
        int userId = -1;
        try {
            if (customer == null) {
                throw new CustomerException("Customer data cannot be null");
            }
            if (UserDAO.getUserByEmail(customer.getEmail()) != null) {
                throw new CustomerException("Email is already registered");
            }
            if (UserDAO.getUserByNic(customer.getNic()) != null){
                throw new CustomerException("NIC is already registered");
            }
            userId = UserDAO.addUser(customer);
        } catch (Exception e) {
            throw new CustomerException("Error while registering customer", e);
        }
        return userId;
    }

    // Update customer details
    public boolean updateCustomer(User updatedCustomer) throws CustomerException {
        try {
            if (updatedCustomer == null) {
                throw new CustomerException("Updated customer data cannot be null");
            }
            return (UserDAO.updateUser(updatedCustomer));
        } catch (Exception e) {
            throw new CustomerException("Error while updating customer details", e);
        }
    }

    // Fetch a customer by ID
    public User getCustomerById(int customerId) throws CustomerException {
        try {
            User customer = UserDAO.getUserById(customerId);
            if (customer == null) {
                throw new CustomerException("Customer not found");
            }
            return customer;
        } catch (Exception e) {
            throw new CustomerException("Error while fetching customer details", e);
        }
    }

    // Fetch a customer by email
    public User getCustomerByEmail(String email) throws CustomerException {
        try {
            User customer = UserDAO.getUserByEmail(email);
            if (customer == null) {
                throw new CustomerException("Customer not found");
            }
            return customer;
        } catch (Exception e) {
            throw new CustomerException("Error while fetching customer details by email", e);
        }
    }

    // Get all customers
    public List<User> getAllCustomers() throws CustomerException {
        try {
            return UserDAO.getAllUsers();
        } catch (Exception e) {
            throw new CustomerException("Error while fetching all customers", e);
        }
    }

    // Delete a customer
    public void deleteCustomer(int customerId) throws CustomerException {
        try {
            UserDAO.deleteUser(customerId);
        } catch (Exception e) {
            throw new CustomerException("Error while deleting customer", e);
        }
    }

    public boolean isEmailAlreadyRegistered(String email) throws CustomerException {
        try {
            return UserDAO.getUserByEmail(email) != null;
        }
        catch (Exception e) {
            throw new CustomerException("Error while checking email registration", e);
        }
    }

    public boolean isNicAlreadyRegistered(String nic) throws CustomerException {
        try {
            return UserDAO.getUserByNic(nic) != null;
        }
        catch (Exception e) {
            throw new CustomerException("Error while checking NIC registration", e);
        }
    }
}
