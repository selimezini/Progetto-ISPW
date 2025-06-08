package dao;

import model.users.Citizen;
import model.users.Employee;
import model.users.User;

public abstract class UserDao {


    public abstract  void addUser(User user);

    public abstract Citizen authenticateCitizen(String username, String password);

    public abstract Employee authenticateEmployee(String username, String password,  String MunicipalityCode);

    public abstract User findByUsername(String username);

    public abstract void updatePassword(String username,String newPassword);
    public abstract void updateUsername(String username, String newUsername);

}
