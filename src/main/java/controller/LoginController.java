package controller;

import beans.LoginBean;
import model.Municipality;
import model.users.Citizen;
import model.users.Employee;
import model.users.User;
import exceptions.DataAccessException;
import dao.FactoryDao;
import dao.MunicipalityDao;
import dao.UserDao;
import exceptions.ApplicationException;
import exceptions.UserNotFoundException;
import org.example.viewprova2.session.SessionManager;

public class LoginController {


    public void authenticateUser(LoginBean loginBean) {
        FactoryDao   factoryDao    = FactoryDao.getInstance();
        UserDao      userDao       = factoryDao.createUserDao();
        SessionManager session     = SessionManager.getInstance();

        try {
            // 1) Trovo l'utente generico
            User authenticatedUser = userDao.verifyUser(
                    loginBean.getUsername(),
                    loginBean.getPassword(),
                    loginBean.getRole()
            );

            // 2) Se è un Employee, verifico il codice del comune
            if (authenticatedUser instanceof Employee) {
                Municipality m = factoryDao
                        .createMunicipalityDao()
                        .getMunicipalityByCode(loginBean.getMunicipalityCode());

                if (m == null) {
                    throw new ApplicationException(
                            "Nessun comune trovato per codice: " + loginBean.getMunicipalityCode()
                    );
                }
                session.setMunicipalityCode(m.getCodice());
            }

            // 3) Memorizzo l’utente in sessione
            session.setCurrentUser(authenticatedUser);

        } catch (UserNotFoundException e) {
            throw new ApplicationException("Credenziali non valide.");
        } catch (DataAccessException dae) {
            throw new ApplicationException("Errore durante il login: " + dae.getMessage());
        }
    }



    public void registerUser(LoginBean loginBean) {
        FactoryDao factoryDao      = FactoryDao.getInstance();
        UserDao userDao            = factoryDao.createUserDao();
        User newUser;

        try {

            if (userDao.findByUsername(loginBean.getUsername()) != null) {
                throw new ApplicationException("Username già presente. Scegli un altro username.");
            }

            if (loginBean.getRole().equals("Citizen")) {

                Citizen citizen = new Citizen(
                        loginBean.getUsername(),
                        loginBean.getPassword(),
                        loginBean.getRole()
                );
                newUser = citizen;

            } else {
                MunicipalityDao municipalityDao = factoryDao.createMunicipalityDao();
                Municipality municipality = municipalityDao.getMunicipalityByCode(
                        loginBean.getMunicipalityCode()
                );

                if (municipality == null) {
                    throw new ApplicationException("Codice del comune non valido.");
                }

                Employee employee = new Employee(
                        loginBean.getUsername(),
                        loginBean.getPassword(),
                        loginBean.getRole()
                );
                employee.setMyMunicipality(municipality);
                newUser = employee;
            }

            userDao.addUser(newUser);

        } catch (DataAccessException e) {
            throw new ApplicationException(
                    "Impossibile registrare l'utente nel database. Riprova più tardi."
            );
        }


    }

        public void changePassword(LoginBean loginBean) {
            FactoryDao factoryDao = FactoryDao.getInstance();
            UserDao userDao = factoryDao.createUserDao();
            try {
                userDao.updatePassword(SessionManager.getInstance().getCurrentUser().getUsername(), loginBean.getPassword());
            }catch (DataAccessException e) {
                throw new ApplicationException(e.getMessage());
            }


        }

        public void changeUsername(LoginBean loginBean) {
            FactoryDao factoryDao = FactoryDao.getInstance();
            UserDao userDao = factoryDao.createUserDao();
            try {
                userDao.updateUsername(SessionManager.getInstance().getCurrentUser().getUsername(), loginBean.getUsername());
            }catch (DataAccessException e) {
                throw new ApplicationException(e.getMessage());
            }
    }


        public LoginBean getUserCredentials(){

            SessionManager sessionManager = SessionManager.getInstance();
            User user = sessionManager.getCurrentUser();
            LoginBean loginBean = new LoginBean(user.getUsername(), user.getPassword(), user.getRole(), null);
            return loginBean;

        }


}
