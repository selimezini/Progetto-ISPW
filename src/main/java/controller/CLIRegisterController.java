package controller;

import beans.LoginBean;
import factory.GraphicalFactory;
import exceptions.ApplicationException;

import java.util.Scanner;

public class CLIRegisterController extends RegisterController {



    @Override
    public void register() {
        Scanner sc = new Scanner(System.in);


        System.out.print("Inserisci username: ");
        String user = sc.nextLine().trim();

        System.out.print("Inserisci password: ");
        String pass = sc.nextLine().trim();

        System.out.print("Sei dipendente del comune? (si/no): ");

        String resp = sc.nextLine().trim().toLowerCase();
        boolean isEmp = resp.equals("si");

        String code = "";
        if (isEmp) {

            System.out.print("Inserisci il codice del comune: ");
            code = sc.nextLine().trim();
        }


        if (user.isEmpty() || pass.isEmpty() || (isEmp && code.isEmpty())) {
            System.out.println("Errore: devi compilare tutti i campi richiesti.");
            return;
        }

        String role = isEmp ? "Employee" : "Citizen";
        LoginBean bean = new LoginBean(user, pass, role, code);


        try {
            LoginController loginController = new LoginController();
            loginController.registerUser(bean);
            GraphicalFactory  graphicalFactory = GraphicalFactory.getInstance();
             GraphicLoginController graphicLoginController = graphicalFactory.createLoginController();
            System.out.println("Registrazione avvenuta con successo!");
        } catch (ApplicationException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
