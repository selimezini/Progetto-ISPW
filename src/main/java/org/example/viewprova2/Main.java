package org.example.viewprova2;


import controller.GraphicLoginController;

import factory.GraphicalFactory;
import exceptions.DataLoadException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main extends Application {

    private static String executionMode;
    private static String storageMode = "db";


    public static void main(String[] args) {
        try {
            loadConfig();
        } catch (DataLoadException ex) {
            System.err.println(" Config persistenza: " + ex.getMessage());
            System.err.println("Uso modalità di default: " + storageMode);
        }

        System.out.println("Benvenuti in CivisAlert!");
        askExecutionMode();

        if ("GUI".equals(executionMode)) {
            launch(args);
        } else {
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


    public static String getExecutionMode() {
        if (executionMode == null) {
            throw new IllegalStateException("Modalità di esecuzione non inizializzata.");
        }
        return executionMode;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login-view.fxml"));

            GraphicalFactory factory = GraphicalFactory.getInstance();
            GraphicLoginController loginController = factory.createLoginController();
            loader.setController(loginController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("CivisAlert – Login");
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della schermata di login: " + e.getMessage());
        }
    }


    public static void loadConfig() {
        Properties props = new Properties();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties")) {

            if (in == null) {
                throw new DataLoadException("File di configurazione non trovato nel classpath");
            }

            props.load(in);

            String m = props.getProperty("mode");
            if (m == null) {
                throw new DataLoadException("Manca la proprietà `mode`");
            }

            m = m.trim().toLowerCase();
            switch (m) {
                case "demo", "fsys", "db" -> storageMode = m;
                default -> throw new DataLoadException("Mode non valido: " + m);
            }

            System.out.println("Persistenza dati: " + storageMode);

        } catch (IOException e) {
            throw new DataLoadException("Impossibile leggere il file di configurazione", e);
        }
    }



    public static String getStorageMode(){
        return storageMode;
    }



}


