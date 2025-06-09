package dao;

import model.users.Citizen;
import model.users.Employee;
import model.users.User;

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
    public User verifyUser(String username, String password, String role) {
        return null;
    }



}
