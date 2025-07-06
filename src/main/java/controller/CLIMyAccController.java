package controller;

import beans.LoginBean;
import factory.GraphicalFactory;
import model.users.User;
import org.example.viewprova2.session.SessionManager;

import java.util.Scanner;

public class CLIMyAccController extends MyAccController {

    private final LoginController loginController = new LoginController();
    private final Scanner sc = new Scanner(System.in);



    public void startMyAcc() {
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

        boolean anyChange = changeUsername(currentBean);
        boolean passwordChanged = changePassword(currentBean);
        if (passwordChanged) {
            anyChange = true;
        }


        if (!anyChange) {
            System.out.println("Nessuna modifica effettuata.");
        }
    }

    private boolean changeUsername(LoginBean currentBean) {
        System.out.print("Vuoi cambiare username? (s/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("s")) {
            return false;
        }

        System.out.print("Inserisci nuovo username: ");
        String newUser = sc.nextLine().trim();
        if (newUser.isEmpty()) {
            System.out.println("Username non può essere vuoto. Operazione annullata.");
            return false;
        }

        try {
            loginController.changeUsername(
                    new LoginBean(newUser,
                            currentBean.getPassword(),
                            currentBean.getRole(),
                            null)
            );
            SessionManager.getInstance()
                    .getCurrentUser()
                    .setUsername(newUser);
            System.out.println("Username aggiornato con successo a: " + newUser);
            return true;
        } catch (Exception e) {
            System.out.println("Errore cambio username: " + e.getMessage());
            return false;
        }
    }

    private boolean changePassword(LoginBean currentBean) {
        System.out.print("Vuoi cambiare password? (s/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("s")) {
            return false;
        }

        System.out.print("Inserisci nuova password: ");
        String newPass = sc.nextLine().trim();
        if (newPass.isEmpty()) {
            System.out.println("Password non può essere vuota. Operazione annullata.");
            return false;
        }

        try {
            loginController.changePassword(
                    new LoginBean(currentBean.getUsername(),
                            newPass,
                            currentBean.getRole(),
                            null)
            );
            SessionManager.getInstance()
                    .getCurrentUser()
                    .setPassword(newPass);
            System.out.println("Password aggiornata con successo.");
            return true;
        } catch (Exception e) {
            System.out.println("Errore cambio password: " + e.getMessage());
            return false;
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
            homeEmployeeController.loadHome();
        } else {
            HomeController cliHome = factory.createHomeController();
            cliHome.loadHome();
        }
    }

    @Override
    public void exit() {
       loginController.logout();
        System.out.println("Logout effettuato. Arrivederci!");
        GraphicalFactory factory = GraphicalFactory.getInstance();
        GraphicLoginController controller = factory.createLoginController();
        controller.login();
    }
}
