package com.example.megacitycab.dao;

import com.example.megacitycab.model.User;

import java.util.List;

public interface UserDAO {
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    User getUserByEmail(String email);
    User getUserById(int customerId);
}
