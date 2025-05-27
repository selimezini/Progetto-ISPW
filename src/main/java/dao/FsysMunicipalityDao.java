package dao;

import Model.Municipality;

import java.util.List;

public class FsysMunicipalityDao extends MunicipalityDao {
    @Override
    public List<Municipality> getMunicipalityByName(String name) {
        return List.of();
    }

    @Override
    public Municipality getMunicipalityByCode(String code) {
        return null;
    }

    @Override
    public Municipality getMunicipalityByNameAndRegion(String municipalityName, String region){
        return null;
    }


}
