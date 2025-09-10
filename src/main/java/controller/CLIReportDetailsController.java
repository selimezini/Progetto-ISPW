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

        printDetails();

        confirmChanges();
    }

    private void printDetails() {
        System.out.println("\n==== DETTAGLI SEGNALAZIONE ====");
        System.out.println("Titolo       : " + safe(report.getTitle()));
        System.out.println("Data         : " + safe(report.getDate()));
        System.out.println("Urgenza      : " + safe(report.getUrgencyType()));
        System.out.println("Tipo Problema: " + safe(report.getProblemType()));
        System.out.println("Descrizione  : " + safe(report.getDescription()));
        System.out.println("Via          : " + safe(report.getViaDelProblema()));
        System.out.println("Stato attuale: " + safe(report.getStatus()));
        if (report.getImagePath() != null && !report.getImagePath().isBlank()) {
            System.out.println("Immagine     : " + report.getImagePath());
        }
        System.out.println("===============================");
    }


    private String safe(Object o) {
        return o == null ? "" : o.toString();
    }

    @Override
    public void confirmChanges() {
        System.out.println("\nVuoi modificare lo stato? (s/n)");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (!answer.equals("s") && !answer.equals("y")) {
            System.out.println("Nessuna modifica effettuata.");
            return;
        }


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

        String newStatusDesc = statuses[choice].getDescription();


        if (newStatusDesc.equalsIgnoreCase(report.getStatus())) {
            System.out.println("Lo stato selezionato è uguale a quello attuale. Nessuna modifica effettuata.");
            return;
        }

        report.setStatus(newStatusDesc);

        try {
            reportController.submitUpdate(report);
            System.out.println("Stato aggiornato in \"" + newStatusDesc + "\" con successo.");
        } catch (DataAccessException e) {
            System.out.println("Errore aggiornando lo stato: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore imprevisto durante l'aggiornamento: " + e.getMessage());
        }
    }
}
