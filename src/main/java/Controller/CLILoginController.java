package Controller;

import Beans.LoginBean;
import exceptions.ApplicationException;
import org.example.viewprova2.session.SessionManager;

import java.util.Scanner;

public class CLILoginController extends GraphicLoginController {

    private final Scanner scanner = new Scanner(System.in);
    private final LoginController appController = new LoginController();

    @Override
    public void login() {
        while (true) {
            System.out.println("*************************");
            System.out.println("**     CivisAlert      **");
            System.out.println("*************************");
            System.out.println("1) Login");
            System.out.println("2) Registrati");
            System.out.println("0) Esci");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    attemptLogin();
                    break;
                case "2":
                    register();
                    break;
                case "0":
                    System.out.println("Uscita dal programma...");
                    return;
                default:
                    System.out.println("Scelta invalida. Riprova.");
            }
        }
    }

    private LoginBean raccogliCredenziali() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        String role;
        String municipalCode = null;
        while (true) {
            System.out.print("Sei dipendente del comune? (si/no): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            if (resp.equals("si")) {
                role = "Dipendente";
                System.out.print("Codice comune: ");
                municipalCode = scanner.nextLine().trim();
                break;
            } else if (resp.equals("no")) {
                role = "Citizen";
                break;
            } else {
                System.out.println("Risposta non valida, inserisci 'si' o 'no'.");
            }
        }

        return new LoginBean(username, password, role, municipalCode);
    }

    private void attemptLogin() {
        while (true) {
            LoginBean creds = raccogliCredenziali();
            try {
                appController.authenticateUser(creds);
                String username = SessionManager.getInstance().getCurrentUser().getUsername();
                System.out.println("Login avvenuto con successo! Benvenuto, " + username);
                return;  // esce dal loop di login
            } catch (ApplicationException ex) {
                System.out.println("Errore: " + ex.getMessage());
                System.out.print("Vuoi riprovare? (si/no): ");
                String again = scanner.nextLine().trim().toLowerCase();
                if (!again.equals("si")) {
                    return;
                }
            }
        }
    }

    @Override
    public void register() {
        System.out.println("** Registrazione **");
        LoginBean creds = raccogliCredenziali();
        try {
            appController.registerUser(creds);
            String username = SessionManager.getInstance().getCurrentUser().getUsername();
            System.out.println("Registrazione completata. Benvenuto, " + username);
        } catch (ApplicationException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }
    }
}


