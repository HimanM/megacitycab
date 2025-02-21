package com.example.megacitycab.service;


import com.example.megacitycab.dao.Interfaces.AdminDAO;
import com.example.megacitycab.dao.AdminDAOImpl;
import com.example.megacitycab.exceptions.AdminException;
import com.example.megacitycab.model.Admin;

import java.util.List;
public class AdminService {
    private final AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAOImpl(); // Using AdminDAOImpl by default
    }

    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO; // Allow dependency injection if needed
    }

    // Add a new admin
    public void createAdmin(Admin admin) throws AdminException {
        try {
            if (admin == null) {
                throw new AdminException("Admin data cannot be null");
            }
            if (adminDAO.getAdminByUsername(admin.getUsername()) != null) {
                throw new AdminException("Admin with the given username already exists");
            }
            adminDAO.createAdmin(admin);
        } catch (Exception e) {
            throw new AdminException("Error while creating admin", e);
        }
    }

    // Update an existing admin
    public void updateAdmin(Admin admin) throws AdminException {
        try {
            if (admin == null) {
                throw new AdminException("Admin data cannot be null");
            }
            adminDAO.updateAdmin(admin);
        } catch (Exception e) {
            throw new AdminException("Error while updating admin", e);
        }
    }

    // Delete an admin by ID
    public boolean deleteAdmin(int ID) throws AdminException {
        try {
            return adminDAO.deleteAdmin(ID);
        } catch (Exception e) {
            throw new AdminException("Error while deleting admin", e);
        }
    }

    // Get admin by username
    public Admin getAdminByUsername(String username) throws AdminException {
        try {
            return adminDAO.getAdminByUsername(username);
        } catch (Exception e) {
            throw new AdminException("Error while fetching admin details", e);
        }
    }

    // Get all admins
    public List<Admin> getAllAdmins() throws AdminException {
        try {
            return adminDAO.getAllAdmins();
        } catch (Exception e) {
            throw new AdminException("Error while fetching admin list", e);
        }
    }
}

