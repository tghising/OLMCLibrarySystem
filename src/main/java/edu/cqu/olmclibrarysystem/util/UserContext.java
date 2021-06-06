package edu.cqu.olmclibrarysystem.util;


import edu.cqu.olmclibrarysystem.model.User;

/**
 * User: Context class for User which is singleton class and used to store current sign in user
 * information over the HTS
 *
 * @author Tikaraj Ghising - 12129239
 */
public class UserContext {

    private final static UserContext instance = new UserContext();
    private User loggedUser;

    public static UserContext getInstance() {
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }
}
