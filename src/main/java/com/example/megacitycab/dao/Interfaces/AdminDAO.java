package com.example.megacitycab.dao.Interfaces;

import com.example.megacitycab.model.Admin;

import java.util.List;

public interface AdminDAO {
    Admin getAdminByUsername(String username);
    List<Admin> getAllAdmins();
    boolean createAdmin(Admin admin);
    boolean updateAdmin(Admin admin);
    boolean deleteAdmin(int id);
}
