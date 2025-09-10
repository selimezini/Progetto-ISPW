package controller;

import beans.BeanReport;
import exceptions.ValidationException;
import factory.GraphicalFactory;
import model.ProblemType;
import model.UrgencyType;
import exceptions.ApplicationException;

import java.util.Scanner;

public class CLIReportController extends DoReportController {




    @Override
    public void createReport() {
        Scanner scanner = new Scanner(System.in);
        ReportController controller = new ReportController();

        boolean done = false;

        while (!done) {
            System.out.println("\n==== Creazione Nuova Segnalazione ====");

            System.out.print("Titolo del problema: ");
            String title = scanner.nextLine().trim();

            System.out.print("Descrizione del problema: ");
            String description = scanner.nextLine().trim();

            System.out.print("Via del problema: ");
            String via = scanner.nextLine().trim();

            ProblemType[] types = ProblemType.values();
            System.out.println("\nTipi di problema disponibili:");
            for (int i = 0; i < types.length; i++) {
                System.out.printf("  %d) %s%n", i + 1, types[i].getDescription());
            }
            System.out.print("Seleziona il tipo di problema (1-" + types.length + "): ");
            int typeIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            ProblemType selectedType = types[typeIndex];

            UrgencyType[] urgencies = UrgencyType.values();
            System.out.println("\nLivelli di urgenza disponibili:");
            for (int i = 0; i < urgencies.length; i++) {
                System.out.printf("  %d) %s%n", i + 1, urgencies[i].getDescription());
            }
            System.out.print("Seleziona l’urgenza (1-" + urgencies.length + "): ");
            int urgencyIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            UrgencyType selectedUrgency = urgencies[urgencyIndex];

            System.out.print("Percorso dell’immagine (facoltativo, invio per saltare): ");
            String imagePath = scanner.nextLine().trim();
            if (imagePath.isEmpty()) {
                imagePath = null;
            }

            BeanReport bean = new BeanReport();
            bean.setTitle(title);
            bean.setDescription(description);
            bean.setViaDelProblema(via);
            bean.setProblemType(selectedType.getDescription());
            bean.setUrgencyType(selectedUrgency.getDescription());
            bean.setStatus("APERTO");
            bean.setImagePath(imagePath);
            bean.setImage(null);

            try {
                bean.validate();
                controller.submitReport(bean);

                System.out.println("Segnalazione inviata con successo!");
                done = true; // usciamo dal ciclo impostando il flag

            } catch (ValidationException ve) {
                System.out.println("Errore di input: " + ve.getMessage());
                System.out.println("Riprova da capo.\n");
                // done rimane false -> il ciclo ricomincia

            } catch (ApplicationException ae) {
                System.out.println("Errore durante l’invio della segnalazione: " + ae.getMessage());
                done = true; // usciamo dal ciclo anche in caso di errore applicativo
            }
        }
    }

    @Override
    public void deleteReport() {

        System.out.println("non implementato.");
    }

}
