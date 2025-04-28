package dao;

import Model.Municipality;

import java.util.ArrayList;
import java.util.List;

public class DemoMunicipalityDao extends MunicipalityDao {

    private final List<Municipality> municipalities = new ArrayList<>();
    private static   DemoMunicipalityDao instance;
    public static DemoMunicipalityDao getInstance() {
       if(instance == null) {
           instance = new DemoMunicipalityDao();
       }
        return instance;
    }

    private DemoMunicipalityDao() {
    municipalities.add(new Municipality("Milano","Milano","MI001","Lombardia"));
    municipalities.add(new Municipality("Grottaferrata","Roma","GR001","Lazio"));
    municipalities.add(new Municipality("Frascati","Roma","FR001","Lazio"));
    }


    @Override
    public List<Municipality> getMunicipalityByName(String name) {
        // qui cerco dei possibli match con quello che ha inserito l`utente
        String n = name.toLowerCase();
        List<Municipality> result = new ArrayList<>();
        for (Municipality m : municipalities) {
            if (m.getName().toLowerCase().contains(n)) {
                result.add(m);
            }

        }


        return result;
    }



    @Override
    public Municipality getMunicipalityByCode(String code) {

        String c = code.trim().toLowerCase();
        for(Municipality m : municipalities){
            if(m.getCodice().toLowerCase().equals(c)){
                return m;
            }
        }

        return null;
    }

}


