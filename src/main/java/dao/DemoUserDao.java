package dao;

import model.Municipality;
import model.users.Citizen;
import model.users.Employee;


import model.users.User;
import exceptions.DataAccessException;
import exceptions.UserNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DemoUserDao extends UserDao {

    private final List<User> users = new ArrayList<>();


    private static DemoUserDao instance;
    public static DemoUserDao getInstance() {
        if (instance == null) {
            instance = new DemoUserDao();
        }
        return instance;
    }
    private DemoUserDao() {
        // due utenti di esempio
        users.add(new Citizen("Seli", "CivisAlert", "Citizen"));
        users.add(new Employee("SeliEmployee", "password", "Employee"));
    }


    @Override
    public void addUser(User user) {
        boolean exists = users.stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername()));
        if (exists) {
            throw new DataAccessException(
                    "Utente già presente: " + user.getUsername());
        }
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
            throw new UserNotFoundException(
                    "Credenziali non valide per cittadino: " + username);
        }
        return (Citizen) found.get();
    }


    @Override
    public Employee authenticateEmployee(
            String username,
            String password,
            String municipalityCode) {

        // 1) trova l'utente e verifica username/password e tipo Employee
        Optional<User> foundUser = users.stream()
                .filter(u -> u instanceof Employee
                        && u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst();

        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(
                    "Credenziali non valide per dipendente: " + username);
        }
        Employee emp = (Employee) foundUser.get();

        // 2) cerca il Municipio in memoria per codice
        Municipality m = FactoryDao.getInstance()
                .createMunicipalityDao()
                .getMunicipalityByCode(municipalityCode);

        if (m == null) {
            throw new UserNotFoundException(
                    "Codice comune non valido: " + municipalityCode);
        }

        // 3) assegna il Comune e restituisci l'Employee
        emp.setMyMunicipality(m);
        return emp;
    }


    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException("Utente non trovato: " + username)
                );
    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }

    @Override
    public void updateUsername(String username,String newUsername) {

    }
}
