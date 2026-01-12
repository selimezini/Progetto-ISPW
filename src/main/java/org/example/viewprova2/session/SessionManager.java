package org.example.viewprova2.session;

import beans.MunicipalityBean;
import model.Municipality;
import model.Report;
import model.users.User;

import java.util.List;
@SuppressWarnings("java:S6548")
public class SessionManager {

    private static SessionManager instance = new SessionManager();
    private User currentUser;
    private List<Report> reports;
    private List<Municipality> municipalities;
    private MunicipalityBean currentMunicipalityReport;
    private String municipalityCode;


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
                break;
            }
        }
    }

    public void addReport(Report report) {
        reports.add(report);
    }


    public void setMunicipalities(List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }

    public void setCurrentMunicipalityReport(MunicipalityBean municipalityReport) {
        this.currentMunicipalityReport = municipalityReport;
    }

    public MunicipalityBean getCurrentMunicipalityReport() {
        return currentMunicipalityReport;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }


}
