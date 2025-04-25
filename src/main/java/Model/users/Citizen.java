package Model.users;

import Model.Report;

import java.util.List;

public class Citizen extends User {

    private List<Report> reports;

    public Citizen(String username, String password, String role) {
        super(username, password, role);
    }

    public List<Report> getReports() {
        return reports;
    }

    public void addReport(Report report) {
        reports.add(report);
    }


}
