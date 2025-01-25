package ServiceTest;

import com.example.megacitycab.model.User;
import com.example.megacitycab.service.AuthService;

public class AuthServiceTest {
    public static void main(String[] args) {
        AuthService authService = new AuthService();

        // Test successful login
        try {
            User user = authService.authenticate("jane_smith", "mypassword");
            System.out.println("Login successful! Welcome, " + user.getName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Test failed login
        try {
            User user = authService.authenticate("john_doe", "wrongpassword");
            System.out.println("Login successful! Welcome, " + user.getName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
