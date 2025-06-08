package dao;

import model.Municipality;

import java.util.List;

public  abstract class MunicipalityDao {

public abstract List<Municipality> getMunicipalityByName(String name);

public abstract Municipality getMunicipalityByCode(String code);

public abstract Municipality getMunicipalityByNameAndRegion(String municipalityName, String region);




}
