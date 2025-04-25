package Model;

import java.util.List;

public class Municipality {

    private String name;
    private String province;
    private String sigla;
    private String codice;
    private List<Report> reports;
    private String region;

    public String getSigla() {
        return sigla;
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

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public List<Report> getReports() {
        return reports;
    }

}

