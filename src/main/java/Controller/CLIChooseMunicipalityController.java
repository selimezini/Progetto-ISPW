package Controller;

import Beans.MunicipalityBean;
import Model.Municipality;
import java.util.List;

public class CLIChooseMunicipalityController extends ChooseMunicipalityController {

    @Override
    public void searchMunicipality(String municipality) {
        // Implementazione CLI della ricerca (placeholder)
        System.out.println("Ricerca comune: " + municipality);
    }

    @Override
    public List<MunicipalityBean> showMunicipalityList() {
        // Restituisce una lista vuota per ora (da implementare)
        return List.of();
    }

    @Override
    public void chooseMunicipality() {
        // Implementazione CLI della scelta (placeholder)
        System.out.println("Comune selezionato.");
    }
}
