package org.example.viewprova2;


import Controller.GraphicLoginController;
import Controller.SceneManager;
import Factory.GraphicalFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    private static String executionMode;

    /**
     * Punto di ingresso standard.
     * Decide CLI vs GUI: se GUI chiama launch() che scatena start().
     */
    public static void main(String[] args) {
        System.out.println("Benvenuti in CivisAlert!");
        askExecutionMode();

        if ("GUI".equals(executionMode)) {
            // Avvia il toolkit JavaFX e poi esegue start(...)
            launch(args);
        } else {
            // Modalità CLI
            runCli();
        }
    }

    private static void runCli() {
        System.out.println("Avvio in modalità CLI...");

        GraphicalFactory cliFactory = GraphicalFactory.getInstance();
        GraphicLoginController loginController  = cliFactory.createLoginController();
        loginController.login();


    }

    private static void askExecutionMode() {
        Scanner sc = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Seleziona la modalità di esecuzione:");
            System.out.println("1) GUI");
            System.out.println("2) CLI");
            System.out.print("> ");
            input = sc.nextLine().trim();

            if ("1".equals(input) || "GUI".equalsIgnoreCase(input)) {
                executionMode = "GUI";
                break;
            } else if ("2".equals(input) || "CLI".equalsIgnoreCase(input)) {
                executionMode = "CLI";
                break;
            } else {
                System.out.println("Input non valido. Digita '1' o 'GUI' per GUI, '2' o 'CLI' per CLI.\n");
            }
        }
    }

    /**
     * Restituisce la modalità scelta dall'utente.
     */
    public static String getExecutionMode() {
        if (executionMode == null) {
            throw new IllegalStateException("Modalità di esecuzione non inizializzata.");
        }
        return executionMode;
    }

    // ---------------------------------------------------
    // Questo è il metodo che JavaFX chiama dopo launch():
    // ---------------------------------------------------
    @Override
    public void start(Stage primaryStage) {
        // 1) Registra lo stage principale in SceneManager
        SceneManager.setStage(primaryStage);

        // 2) Carica la scena di login usando SceneManager
        SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert – Login");
    }




}



