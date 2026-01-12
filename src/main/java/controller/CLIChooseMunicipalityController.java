package controller;

import beans.MunicipalityBean;
import factory.GraphicalFactory;
import org.example.viewprova2.session.SessionManager;

import java.util.List;
import java.util.Scanner;

public class CLIChooseMunicipalityController extends ChooseMunicipalityController {





    @Override
    public void searchMunicipality() {
        Scanner sc = new Scanner(System.in);
        List<MunicipalityBean> lastResults;
        System.out.println("\n==== Ricerca Comune  ====");
        System.out.print("Inserisci il nome (o parte del nome) del comune: ");
        String munName = sc.nextLine().trim();
        while (munName.isEmpty()) {
            System.out.print("Il nome non può essere vuoto. Riprova: ");
            munName = sc.nextLine().trim();
        }

        MunicipalityBean requestBean = new MunicipalityBean(munName, null,null);
        ReportController reportController = new ReportController();
        lastResults = reportController.searchMunicipality(requestBean);


        if (lastResults.isEmpty()) {
            System.out.println("Nessun comune trovato con '" + munName + "'.");
            searchMunicipality();
            return;
        }


        System.out.println("\nComuni trovati:");
        for (int i = 0; i < lastResults.size(); i++) {
            MunicipalityBean mb = lastResults.get(i);
            System.out.println("  " + (i + 1) + ") " + mb);
        }


        int selected = -1;
        int max = lastResults.size();

        do {
            System.out.print("Seleziona il numero del comune (1-" + max + "): ");

            if (!sc.hasNextInt()) {
                sc.nextLine(); // consuma l’input errato
                System.out.println("Inserisci un numero valido.");
                continue;
            }


            selected = sc.nextInt() - 1;
            sc.nextLine();

            if (selected < 0 || selected >= max) {
                System.out.println("Numero non valido. Riprova.");
                selected = -1;
            }

        } while (selected < 0);





        MunicipalityBean chosen = lastResults.get(selected);
        SessionManager.getInstance().setCurrentMunicipalityReport(chosen);
        System.out.println("Comune selezionato: " + chosen.toString());


       DoReportController controller = GraphicalFactory.getInstance().createReportController();
       controller.createReport();
    }


}
