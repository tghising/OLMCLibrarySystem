package edu.cqu.olmclibrarysystem.model;

import edu.cqu.olmclibrarysystem.enums.UserRole;

/**
 * User: Model class for User registration
 *
 * @author Tikaraj Ghising - 12129239
 */
public class User {

    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private UserRole role = UserRole.LIBRARIAN; //Default role of User

    // default constructor
    public User() {
    }

    // parameterized constructor
    public User(String name, String email, String pwd, String phoneNumber, String address) {
        this.fullName = name;
        this.email = email;
        this.password = pwd;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(String name, String email, String phoneNumber, String address) {
        this.fullName = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        String userInfo = "User Id: " + userId + ", Full Name: " + fullName + ", Email: " + email + ", Role: " + role + ", Phone Number: " + phoneNumber
                + ", Address: " + address;
        return userInfo;
    }
}


