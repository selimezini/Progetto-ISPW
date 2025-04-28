package dao;

import Model.users.Citizen;
import Model.users.Employee;
import Model.users.User;

public class FsysUserDao extends UserDao {
    @Override
    public void addUser(User user) {

    }

    @Override
    public Citizen authenticateCitizen(String username, String password) {
        return null;
    }

    @Override
    public Employee authenticateEmployee(String username, String password, String MunicipalityName, String MunicipalityCode) {
        return null;
    }
}
