package Controller;

import Beans.LoginBean;
import Model.Municipality;
import Model.users.Citizen;
import Model.users.Employee;
import Model.users.User;
import exceptions.DataAccessException;
import dao.FactoryDao;
import dao.MunicipalityDao;
import dao.UserDao;
import exceptions.ApplicationException;
import exceptions.UserNotFoundException;
import org.example.viewprova2.session.SessionManager;

public class LoginController {


    public void authenticateUser(LoginBean loginBean) {

        FactoryDao factoryDao = FactoryDao.getInstance();
        UserDao userDao = factoryDao.createUserDao();

        SessionManager sessionManager = SessionManager.getInstance();

        try {
            User authenticatedUser;
            // Confronto sul ruolo
            if ("Dipendente".equals(loginBean.getRole())) {

                authenticatedUser = userDao.authenticateEmployee(
                        loginBean.getUsername(),
                        loginBean.getPassword(),
                        loginBean.getMunicipalityCode()
                );
            } else {

                authenticatedUser = userDao.authenticateCitizen(
                        loginBean.getUsername(),
                        loginBean.getPassword()
                );
            }


            sessionManager.setCurrentUser(authenticatedUser);

        } catch (UserNotFoundException e) {

            throw new ApplicationException("Credenziali non valide. Riprova.");
        }
    }


    public void registerUser(LoginBean loginBean) {
        FactoryDao factoryDao = FactoryDao.getInstance();
        UserDao userDao = factoryDao.createUserDao();
        SessionManager sessionManager = SessionManager.getInstance();
        User newUser;



        try{
            if(loginBean.getRole().equals("Citizen"))  {
                Citizen citizen = new Citizen(loginBean.getUsername(), loginBean.getPassword(), loginBean.getRole());
                newUser = (User) citizen;
            }else{
                Employee employee = new Employee(loginBean.getUsername(), loginBean.getPassword(), loginBean.getRole());
                MunicipalityDao municipalityDao = factoryDao.createMunicipalityDao();
                Municipality municipality = municipalityDao.getMunicipalityByCode(loginBean.getMunicipalityCode());
                employee.setMyMunicipality(municipality);
                newUser = (User) employee;
            }

            userDao.addUser(newUser);


        }catch(DataAccessException e){
             throw new ApplicationException("Impossibile registare l'utente nel database. Riprova piu tardi");
        }

        sessionManager.setCurrentUser(newUser);
    }
}
