package org.example.viewprova2.session;

import Model.Report;
import Model.users.User;

import java.util.List;

public class SessionManager {

    private static SessionManager instance = new SessionManager();
    private User currentUser;
    private List<Report> reports;



    public static SessionManager getInstance() {
        return instance;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void changePassword(String newPassword) {
        currentUser.setPassword(newPassword);
    }

    public List<Report> getReports() {

        return reports;

    }

    public void setStatusReport(String reportId, String status) {
        for (Report report : reports) {
            if (report.getReportId().equals(reportId)) {
                report.setStatus(status);
                break; // Se l'ID è univoco, puoi uscire dal ciclo
            }
        }
    }

    public void addReport(Report report) {
        reports.add(report);
    }




}
