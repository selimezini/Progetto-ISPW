package controller;

import beans.LoginBean;
import factory.GraphicalFactory;
import model.users.User;
import org.example.viewprova2.session.SessionManager;

import java.util.Scanner;

public class CLIMyAccController extends MyAccController {

    private final LoginController loginController = new LoginController();
    private final Scanner sc = new Scanner(System.in);

    public CLIMyAccController() {
        menu();
    }

    public void menu() {
        while (true) {
            System.out.println("*************************");
            System.out.println("**     CivisAlert      **");
            System.out.println("**       Account       **");
            System.out.println("*************************");
            System.out.println("1) Cambia credenziali");
            System.out.println("2) Visualizza credenziali");
            System.out.println("3) Torna alla home");
            System.out.println("4) Esci dall'account");
            System.out.print("> ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    confirmChanges();
                    break;
                case "2":
                    showCredentials();
                    break;
                case "3":
                    backToHome();
                    return;
                case "4":
                    exit();
                    return;
                default:
                    System.out.println("Scelta invalida. Riprova.");
            }
        }
    }


    @Override
    public void confirmChanges() {
        LoginBean currentBean = loginController.getUserCredentials();
        if (currentBean == null) {
            System.out.println("Errore interno: utente non trovato.");
            return;
        }

        boolean anyChange = false;

        // Cambio username?
        System.out.print("Vuoi cambiare username? (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        if (resp.equals("s")) {
            System.out.print("Inserisci nuovo username: ");
            String newUser = sc.nextLine().trim();
            if (newUser.isEmpty()) {
                System.out.println("Username non può essere vuoto. Operazione annullata.");
            } else {
                LoginBean beanForUsername = new LoginBean(
                        newUser,
                        currentBean.getPassword(),
                        currentBean.getRole(),
                        null
                );
                try {
                    loginController.changeUsername(beanForUsername);
                    System.out.println("Username aggiornato con successo a: " + newUser);
                    SessionManager.getInstance().getCurrentUser().setUsername(newUser);
                    anyChange = true;
                } catch (Exception e) {
                    System.out.println("Errore cambio username: " + e.getMessage());
                    return;
                }
            }
        }

        // Cambio password?
        System.out.print("Vuoi cambiare password? (s/n): ");
        resp = sc.nextLine().trim().toLowerCase();
        if (resp.equals("s")) {
            System.out.print("Inserisci nuova password: ");
            String newPass = sc.nextLine().trim();
            if (newPass.isEmpty()) {
                System.out.println("Password non può essere vuota. Operazione annullata.");
            } else {
                LoginBean beanForPassword = new LoginBean(
                        currentBean.getUsername(),
                        newPass,
                        currentBean.getRole(),
                        null
                );
                try {
                    loginController.changePassword(beanForPassword);
                    System.out.println("Password aggiornata con successo.");
                    SessionManager.getInstance().getCurrentUser().setPassword(newPass);
                    anyChange = true;
                } catch (Exception e) {
                    System.out.println("Errore cambio password: " + e.getMessage());
                    return;
                }
            }
        }

        if (!anyChange) {
            System.out.println("Nessuna modifica effettuata.");
        }
    }


    public void showCredentials() {
        LoginBean loginBean = loginController.getUserCredentials();
        if (loginBean == null) {
            System.out.println("Errore: nessun utente autenticato.");
            return;
        }
        System.out.println("Username: " + loginBean.getUsername());
        System.out.println("Password: " + loginBean.getPassword());
    }


    private void backToHome() {
        User current = SessionManager.getInstance().getCurrentUser();

        String role = current.getRole();
        GraphicalFactory factory = GraphicalFactory.getInstance();
        if ("Employee".equals(role)) {
            HomeEmployeeController  homeEmployeeController= factory.createHomeEmployeeController();
        } else {
            HomeController cliHome = factory.createHomeController();

        }
    }

    @Override
    public void exit() {
        SessionManager.getInstance().setCurrentUser(null);
        System.out.println("Logout effettuato. Arrivederci!");
        GraphicalFactory factory = GraphicalFactory.getInstance();
        GraphicLoginController loginController = factory.createLoginController();
    }
}
