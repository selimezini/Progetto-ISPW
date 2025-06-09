package dao;

import model.Report;

import java.util.List;

public class FsysReportDao extends ReportDao {
    @Override
    public List<Report> getReportsOfUser(String username) {
        return List.of();
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(String munName, String munProvince) {

        return List.of();
    }

    @Override
    public void addReport(Report report) {

    }

    @Override
    public void updateReport(String id, String newStatus) {

    }


}
