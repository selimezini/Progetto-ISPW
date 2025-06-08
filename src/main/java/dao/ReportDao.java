package dao;

import model.Report;

import java.util.List;

public abstract class ReportDao {


    public abstract List<Report> getReportsOfUser(String username);

    public abstract List<Report> getAllReportsOfMunicipality(String code);

    public abstract void addReport(Report report);

    public abstract void updateReport(String id,String newStatus);

}
