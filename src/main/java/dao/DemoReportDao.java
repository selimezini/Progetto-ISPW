package dao;

import model.Municipality;
import model.Report;
import exceptions.DataAccessException;

import java.util.ArrayList;
import java.util.List;

public class DemoReportDao extends ReportDao {

    private static DemoReportDao instance;
    private final List<Report> reports = new ArrayList<>();

    private DemoReportDao() {}

    public static DemoReportDao getInstance() {
        if (instance == null) {
            instance = new DemoReportDao();
        }
        return instance;
    }

    @Override
    public List<Report> getReportsOfUser(String username) {
        if (username == null) {
            throw new DataAccessException("Username non può essere null");
        }
        try {
            List<Report> result = new ArrayList<>();
            for (Report r : reports) {
                if (r.getAuthor().getUsername().equals(username)) {
                    result.add(r);
                }
            }
            return result;
        } catch (Exception ex) {
            throw new DataAccessException("Errore recuperando report per utente: " + username, ex);
        }
    }

    @Override
    public List<Report> getAllReportsOfMunicipality(String munName, String munProvince) {
        List<Report> result = new ArrayList<>();
        for (Report r : reports) {
            Municipality m = r.getMunicipality();
            if (m != null
                    && m.getName().equals(munName)
                    && m.getProvince().equals(munProvince)) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public void addReport(Report report) {
        System.out.println("[DEBUG] Aggiunta report: " + report);

        reports.add(report);
    }


    @Override
    public void updateReport(String id, String newStatus) {

        try {
            boolean found = false;
            for (Report r : reports) {
                if (r.getReportId().equals(id)) {
                    r.setStatus(newStatus);
                    found = true;
                    break;
                }
            }

        } catch (DataAccessException dae) {
            throw dae; // rilancia se non trovato
        } catch (Exception ex) {
            throw new DataAccessException("Errore aggiornando lo stato del report con ID: " + id, ex);
        }
    }
}
