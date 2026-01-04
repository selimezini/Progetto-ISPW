package dao;


import model.users.Citizen;
import model.users.Employee;


import model.users.User;
import exceptions.DataAccessException;
import exceptions.UserNotFoundException;


import java.util.ArrayList;
import java.util.List;



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
        //  utenti di esempio
        users.add(new Citizen("Seli", "CivisAlert", "Citizen"));
        users.add(new Employee("SeliEmployee", "password", "Employee"));
        Employee employee = new Employee("balotelli", "password", "Employee");

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
        //non implementato
    }

    @Override
    public void updateUsername(String username,String newUsername) {
        //non implementato
    }

    @Override
    public User verifyUser(String username, String password, String role) {
        return users.stream()
                .filter(u ->
                        u.getUsername().equals(username)
                                && u.getPassword().equals(password)
                                && u.getRole().equals(role)
                )
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Credenziali non valide per ruolo: " + role
                        )
                );
    }
}
