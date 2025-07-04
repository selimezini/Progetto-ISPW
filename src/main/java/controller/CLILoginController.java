package controller;

import beans.LoginBean;
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
        while (true) {

            LoginBean creds = raccogliCredenziali();
            LoginController loginController = new LoginController();

            try {
                loginController.authenticateUser(creds);
                System.out.println("Autenticazione avvenuta con successo. Bentornato " + creds.getUsername() + "!")  ;
                GraphicalFactory factory = GraphicalFactory.getInstance();
                if(creds.getRole().equals("Citizen")) {
                    HomeController homeController = factory.createHomeController();
                    homeController.loadHome();
                }else{
                    HomeEmployeeController homeEmployeeController = factory.createHomeEmployeeController();
                    homeEmployeeController.loadHome();
                }

            }catch(ApplicationException e) {
                System.out.println(e.getMessage());
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


