package Controller;

import Beans.LoginBean;

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
                    accedi();
                    break;
                case "2":
                    register();
                    break;
                case "0":
                    System.out.println("Uscita dal programma...");
                    return;
                default:
                    System.out.println("Scelta invalida. Riprova.");
                    break;
            }
        }
    }

    private LoginBean raccogliCredenziali() {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Inserisci password: ");
        String password = scanner.nextLine().trim();

        String role;
        String municipalCode = null;

        while (true) {
            System.out.print("Sei un dipendente del comune? (si/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("si")) {
                role = "employee";
                System.out.print("Inserisci codice comune: ");
                municipalCode = scanner.nextLine().trim();
                break;
            } else if (response.equals("no")) {
                role = "citizen";
                break;
            } else {
                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
            }
        }

        return new LoginBean(username, password, role, municipalCode);
    }

    public void accedi() {
        LoginBean credentials = raccogliCredenziali();

        // TODO: Chiama il controller applicativo per l'autenticazione
        // Esempio:
        // ApplicationController controller = new ApplicationController();
        // boolean success = controller.autenticaUtente(credentials);
        // if (success) { ... } else { ... }
    }

    public void register() {

        System.out.println("** Registrazione **");
        LoginBean credentials = raccogliCredenziali();

        // TODO: Chiama il controller applicativo per la registrazione
        // Esempio:
        // ApplicationController controller = new ApplicationController();
        // boolean success = controller.registraUtente(credentials);
        // if (success) { ... } else { ... }
    }
}


