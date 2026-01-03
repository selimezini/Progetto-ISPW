package dao;


import model.users.User;

public abstract class UserDao {


    public abstract  void addUser(User user);


    public abstract User findByUsername(String username);

    public abstract void updatePassword(String username,String newPassword);
    public abstract void updateUsername(String username, String newUsername);
    public abstract User verifyUser(String username,String password, String role);
}
