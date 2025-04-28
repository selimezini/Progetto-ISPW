package dao;

import Model.Municipality;
import Model.Report;

import java.util.ArrayList;
import java.util.List;

public class DemoReportDao extends ReportDao {

    private static  DemoReportDao instance;
    private  final List<Report> reports = new ArrayList<>();

    private DemoReportDao() {}

    public static DemoReportDao getInstance() {
        if (instance == null) {
            instance = new DemoReportDao();
        }
        return instance;
    }



    @Override
    public List<Report> getReportsOfUser(String username) {
        List<Report> result = new ArrayList<>();
        for(Report r : reports) {
            if(r.getAuthor().getUsername().equals(username)){
                result.add(r);
            }
        }

        return result;
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(Municipality municipality) {
        List<Report> result = new ArrayList<>();
        for(Report r : reports) {
            if(r.getMunicipality().equals(municipality)){
                result.add(r);
            }
        }
        return result;

    }

    @Override
    public void addReport(Report report) {
        reports.add(report);

    }

    @Override
    public void updateReport(Report report,String status) {

        for(Report r : reports) {
            if(r.getReportId().equals(report.getReportId())) {
                r.setStatus(status);
                break;
            }
        }

    }
}
