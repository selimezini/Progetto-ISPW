package controller;

import beans.BeanReport;
import model.ProblemType;
import model.UrgencyType;
import exceptions.ApplicationException;

import java.util.Scanner;

public class CLIReportController extends DoReportController {


    public CLIReportController() {
        createReport();
    }

    @Override
    public void createReport() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n==== Creazione Nuova Segnalazione (CLI) ====");

        // 1. Titolo
        System.out.print("Titolo del problema: ");
        String title = scanner.nextLine().trim();
        while (title.isEmpty()) {
            System.out.print("Il titolo non può essere vuoto. Riprova: ");
            title = scanner.nextLine().trim();
        }

        // 2. Descrizione
        System.out.print("Descrizione del problema: ");
        String description = scanner.nextLine().trim();
        while (description.isEmpty()) {
            System.out.print("La descrizione non può essere vuota. Riprova: ");
            description = scanner.nextLine().trim();
        }

        // 3. Via del problema
        System.out.print("Via del problema: ");
        String via = scanner.nextLine().trim();
        while (via.isEmpty()) {
            System.out.print("La via non può essere vuota. Riprova: ");
            via = scanner.nextLine().trim();
        }

        // 4. Tipo di problema
        ProblemType[] types = ProblemType.values();
        System.out.println("\nTipi di problema disponibili:");
        for (int i = 0; i < types.length; i++) {
            System.out.printf("  %d) %s\n", i + 1, types[i].getDescription());
        }

        int typeIndex = -1;
        while (typeIndex < 0 || typeIndex >= types.length) {
            System.out.print("Seleziona il tipo di problema (1-" + types.length + "): ");
            try {
                typeIndex = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                typeIndex = -1;
            }
        }

        ProblemType selectedType = types[typeIndex];

        // 5. Urgenza
        UrgencyType[] urgencies = UrgencyType.values();
        System.out.println("\nLivelli di urgenza disponibili:");
        for (int i = 0; i < urgencies.length; i++) {
            System.out.printf("  %d) %s\n", i + 1, urgencies[i].getDescription());
        }

        int urgencyIndex = -1;
        while (urgencyIndex < 0 || urgencyIndex >= urgencies.length) {
            System.out.print("Seleziona l’urgenza (1-" + urgencies.length + "): ");
            try {
                urgencyIndex = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                urgencyIndex = -1;
            }
        }

        UrgencyType selectedUrgency = urgencies[urgencyIndex];

        // 6. Percorso immagine (opzionale)
        System.out.print("Percorso dell’immagine (facoltativo, invio per saltare): ");
        String imagePath = scanner.nextLine().trim();
        if (imagePath.isEmpty()) {
            imagePath = null;
        }

        // 7. Costruisci il Bean e invia la segnalazione
        BeanReport bean = new BeanReport(
                title,
                description,
                selectedType.getDescription(),
                selectedUrgency.getDescription(),
                "APERTO",
                imagePath,
                null, // immagine non gestita da CLI
                via
        );

        ReportController controller = new ReportController();
        try {
            controller.submitReport(bean);
            System.out.println("Segnalazione inviata con successo!");
            LoginController loginController = new LoginController();
        } catch (ApplicationException e) {
            System.out.println("Errore durante l’invio della segnalazione: " + e.getMessage());
        }
    }

    @Override
    public void deleteReport() {
        System.out.println("deleteReport() non supportato in modalità CLI.");
    }

    @Override
    public void updateReport() {
        System.out.println("updateReport() non supportato in modalità CLI.");
    }
}
