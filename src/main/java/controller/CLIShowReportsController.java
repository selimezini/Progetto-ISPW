package controller;

import beans.BeanReport;
import factory.GraphicalFactory;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CLIShowReportsController extends ShowReportsController {

    private final ReportController reportController = new ReportController();
    private final Scanner scanner = new Scanner(System.in);



    @Override
    public void showReports() {
        System.out.println("\n==== ELENCO SEGNALAZIONI ====");


        List<BeanReport> reports = reportController.getReportsForCurrentMunicipality();

        // 2) Se non ci sono segnalazioni, esci
        if (reports.isEmpty()) {
            System.out.println("Nessuna segnalazione per il comune selezionato.");
            return;
        }


        for (int i = 0; i < reports.size(); i++) {
            BeanReport r = reports.get(i);
            String preview = String.format(
                    "%d) Titolo: %s | Data: %s | Urgenza: %s | Stato: %s",
                    i + 1,
                    r.getTitle(),
                    r.getDate().toString(),
                    r.getUrgencyType(),
                    r.getStatus()
            );
            System.out.println(preview);
        }

        System.out.println("\nInserisci il numero della segnalazione per i dettagli, o 0 per tornare indietro:");
        int choice = -1;
        while (true) {
            System.out.print("> ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Per favore inserisci un numero valido.");
                continue;
            }
            if (choice < 0 || choice > reports.size()) {
                System.out.println("Scelta non valida. Riprova.");
            } else {
                break;
            }
        }


        if (choice == 0) {
            return;
        }


        BeanReport selected = reports.get(choice - 1);
        GraphicalFactory graphicalFactory = GraphicalFactory.getInstance();
        ReportDetailsController reportDetailsController = graphicalFactory.createReportDetailsController();
        reportDetailsController.setReport(selected);
    }
}
