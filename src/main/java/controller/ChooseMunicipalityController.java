package controller;

import beans.MunicipalityBean;

import java.util.List;

public abstract class ChooseMunicipalityController {

    public abstract void searchMunicipality();

    public abstract List<MunicipalityBean> showMunicipalityList();

    public abstract void chooseMunicipality();
}

