
import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class TestDatabase {
    public static void main(String[] args) {
        testConnection();
        testUserDAO();
    }

    // Test database connection
    private static void testConnection() {
        System.out.println("Testing Database Connection...");
        try {
            if (DatabaseConnection.getConnection() != null) {
                System.out.println("Connection successful!");
            }
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    // Test UserDAOImpl methods
    private static void testUserDAO() {
        System.out.println("Testing UserDAO...");
        UserDAOImpl userDAO = new UserDAOImpl();

        // 1. Add a new user
        User newUser = new User(
                0, // ID is auto-incremented
                "john_doe",
                "password123", // In a real app, hash passwords before saving
                "John Doe",
                "123 Main Street, Colombo",
                "987654321V",
                "0779876543",
                "CUSTOMER",
                LocalDateTime.now()
        );
        boolean userAdded = userDAO.addUser(newUser);
        System.out.println("User added: " + userAdded);

        // 2. Get user by username
        User fetchedUser = userDAO.getUserByUsername("john_doe");
        if (fetchedUser != null) {
            System.out.println("User fetched: " + fetchedUser.getName());
        } else {
            System.out.println("User not found.");
        }

        // 3. Get all users
        List<User> allUsers = userDAO.getAllUsers();
        System.out.println("All users: ");
        for (User user : allUsers) {
            System.out.println("- " + user.getUsername());
        }

        // 4. Update user
        if (fetchedUser != null) {
            fetchedUser.setName("John Updated");
            boolean userUpdated = userDAO.updateUser(fetchedUser);
            System.out.println("User updated: " + userUpdated);
        }

        // 5. Delete user
        if (fetchedUser != null) {
            boolean userDeleted = userDAO.deleteUser(fetchedUser.getId());
            System.out.println("User deleted: " + userDeleted);
        }
    }
}
