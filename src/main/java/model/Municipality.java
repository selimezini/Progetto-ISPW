package model;

import java.util.ArrayList;
import java.util.List;

public class Municipality {

    private String name;
    private String province;
    private String codice;
    private final List<Report> reports  = new ArrayList<>();
    private String region;


    public Municipality() {}

    public Municipality(String name, String province, String codice, String region) {

        this.name = name;
        this.province = province;

        this.codice = codice;
        this.region = region;

    }




    public String getRegion() {

        return region;
    }

    public String getCodice() {
        return codice;
    }


    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String provincia) {
        this.province = provincia;
    }

    public void setRegione(String region) {
        this.region = region;
    }



    public void addReport(Report report) {
        reports.add(report);
    }

    public List<Report> getReports() {
        return reports;
    }

}

