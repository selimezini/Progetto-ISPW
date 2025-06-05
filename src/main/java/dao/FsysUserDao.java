package dao;

import Model.users.Citizen;
import Model.users.Employee;
import Model.users.User;

public class FsysUserDao extends UserDao {
    @Override
    public void addUser(User user) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }

    @Override
    public void updateUsername(String username, String newUsername) {

    }


    @Override
    public Citizen authenticateCitizen(String username, String password) {

        return null;
    }

    @Override
    public Employee authenticateEmployee(String username, String password, String MunicipalityCode) {

        return null;
    }
}
