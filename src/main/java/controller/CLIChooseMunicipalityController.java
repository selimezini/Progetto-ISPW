package controller;

import beans.MunicipalityBean;
import org.example.viewprova2.session.SessionManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CLIChooseMunicipalityController extends ChooseMunicipalityController {


    public  CLIChooseMunicipalityController(){
        searchMunicipality();
    }


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

        // 3) Se nessun risultato, ripeti
        if (lastResults.isEmpty()) {
            System.out.println("Nessun comune trovato con '" + munName + "'.");
            searchMunicipality();
            return;
        }

        // 4) Mostra i risultati numerati
        System.out.println("\nComuni trovati:");
        for (int i = 0; i < lastResults.size(); i++) {
            MunicipalityBean mb = lastResults.get(i);
            System.out.printf("  %d) %s\n", i + 1, mb.toString());
        }

        // 5) Leggi scelta utente
        int selected = -1;
        while (true) {
            System.out.print("Seleziona il numero del comune (1-" + lastResults.size() + "): ");
            try {
                selected = sc.nextInt() - 1;
                sc.nextLine(); // consuma newline
            } catch (InputMismatchException ime) {
                sc.nextLine(); // consuma l’input errato
                System.out.println("Inserisci un numero valido.");
                continue;
            }
            if (selected < 0 || selected >= lastResults.size()) {
                System.out.println("Numero non valido. Riprova.");
            } else {
                break;
            }
        }

        // 6) Preleva il bean scelto e aggiorna la sessione
        MunicipalityBean chosen = lastResults.get(selected);
        SessionManager.getInstance().setCurrentMunicipalityReport(chosen);
        System.out.println("Comune selezionato: " + chosen.toString());

        // 7) Ora “passo di scena” al controller CLI dei report:
        CLIReportController cliReport = new CLIReportController();

    }

    @Override
    public void chooseMunicipality() {

        System.out.println("Comune selezionato.");
    }
}
