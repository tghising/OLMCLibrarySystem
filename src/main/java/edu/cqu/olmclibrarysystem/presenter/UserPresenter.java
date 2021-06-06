package edu.cqu.olmclibrarysystem.presenter;

import edu.cqu.olmclibrarysystem.model.User;

import java.util.List;

/**
 * UserPresenter: Presenter for User
 *
 * @author Tikaraj Ghising
 */
public class UserPresenter {

    private UserPersister persister;

    public UserPresenter() {
        this.persister = new UserPersister();
    }

    public void add(User user) {
        persister.registerUser(user);
    }

    public void updatePassword(int userId, String password) {
        persister.updateUserPassword(userId, password);
    }

    public void delete(int userId) {
        persister.deleteUser(userId);
    }

    public User search(String email) {
        User user = persister.findUserByEmail(email);
        return user;
    }

    public List<User> getAllUsers() {
        return persister.findAllUsers();

    }

    public boolean isUserExist(String email, String password) {
        return persister.isUserExist(email, password);
    }
}
