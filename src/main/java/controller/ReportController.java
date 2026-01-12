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

                MunicipalityBean bean = new MunicipalityBean(m.getName(), m.getRegion(), m.getCodice());
                beans.add(bean);
            }

            sessionManager.setMunicipalities(municipalities);

            return beans;

        } catch (DataAccessException e) {
            throw new ApplicationException(e.getMessage());
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


            report.setProblemType(bean.getProblemTypeEnum());
            report.setUrgencyType(bean.getUrgencyTypeEnum());
            report.setDate(new Date());

           //salvo
            reportDao.addReport(report);
            System.out.println("Report submitted");

        } catch (DataAccessException ex) {

            throw new ApplicationException(ex.getMessage());
        }
    }


    public List<BeanReport> getReportsForCurrentMunicipality() {
        String municipalityCode = SessionManager.getInstance().getMunicipalityCode();

        FactoryDao factory = FactoryDao.getInstance();
        MunicipalityDao munDao = factory.createMunicipalityDao();
        Municipality currentMunicipality = munDao.getMunicipalityByCode(municipalityCode);


        String munName     = currentMunicipality.getName();
        String munProvince = currentMunicipality.getProvince();

        List<Report> reports = factory
                .createReportDao()
                .getAllReportsOfMunicipality(munName, munProvince);

        for (Report r : reports) {
            r.setMunicipality(currentMunicipality);
        }


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


        String username = SessionManager.getInstance()
                .getCurrentUser()
                .getUsername();



        List<Report> modelReports = FactoryDao
                .getInstance()
                .createReportDao()
                .getReportsOfUser(username);


        List<BeanReport> beans = new ArrayList<>(modelReports.size());
        for (Report r : modelReports) {
            BeanReport b = new BeanReport();

            b.setTitle(r.getTitle());
            b.setDescription(r.getDescription());
            b.setProblemType(r.getProblemType().getDescription());
            b.setUrgencyType(r.getUrgencyType().getDescription());
            b.setStatus(r.getStatus());
            b.setImagePath(r.getImagePath());
            b.setImage(null);
            b.setViaDelProblema(r.getViaDelProblema());


            b.setReportId(r.getReportId());
            b.setDate(r.getDate());
            b.setAuthorUsername(r.getAuthor().getUsername());
            b.setMunicipalityCode(r.getMunicipality().getCodice());
            b.setMunicipalityName(r.getMunicipality().getName());
            b.setMunicipalityProvince(r.getMunicipality().getProvince());
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

            beans.add(b);
        }


        return beans;
    }
}





