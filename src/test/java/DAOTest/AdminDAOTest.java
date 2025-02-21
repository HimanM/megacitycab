package DAOTest;

import com.example.megacitycab.dao.Interfaces.AdminDAO;
import com.example.megacitycab.dao.AdminDAOImpl;
import com.example.megacitycab.model.Admin;

import java.util.List;

public class AdminDAOTest {
    public static void main(String[] args) {
        AdminDAO adminDAO = new AdminDAOImpl();

        // Test createAdmin
        Admin newAdmin = new Admin(100, "admin_test", "testpassword", "Test Admin","Test Address","1456b6789V", "0771234567");
        boolean isCreated = adminDAO.createAdmin(newAdmin);
        System.out.println("Admin created: " + isCreated);

        // Test getAdminByUsername
        Admin admin = adminDAO.getAdminByUsername("admin_test");
        System.out.println("Retrieved Admin: " + admin.getName());

        // Test getAllAdmins
        List<Admin> allAdmins = adminDAO.getAllAdmins();
        System.out.println("All Admins: ");
        allAdmins.forEach(a -> System.out.println(a.getName()));

        // Test updateAdmin
        admin.setName("Updated Admin");
        admin.setPassword("updatedpassword");
        boolean isUpdated = adminDAO.updateAdmin(admin);
        System.out.println("Admin updated: " + isUpdated);

        // Test deleteAdmin
        boolean isDeleted = adminDAO.deleteAdmin(admin.getId());
        System.out.println("Admin deleted: " + isDeleted);
    }
}
