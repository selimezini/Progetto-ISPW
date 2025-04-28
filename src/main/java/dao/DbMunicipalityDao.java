package dao;

import Model.Municipality;

import java.util.List;

public class DbMunicipalityDao extends MunicipalityDao{
    @Override
    public List<Municipality> getMunicipalityByName(String name) {
        return List.of();
    }

    @Override
    public Municipality getMunicipalityByCode(String code){
        return new Municipality();

    }


}
