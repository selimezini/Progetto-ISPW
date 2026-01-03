package dao;

import model.Report;

import java.util.List;

public class FsysReportDao extends ReportDao {
    @Override
    public List<Report> getReportsOfUser(String username) {

        // non implementato
        return List.of();
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(String munName, String munProvince) {
        // non implementato
        return List.of();
    }

    @Override
    public void addReport(Report report) {
        //non implementato
    }

    @Override
    public void updateReport(String id, String newStatus) {
        //non implementato
    }


}
