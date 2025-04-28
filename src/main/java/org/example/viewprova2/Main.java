package org.example.viewprova2;


import Controller.GraphicLoginController;
import Controller.SceneManager;
import Factory.GraphicalFactory;
import exceptions.DataLoadException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main extends Application {

    private static String executionMode;
    private static String storageMode = "db";


    /**
     * Punto di ingresso standard.
     * Decide CLI vs GUI: se GUI chiama launch() che scatena start().
     */
    public static void main(String[] args) {
        try {
            loadConfig();
        } catch (DataLoadException ex) {
            // ← qui dentro ci finisci solo se loadConfig() ha thrown new DataLoadException(...)
            System.err.println(" Config persistenza: " + ex.getMessage());
            System.err.println("Uso modalità di default: " + storageMode);
        }

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


    public static void loadConfig(){
            Properties props = new Properties();
            try(FileInputStream in = new FileInputStream("src/main/resources/config/config.properties")){

            // 1) carica tutte le coppie chiave= valore dal file in un oggetto Properties\
            props.load(in);

            // 2) legge la proprieta "mode"
            String m = props.getProperty("mode");
            if( m == null){

                throw new DataLoadException("manca la proprieta` mode");

            }

            m = m.trim().toLowerCase();
            switch (m){
                case "demo", "fsys", "db" -> storageMode = m;
                default -> throw new DataLoadException("mode non valido :" + m);

            }

            System.out.println("Persistenza dati" + storageMode);

            } catch(IOException e){
            // 5) se il file non esiste o non si riesce a leggere, allora lancio un eccezione
                throw new DataLoadException("impossibile leggere il file");
            }



    }


    public static String getStorageMode(){
        return storageMode;
    }



}



