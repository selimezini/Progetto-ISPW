package controller;

import beans.BeanReport;
import beans.MunicipalityBean;
import model.Municipality;

import model.Report;

import model.users.Citizen;

import dao.FactoryDao;
import dao.MunicipalityDao;
import dao.ReportDao;

import exceptions.ApplicationException;
import exceptions.DataAccessException;

import org.example.viewprova2.session.SessionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReportController {

    public List<MunicipalityBean> searchMunicipality(MunicipalityBean municipality) {
        FactoryDao dao = FactoryDao.getInstance();
        MunicipalityDao municipalityDao = dao.createMunicipalityDao();
        SessionManager sessionManager = SessionManager.getInstance();

        try {
            List<Municipality> municipalities =
                    municipalityDao.getMunicipalityByName(municipality.getName());
            List<MunicipalityBean> beans = new ArrayList<>();

            for (Municipality m : municipalities) {

                MunicipalityBean bean = new MunicipalityBean(m.getName(), m.getRegion());
                beans.add(bean);
            }

            sessionManager.setMunicipalities(municipalities);
            return beans;

        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    public void submitReport(BeanReport bean) {
        try {

            FactoryDao factory = FactoryDao.getInstance();
            SessionManager session = SessionManager.getInstance();
            MunicipalityDao muniDao = factory.createMunicipalityDao();
            ReportDao reportDao = factory.createReportDao();


            Citizen author = (Citizen) session.getCurrentUser();
            MunicipalityBean munBean = session.getCurrentMunicipalityReport();
            Municipality municipality = muniDao.getMunicipalityByNameAndRegion(munBean.getName(),munBean.getRegion() );

            // 3) Costruisce il Report
            Report report = new Report();
            String randomId = UUID.randomUUID().toString();
            report.setReportId(randomId);
            report.setTitle(bean.getTitle());
            report.setDescription(bean.getDescription());
            report.setStatus(bean.getStatus());
            report.setViaDelProblema(bean.getViaDelProblema());
            report.setImagePath(bean.getImagePath());
            if (bean.getImage() != null) {
                report.setImage(bean.getImage());
            }
            report.setAuthor(author);
            report.setMunicipality(municipality);

            // 4) Assegna tipi ed data corrente
            report.setProblemType(bean.getProblemTypeEnum());
            report.setUrgencyType(bean.getUrgencyTypeEnum());
            report.setDate(new Date());
            System.out.println("Stampo il path dell'immagine: " + report.getImagePath());
            // 5) Salva
            reportDao.addReport(report);
            System.out.println("Report submitted");

        } catch (DataAccessException ex) {
            // qualunque errore (DAO, cast, null, ecc.) viene rilanciato come ApplicationException
            throw new ApplicationException(ex.getMessage());
        }
    }


    public List<BeanReport> getReportsForCurrentMunicipality() {

        System.out.println("Entrato nel controller applciativo per getReportsforCurrentMunicipality");
        // 1) Prendo il codice del comune da SessionManager
        String municipalityCode = SessionManager.getInstance().getMunicipalityCode();
        if (municipalityCode == null || municipalityCode.isBlank()) {
            return List.of(); // Codice comune non disponibile
        }

        // 2) Recupero i report dal DAO
        List<Report> reports = FactoryDao
                .getInstance()
                .createReportDao()
                .getAllReportsOfMunicipality(municipalityCode);

        // 3) Mappo ogni Report in un BeanReport
        List<BeanReport> beanList = new ArrayList<>(reports.size());

        for (Report r : reports) {
            BeanReport b = new BeanReport();
            b.setReportId(r.getReportId());
            b.setTitle(r.getTitle());
            b.setDescription(r.getDescription());
            b.setProblemType(r.getProblemType().getDescription());
            b.setUrgencyType(r.getUrgencyType().getDescription());
            b.setStatus(r.getStatus());
            b.setImagePath(r.getImagePath());

            if (r.getImagePath() != null && !r.getImagePath().isBlank()) {
                try {
                    javafx.scene.image.Image img = new javafx.scene.image.Image(r.getImagePath());
                    b.setImage(img);
                } catch (Exception e) {
                    b.setImage(null);
                }
            }
            b.setViaDelProblema(r.getViaDelProblema());
            b.setAuthorUsername(r.getAuthor().getUsername());
            b.setMunicipalityName(r.getMunicipality().getName());
            b.setMunicipalityProvince(r.getMunicipality().getProvince());
            b.setMunicipalityCode(r.getMunicipality().getCodice());
            b.setDate(r.getDate());
            beanList.add(b);

        }

        return beanList;
    }


    public void submitUpdate(BeanReport bean) {

        FactoryDao factory = FactoryDao.getInstance();
        ReportDao reportDao = factory.createReportDao();
        reportDao.updateReport(bean.getReportId(), bean.getStatus());

    }


    public List<BeanReport> getUserReports() {
        System.out.println("Entrato nel controller applicativo per getUserReports");

        // 1) Username corrente
        String username = SessionManager.getInstance()
                .getCurrentUser()
                .getUsername();
        System.out.println("Username recuperato: " + username);

        // 2) Recupero la lista di Report dal DAO
        List<Report> modelReports = FactoryDao
                .getInstance()
                .createReportDao()
                .getReportsOfUser(username);
        System.out.println("Numero di Report dal DAO: " + modelReports.size());

        // 3) Mappa ogni Report in un BeanReport
        List<BeanReport> beans = new ArrayList<>(modelReports.size());
        for (Report r : modelReports) {
            BeanReport b = new BeanReport(
                    r.getTitle(),
                    r.getDescription(),
                    r.getProblemType().getDescription(),
                    r.getUrgencyType().getDescription(),
                    r.getStatus(),
                    r.getImagePath(),
                    null,                    // immagine JavaFX la settiamo dopo
                    r.getViaDelProblema()
            );

            // 4) Imposto gli altri campi con i setter
            b.setReportId(r.getReportId());
            b.setDate(r.getDate());                     // se usi getFormattedDate(): b.setDate(r.getFormattedDate());
            b.setAuthorUsername(r.getAuthor().getUsername());
            b.setMunicipalityCode(r.getMunicipality().getCodice());
            b.setMunicipalityName(r.getMunicipality().getName());
            b.setMunicipalityProvince(r.getMunicipality().getProvince());

            // 5) Provo a caricare l'immagine solo se il path non è vuoto
            String imgPath = r.getImagePath();
            if (imgPath != null && !imgPath.isBlank()) {
                try {
                    javafx.scene.image.Image img = new javafx.scene.image.Image(imgPath);
                    b.setImage(img);
                } catch (Exception ex) {
                    System.out.println("Impossibile caricare immagine da: " + imgPath);
                    b.setImage(null);
                }
            }

            System.out.println("HO PRESO IL REPORT DI USER: " + b);
            beans.add(b);
        }

        System.out.println("Totale BeanReport restituiti: " + beans.size());
        return beans;
    }

}





