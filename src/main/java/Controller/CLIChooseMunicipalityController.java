package Controller;

import Model.Municipality;
import java.util.List;

public class CLIChooseMunicipalityController extends ChooseMunicipalityController {

    @Override
    public void searchMunicipality(String municipality) {
        // Implementazione CLI della ricerca (placeholder)
        System.out.println("Ricerca comune: " + municipality);
    }

    @Override
    public List<Municipality> showMunicipalityList() {
        // Restituisce una lista vuota per ora (da implementare)
        return List.of();
    }

    @Override
    public void chooseMunicipality() {
        // Implementazione CLI della scelta (placeholder)
        System.out.println("Comune selezionato.");
    }
}
