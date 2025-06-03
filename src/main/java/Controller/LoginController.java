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
        System.out.println( "RUOLO" +loginBean.getRole());
        SessionManager sessionManager = SessionManager.getInstance();

        try {
            User authenticatedUser;
            // Confronto sul ruolo
            if ("Employee".equals(loginBean.getRole())) {
                System.out.println("LoginController: STO ENTRANDO NEL RAMO EMPLOYEE");
                authenticatedUser = userDao.authenticateEmployee(
                        loginBean.getUsername(),
                        loginBean.getPassword(),
                        loginBean.getMunicipalityCode()
                );
            } else {
                System.out.println("LoginController: STO ENTRANDO NEL RAMO CITIZEN");
                authenticatedUser = userDao.authenticateCitizen(
                        loginBean.getUsername(),
                        loginBean.getPassword()
                );
            }


            sessionManager.setCurrentUser(authenticatedUser);
            if(loginBean.getMunicipalityCode() != null) {
                sessionManager.setMunicipalityCode(loginBean.getMunicipalityCode());
            }
            } catch (UserNotFoundException e) {

            throw new ApplicationException(e.getMessage());
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
