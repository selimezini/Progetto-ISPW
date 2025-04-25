package Controller;

import Model.Municipality;
import java.util.List;

public abstract class ChooseMunicipalityController {

    public abstract void searchMunicipality(String municipality);

    /**
     * @return lista oggetti di Municipality
     */
    public abstract List<Municipality> showMunicipalityList();

    public abstract void chooseMunicipality();
}

