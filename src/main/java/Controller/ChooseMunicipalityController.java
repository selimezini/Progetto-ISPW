package Controller;

import Beans.MunicipalityBean;
import Model.Municipality;
import java.util.List;

public abstract class ChooseMunicipalityController {

    public abstract void searchMunicipality();

    public abstract List<MunicipalityBean> showMunicipalityList();

    public abstract void chooseMunicipality();
}

