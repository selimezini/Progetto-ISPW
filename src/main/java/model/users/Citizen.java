package model.users;

import model.Report;

import java.util.List;

public class Citizen extends User {

    public Citizen(String username, String password, String role) {

        super(username, password, role);
    }

    private List<Report> reports;



    public List<Report> getReports() {
        return reports;
    }

    public void addReport(Report report) {
        reports.add(report);
    }


}
