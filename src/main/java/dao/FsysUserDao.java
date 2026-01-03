package dao;

import model.users.Citizen;
import model.users.Employee;
import model.users.User;

public class FsysUserDao extends UserDao {
    @Override
    public void addUser(User user) {
    // non implementato
    }

    @Override
    public User findByUsername(String username) {

        // non implementato
        return null;
    }

    @Override
    public void updatePassword(String username, String newPassword) {
    //non implementato
    }

    @Override
    public void updateUsername(String username, String newUsername) {
    // non implementato
    }

    @Override
    public User verifyUser(String username, String password, String role) {

        //non implementato
        return null;
    }



}
