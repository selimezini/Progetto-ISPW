package dao;

import Model.Municipality;
import Model.Report;

import java.util.List;

public class FsysReportDao extends ReportDao {
    @Override
    public List<Report> getReportsOfUser(String username) {
        return List.of();
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(Municipality municipality) {
        return List.of();
    }

    @Override
    public void addReport(Report report) {

    }

    @Override
    public void updateReport(Report report,String status) {

    }
}
