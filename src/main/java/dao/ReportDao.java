package dao;

import Model.Municipality;
import Model.Report;

import java.util.List;

public abstract class ReportDao {


    public abstract List<Report> getReportsOfUser(String username);

    public abstract List<Report> getAllReportsOfMunicipality(Municipality municipality);

    public abstract void addReport(Report report);

    public abstract void updateReport(Report report,String status);

}
