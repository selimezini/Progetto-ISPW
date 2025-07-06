package controller;

import beans.LoginBean;
import exceptions.ValidationException;
import factory.GraphicalFactory;
import exceptions.ApplicationException;

import java.util.Scanner;

public class CLILoginController extends GraphicLoginController {

    private final Scanner scanner = new Scanner(System.in);




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

        String role = null;
        String municipalCode = null;
        boolean inputValido = false;

        while (!inputValido) {
            System.out.print("Sei dipendente del comune? (si/no): ");
            String resp = scanner.nextLine().trim().toLowerCase();

            if (resp.equals("si")) {
                role = "Employee";
                System.out.print("Codice comune: ");
                municipalCode = scanner.nextLine().trim();
                inputValido = true;
            } else if (resp.equals("no")) {
                role = "Citizen";
                inputValido = true;
            } else {
                System.out.println("Risposta non valida, inserisci 'si' o 'no'.");
            }
        }

        return new LoginBean(username, password, role, municipalCode);
    }
    private void attemptLogin() {
        LoginController loginController = new LoginController();
        GraphicalFactory factory = GraphicalFactory.getInstance();

        while (true) {
            LoginBean creds = raccogliCredenziali();

            try {
                creds.validate();
                loginController.authenticateUser(creds);

                System.out.println("Autenticazione avvenuta con successo. Bentornato "
                        + creds.getUsername() + "!");
                GraphicalFactory graphicalFactory = GraphicalFactory.getInstance();
                if ("Citizen".equals(creds.getRole())) {
                   HomeController homeController = graphicalFactory.createHomeController();
                    homeController.loadHome();
                } else {
                    HomeEmployeeController homeEmployeeController = graphicalFactory.createHomeEmployeeController();
                    homeEmployeeController.loadHome();
                }

                break;

            } catch (ValidationException ve) {
                // input non validi: ripeti il ciclo
                System.out.println("Errore di input: " + ve.getMessage());
                System.out.println("Riprova.\n");

            } catch (ApplicationException ae) {

                System.out.println("Login fallito: " + ae.getMessage());
                System.out.println("Riprova.\n");
            }
        }
    }

    @Override
    public void register() {
        GraphicalFactory factory = GraphicalFactory.getInstance();
        RegisterController registerController = factory.createRegisterController();
        registerController.register();
    }
}


