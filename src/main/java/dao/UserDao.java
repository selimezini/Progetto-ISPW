package dao;

import Model.users.Citizen;
import Model.users.Employee;
import Model.users.User;

public abstract class UserDao {


    public abstract  void addUser(User user);

    public abstract Citizen authenticateCitizen(String username, String password);

    public abstract Employee authenticateEmployee(String username, String password, String MunicipalityName, String MunicipalityCode);



}
