package Controller;

import Beans.BeanReport;
import Beans.MunicipalityBean;
import Model.Municipality;
import Model.ProblemType;
import Model.Report;
import Model.UrgencyType;
import Model.users.Citizen;
import Model.users.User;
import dao.FactoryDao;
import dao.MunicipalityDao;
import dao.ReportDao;
import dao.UserDao;
import exceptions.ApplicationException;
import exceptions.DataAccessException;
import exceptions.UserNotFoundException;
import org.example.viewprova2.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

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

            MunicipalityDao muniDao = factory.createMunicipalityDao();
            ReportDao  reportDao = factory.createReportDao();

            // 1) Autore, autore me lo prendo direttamente dal sessionManager. Non serve scomodare il db


            // 2) salvo il municipality e me lo prendo attraverso il Bean che ho salvato sul SessionManager
            Municipality m = muniDao.getMunicipalityByCode(bean.getMunicipalityCode());
            if (m == null) {
                throw new ApplicationException("Comune non trovato: " + bean.getMunicipalityCode());
            }

            // 3) Crea il model Report
            Report r = new Report();
            r.setReportId(bean.getReportId());
            r.setTitle(bean.getTitle());
            r.setDescription(bean.getDescription());
            r.setStatus(bean.getStatus());
            r.setViaDelProblema(bean.getViaDelProblema());
            r.setImagePath(bean.getImagePath());
            if (bean.getImage() != null) {
                r.setImage(bean.getImage());
            }
            r.setAuthor(author);
            r.setMunicipality(m);

            // 4) Mappa ProblemType dalla descrizione
            ProblemType pt = null;
            for (ProblemType p : ProblemType.values()) {
                if (p.getDescription().equals(bean.getProblemType())) {
                    pt = p;
                    break;
                }
            }
            if (pt == null) {
                throw new ApplicationException("Tipo problema non valido: " + bean.getProblemType());
            }
            r.setProblemType(pt);

            // 5) Mappa UrgencyType dalla descrizione
            UrgencyType ut = null;
            for (UrgencyType utype : UrgencyType.values()) {
                if (utype.getDescription().equals(bean.getUrgencyType())) {
                    ut = utype;
                    break;
                }
            }
            if (ut == null) {
                throw new ApplicationException("Tipo urgenza non valido: " + bean.getUrgencyType());
            }
            r.setUrgencyType(ut);

            // 6) (eventuale) data
            if (bean.getDate() != null) {
                r.setDate(bean.getDate());
            }

            // 7) Salva
            reportDao.addReport(r);

        } catch (UserNotFoundException | DataAccessException dae) {
            // ereditato dal dao, riconfezioniamo un’eccezione di application
            throw new ApplicationException("Errore durante il salvataggio del report: " + dae.getMessage(), dae);
        }
    }




}
