package dao;

import Model.Municipality;
import Model.users.Citizen;
import Model.users.Employee;
import Model.users.User;
import exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DemoUserDao: DAO “in memoria” per gli utenti.
 * Singleton: un’unica lista condivisa di utenti in RAM.
 */
public class DemoUserDao extends UserDao {

    private final List<User> users = new ArrayList<>();

    private static  DemoUserDao instance;
    public static DemoUserDao getInstance() {
        if(instance == null) {
            instance = new DemoUserDao();
        }
        return instance; }
    private DemoUserDao() {
        users.add(new Citizen("Seli","CivisAlert","Citizen"));
        users.add(new Employee("SeliEmployee","password","Employee"));
    }




    @Override
    public void addUser(User user) {
        // (opzionale) verifica duplicati su username prima di aggiungere
        users.add(user);
    }

    @Override
    public Citizen authenticateCitizen(String username, String password) {
        Optional<User> found = users.stream()
                .filter(u -> u instanceof Citizen
                        && u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst();

        if (found.isEmpty()) {
            throw new UserNotFoundException();
        }
        return (Citizen) found.get();
    }

    @Override
    public Employee authenticateEmployee(
            String username,
            String password,
            String municipalityName,
            String municipalityCode) {


        Optional<User> foundUser = users.stream()
                .filter(u -> u instanceof Employee
                        && u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst();

        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        Employee emp = (Employee) foundUser.get();


        MunicipalityDao mDao = FactoryDao.getInstance().createMunicipalityDao();
        Municipality m = mDao.getMunicipalityByCode(municipalityCode);
        if (m == null) {
            throw new UserNotFoundException();
        }



        emp.setMyMunicipality(m);
        return emp;
    }


}
