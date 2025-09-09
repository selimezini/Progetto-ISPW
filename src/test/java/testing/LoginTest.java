package testing;


import beans.LoginBean;
import controller.LoginController;
import model.users.User;
import org.example.viewprova2.session.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private LoginController loginController;

    @BeforeEach
    void setUp() {

        loginController = new LoginController();
        SessionManager.getInstance().setCurrentUser(null); // reset sessione
    }

    @Test
    void testAuthenticateUser_SuccessfulLogin() {
        LoginBean loginBean = new LoginBean("mario", "password", "Citizen", null);

        // Esegue l’autenticazione
        loginController.authenticateUser(loginBean);

        // Controlla che l’utente sia stato salvato in sessione
        User loggedUser = SessionManager.getInstance().getCurrentUser();

        assertNotNull(loggedUser, "User should be logged in");
        assertEquals("mario", loggedUser.getUsername(), "Username should match");
        assertEquals("password", loggedUser.getPassword(), "Password should match");
        assertEquals("Citizen", loggedUser.getRole(), "Role should match");
    }

    @Test
    void testAuthenticateUser_InvalidLogin() {
        LoginBean loginBean = new LoginBean("wrongUser", "wrongPass", "Citizen", null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            loginController.authenticateUser(loginBean);
        });

        assertTrue(exception.getMessage().contains("Credenziali non valide"),
                "Exception message should indicate invalid credentials");
    }

    @Test
    void testLogout() {
        LoginBean loginBean = new LoginBean("mario", "password", "Citizen", null);
        loginController.authenticateUser(loginBean);

        assertNotNull(SessionManager.getInstance().getCurrentUser(), "User should be logged in before logout");

        // Esegue logout
        loginController.logout();

        assertNull(SessionManager.getInstance().getCurrentUser(), "User should be null after logout");
    }
}
