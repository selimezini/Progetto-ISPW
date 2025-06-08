package controller;

import beans.BeanReport;
import model.ReportStatus;
import exceptions.DataAccessException;

import java.util.Scanner;

public class CLIReportDetailsController extends ReportDetailsController {

    private BeanReport report;
    private final Scanner scanner = new Scanner(System.in);
    private final ReportController reportController = new ReportController();





    @Override
    public void setReport(BeanReport beanReport) {
        this.report = beanReport;

        // 1) Mostra i dettagli della segnalazione
        System.out.println("\n==== DETTAGLI SEGNALAZIONE ====");
        System.out.println("Titolo       : " + report.getTitle());
        System.out.println("Data         : " + report.getDate());
        System.out.println("Urgenza      : " + report.getUrgencyType());
        System.out.println("Tipo Problema: " + report.getProblemType());
        System.out.println("Descrizione  : " + report.getDescription());
        System.out.println("Via          : " + report.getViaDelProblema());
        System.out.println("Stato attuale: " + report.getStatus());
        if (report.getImagePath() != null && !report.getImagePath().isBlank()) {
            System.out.println("Immagine     : " + report.getImagePath());
        }
        System.out.println("===============================");
    }

    @Override
    public void confirmChanges() {
        // 2) Chiede all’utente se vuole cambiare lo stato
        System.out.println("\nVuoi modificare lo stato? (s/n)");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (!answer.equals("s")) {
            System.out.println("Nessuna modifica effettuata.");
            return;
        }

        // 3) Mostra i possibili stati enumerati
        ReportStatus[] statuses = ReportStatus.values();
        System.out.println("\nSeleziona il nuovo stato:");
        for (int i = 0; i < statuses.length; i++) {
            System.out.printf("  %d) %s\n", i + 1, statuses[i].getDescription());
        }

        int choice = -1;
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(line) - 1;
            } catch (NumberFormatException e) {
                choice = -1;
            }
            if (choice < 0 || choice >= statuses.length) {
                System.out.println("Input non valido. Riprova.");
            } else {
                break;
            }
        }

        // 4) Applica la modifica
        String newStatusDesc = statuses[choice].getDescription();
        report.setStatus(newStatusDesc);

        try {
            reportController.submitUpdate(report);
            System.out.println("Stato aggiornato in \"" + newStatusDesc + "\" con successo.");
        } catch (DataAccessException e) {
            System.out.println("Errore aggiornando lo stato: " + e.getMessage());
        }
    }
}
